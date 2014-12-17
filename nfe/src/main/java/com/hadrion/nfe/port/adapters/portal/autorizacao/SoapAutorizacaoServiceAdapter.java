package com.hadrion.nfe.port.adapters.portal.autorizacao;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.StringSource;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.AutorizacaoService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.RetornoAutorizacao;
import com.hadrion.nfe.port.adapters.portal.ws.Cabecalho;
import com.hadrion.nfe.port.adapters.portal.ws.EndPoints;
import com.hadrion.nfe.port.adapters.portal.ws.Servico;
import com.hadrion.nfe.port.adapters.portal.ws.Versao;
import com.hadrion.nfe.port.adapters.ws.WebServiceTemplateFabrica;
import com.hadrion.nfe.port.adapters.xml.nf.ValidadorLote;

@Service
public class SoapAutorizacaoServiceAdapter implements AutorizacaoService{
	
	@Autowired
	private WebServiceTemplateFabrica webServiceTemplateFabrica;
	
	@Autowired
	private LoteRepositorio loteRepositorio;
	
	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio; 
	
	@Override
	public RetornoAutorizacao autorizar(final Lote lote, Certificado certificado) throws Throwable {
		
		final String endpoint = EndPoints.obter(
				lote.ambiente(),
				lote.local(),
				Versao.V3_10, 
				Servico.AUTORIZACAO);
		
		String xml = nfeDadosMsg(lote,notasDoLote(lote),certificado);
		
		StreamSource source = new StreamSource(
				new StringReader(xml));
		
		ValidadorLote validador = new ValidadorLote(source);
		if (validador.temErros()){
			//TODO Remover System.out.println
			System.out.println(xml);
			throw new RuntimeException(validador.errosComoTexto());
		}
		
		StringWriter writerResult = new StringWriter();
		StreamResult result = new StreamResult(writerResult);

		WebServiceTemplate ws;
		
		ws = webServiceTemplateFabrica.criar(certificado.keyStore(), certificado.senha());
		
		ws.sendSourceAndReceiveToResult(
				endpoint,
				source,
				new WebServiceMessageCallback() {
					@Override
					public void doWithMessage(WebServiceMessage arg) throws IOException,
							TransformerException {
						
						((SoapMessage)arg).setSoapAction(
								endpoint+"/nfeAutorizacao");
						
						StringSource ss = new StringSource(nfeCabecMsg(lote));
						SoapHeader soapHeader = ((SoapMessage)arg).getSoapHeader();
						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						transformer.transform(ss, soapHeader.getResult());
						//BUG - Precisa dessa chamada aqui para atualizar o Header do Envelope SOAP.
						arg.writeTo(System.out);
						//arg.writeTo(new NullOutputStream());
					}
				},
				result);
		
		return new RetornoAutorizacaoDeserializador(
				writerResult.toString()).deserializar();
	}
	
	private Set<NotaFiscal> notasDoLote(Lote lote){
		List<NotaFiscalId> notas = new ArrayList<NotaFiscalId>(lote.notas());
		return new HashSet<NotaFiscal>(notaFiscalRepositorio.notasPendentesAutorizacao(notas,lote.ambiente()));
	}
	
	private String nfeCabecMsg(Lote lote){
		Cabecalho cabecalho = new Cabecalho(lote.uf());
		return cabecalho.autorizacao();
	}
	
	private String nfeDadosMsg(Lote lote, Set<NotaFiscal> notas, 
			Certificado certificado){
		Corpo corpo = new Corpo(lote, notas, certificado);
		return corpo.gerar();
		
//		final File xml = FileUtils.getFile("src","test","resources","ws","Autorizacao-nfeDadosMsg.xml");
//		
//		try {
//			return FileUtils.readFileToString(xml);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
	}
	
}
