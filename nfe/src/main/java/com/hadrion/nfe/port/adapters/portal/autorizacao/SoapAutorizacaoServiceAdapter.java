package com.hadrion.nfe.port.adapters.portal.autorizacao;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.StringSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.AutorizacaoService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.RetornoAutorizacao;
import com.hadrion.nfe.port.adapters.portal.ws.Cabecalho;
import com.hadrion.nfe.port.adapters.portal.ws.EndPoints;
import com.hadrion.nfe.port.adapters.portal.ws.Servico;
import com.hadrion.nfe.port.adapters.portal.ws.Versao;
import com.hadrion.nfe.port.adapters.ws.WebServiceTemplateFabrica;
import com.hadrion.nfe.port.adapters.xml.nf.ValidadorLote;
import com.hadrion.nfe.port.adapters.xml.nf.ValidadorNotaFiscal;

@Service
public class SoapAutorizacaoServiceAdapter implements AutorizacaoService{
	
	@Autowired
	private WebServiceTemplateFabrica webServiceTemplateFabrica;
	
	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio; 
	
	@Override
	public RetornoAutorizacao autorizar(final Lote lote, Certificado certificado) throws Throwable {
		
		final String endpoint = EndPoints.obter(
				lote.ambiente(),
				lote.local(),
				Versao.V3_10, 
				Servico.AUTORIZACAO);
		
		Document xml = nfeDadosMsg(lote,notasDoLote(lote),certificado);
		
		NodeList nl = xml.getElementsByTagName("enviNFe");
		
		if (nl.getLength() == 0)
			throw new RuntimeException("Nó infNFe não encontado.");
		
		Node infNfe = nl.item(0);
		
		NodeList nfeList = ((Element)infNfe).getElementsByTagName("NFe");
		RetornoAutorizacao retorno = new RetornoAutorizacao();
		for (int i = 0; i < nfeList.getLength(); i++) {
			Node nfe = nfeList.item(i);
			String chave = getIdAttribute(nfe,"infNFe");
			if (chave == null)
				throw new RuntimeException("Atributo Id da TAG infNFe não encontrado.");
			
			chave = chave.substring(3);
			ValidadorNotaFiscal validador = new ValidadorNotaFiscal(nfe);
			
			if (validador.temErros())
				retorno.registrarErro(new ChaveAcesso(chave), validador.errosComoTexto());
		}
		
		if (retorno.temErros()){
			for (int i = 0; i < nfeList.getLength(); i++) {
				Node nfe = nfeList.item(i);
				String chave = getIdAttribute(nfe,"infNFe");
				chave = chave.substring(3);
				if (!retorno.temErroPara(new ChaveAcesso(chave)))
					retorno.registrarErro(new ChaveAcesso(chave), "Lote inconsistente.");
			}
			
			return retorno;
		}
		
		ValidadorLote validador = new ValidadorLote(infNfe);
		if (validador.temErros()){
			throw new RuntimeException(validador.errosComoTexto());
		}
		
		DOMSource source = new DOMSource(xml);
		
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
					}
				},
				result);
		
		return new RetornoAutorizacaoDeserializador(
				writerResult.toString()).deserializar();
	}
	
	private String getIdAttribute(Node node, String tag){
		NodeList nl = ((Element)node).getElementsByTagName(tag);
		if (nl.getLength() == 0) 
			return null;
		return ((Element)nl.item(0)).getAttribute("Id");
	}
	
	private Set<NotaFiscal> notasDoLote(Lote lote){
		List<NotaFiscalId> notas = new ArrayList<NotaFiscalId>(lote.notas());
		return new HashSet<NotaFiscal>(notaFiscalRepositorio.notasNaoAutorizadas(notas,lote.ambiente()));
	}
	
	private String nfeCabecMsg(Lote lote){
		Cabecalho cabecalho = new Cabecalho(lote.uf());
		return cabecalho.autorizacao();
	}
	
	private Document nfeDadosMsg(Lote lote, Set<NotaFiscal> notas, 
			Certificado certificado){
		Corpo corpo = new Corpo(lote, notas, certificado);
		return corpo.gerar();
	}
	
}
