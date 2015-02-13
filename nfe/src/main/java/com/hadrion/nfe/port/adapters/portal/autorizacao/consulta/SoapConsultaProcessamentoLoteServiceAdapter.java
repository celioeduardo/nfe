package com.hadrion.nfe.port.adapters.portal.autorizacao.consulta;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

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
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.ConsultaProcessamentoLoteService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.RetornoConsultaProcessamentoLote;
import com.hadrion.nfe.port.adapters.portal.ws.Cabecalho;
import com.hadrion.nfe.port.adapters.portal.ws.EndPoints;
import com.hadrion.nfe.port.adapters.portal.ws.Servico;
import com.hadrion.nfe.port.adapters.portal.ws.Versao;
import com.hadrion.nfe.port.adapters.ws.WebServiceTemplateFabrica;

@Service
public class SoapConsultaProcessamentoLoteServiceAdapter implements ConsultaProcessamentoLoteService{
	
	@Autowired
	private WebServiceTemplateFabrica webServiceTemplateFabrica;
	
	@Override
	public RetornoConsultaProcessamentoLote consultar(final Lote lote, Certificado certificado) {
		final String endpoint = EndPoints.obter(
				lote.ambiente(),
				lote.local(),
				Versao.V3_10, 
				Servico.RET_AUTORIZACAO);
		
		StreamSource source = new StreamSource(
				new StringReader(nfeDadosMsg(lote)));
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
								endpoint+"/nfeRetAutorizacaoLote");
						
						StringSource ss = new StringSource(nfeCabecMsg(lote.uf()));
						SoapHeader soapHeader = ((SoapMessage)arg).getSoapHeader();
						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						transformer.transform(ss, soapHeader.getResult());
						//BUG - Precisa dessa chamada aqui para atualizar o Header do Envelope SOAP.
						//arg.writeTo(System.out);
						//arg.writeTo(new NullOutputStream());
					}
				},
				result);
		
		//System.out.println("\n"+writerResult.toString());
		
		return new RetornoConsultaProcessamentoLoteDeserializador(
				writerResult.toString()).deserializar();

	}
		
	private String nfeCabecMsg(Uf uf){
		Cabecalho cabecalho = new Cabecalho(uf);
		return cabecalho.retornoAutorizacao();
	}
	
	private String nfeDadosMsg(Lote lote){
		Corpo corpo = new Corpo(lote);
		return corpo.gerar();
	}
	
}
