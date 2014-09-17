package com.hadrion.nfe.port.adapters.portal.autorizacao;

import java.io.StringReader;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.AutorizacaoService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.RetornoAutorizacao;

@Service
public class SoapAutorizacaoServiceAdapter implements AutorizacaoService{
	
	@Autowired
	private WebServiceTemplate webServiceTemplate;
	
	@Override
	public RetornoAutorizacao autorizar(Lote lote) throws Throwable {
		StreamSource source = new StreamSource(new StringReader("<message xmlns=\"http://tempuri.org\">Hello Web Service World</message>"));
		StreamResult result = new StreamResult(System.out);
		webServiceTemplate.sendSourceAndReceiveToResult(
				"https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeRecepcao2",
				source, result);
	        
//		webServiceTemplate.sendSourceAndReceiveToResult(
//				"https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeRecepcao2",
//				domSource,
//				new WebServiceMessageCallback() {
//					@Override
//					public void doWithMessage(WebServiceMessage arg) throws IOException,
//							TransformerException {
//						
//						((SoapMessage)arg).setSoapAction("http://www.portalfiscal.inf.br/nfe/wsdl/NfeRecepcao2/nfeRecepcaoLote2");
//						
//						StringSource ss = new StringSource(cabecalho);
//						SoapHeader soapHeader = ((SoapMessage)arg).getSoapHeader();
//						Transformer transformer = TransformerFactory.newInstance().newTransformer();
//						transformer.transform(ss, soapHeader.getResult());
//						//BUG - Precisa dessa chamada aqui para atualizar o Header do Envelope SOAP.
//						arg.writeTo(new NullOutputStream());
//					}
//				},
//				result);
		
		System.out.println(webServiceTemplate);
		return null;
	}
	
}
