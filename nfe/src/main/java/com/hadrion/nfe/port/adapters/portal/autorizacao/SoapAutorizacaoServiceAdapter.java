package com.hadrion.nfe.port.adapters.portal.autorizacao;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
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
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.AutorizacaoService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.RetornoAutorizacao;
import com.hadrion.nfe.port.adapters.ws.WebServiceTemplateFabrica;

@Service
public class SoapAutorizacaoServiceAdapter implements AutorizacaoService{
	
	@Autowired
	private WebServiceTemplateFabrica webServiceTemplateFabrica;
	
	@Override
	public RetornoAutorizacao autorizar(Lote lote, Certificado certificado) throws Throwable {
		
		//final String endpoint = "https://nfe.fazenda.mg.gov.br/nfe2/services/NfeAutorizacao";
		final String endpoint = "https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeAutorizacao";
		
		//StreamSource source = new StreamSource(new StringReader("<message xmlns=\"http://tempuri.org\">Hello Web Service World</message>"));
		StreamSource source = new StreamSource(
				new StringReader(nfeDadosMsg()));
		StreamResult result = new StreamResult(System.out);
//		webServiceTemplate.sendSourceAndReceiveToResult(
//				//"https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeRecepcao2",
//				"https://nfe.fazenda.mg.gov.br/nfe2/services/NfeAutorizacao",
//				source, result);
		
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
						
						StringSource ss = new StringSource(nfeCabecMsg());
						SoapHeader soapHeader = ((SoapMessage)arg).getSoapHeader();
						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						transformer.transform(ss, soapHeader.getResult());
						//BUG - Precisa dessa chamada aqui para atualizar o Header do Envelope SOAP.
						arg.writeTo(System.out);
						//arg.writeTo(new NullOutputStream());
					}
				},
				result);
		return null;
	}
	
	
	private String nfeCabecMsg(){
		final File xml = FileUtils.getFile("src","test","resources","ws","Autorizacao-nfeCabecMsg.xml");
		
		try {
			return FileUtils.readFileToString(xml);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private String nfeDadosMsg(){
		final File xml = FileUtils.getFile("src","test","resources","ws","Autorizacao-nfeDadosMsg.xml");
		
		try {
			return FileUtils.readFileToString(xml);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
