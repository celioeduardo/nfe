package com.hadrion.nfe.port.adapters.portal.inutilizacao;

import java.io.IOException;
import java.io.StringWriter;
import java.net.UnknownHostException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.StringSource;
import org.w3c.dom.Document;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.inutilizacao.Inutilizacao;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.InutilizacaoPortalService;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.RetornoInutilizacao;
import com.hadrion.nfe.port.adapters.portal.ws.Cabecalho;
import com.hadrion.nfe.port.adapters.portal.ws.Local;
import com.hadrion.nfe.port.adapters.ws.WebServiceTemplateFabrica;
import com.hadrion.nfe.tipos.Cnpj;

@Service
public class SoapInutilizacaoServiceAdapter implements InutilizacaoPortalService{
	
	@Autowired
	private WebServiceTemplateFabrica webServiceTemplateFabrica;
	
	@Override
	public RetornoInutilizacao inutilizar(
			Inutilizacao inutilizacao, Certificado certificado,
			Local local,final Uf uf, Cnpj cnpj) {
		
		final String endpoint = local.endpointInutilizacao(inutilizacao.ambiente()); 
				
		Document xml = nfeDadosMsg(inutilizacao,uf,cnpj,certificado);

//		Validação desativada		
//		NodeList nl = xml.getElementsByTagName("enviLote");
//		
//		if (nl.getLength() == 0)
//			throw new RuntimeException("Nó infLote não encontado.");
//		Node infNfe = nl.item(0);
//		
//		ValidadorLote validador = new ValidadorLote(infNfe);
//		if (validador.temErros()){
//			throw new RuntimeException(validador.errosComoTexto());
//		}
		
		DOMSource source = new DOMSource(xml);
		
		StringWriter writerResult = new StringWriter();
		StreamResult result = new StreamResult(writerResult);

		WebServiceTemplate ws;
		
		ws = webServiceTemplateFabrica.criar(certificado.keyStore(), certificado.senha());
		try {
			ws.sendSourceAndReceiveToResult(
					endpoint,
					source,
					new WebServiceMessageCallback() {
						@Override
						public void doWithMessage(WebServiceMessage arg) throws IOException,
								TransformerException {

							//((SoapMessage)arg).setSoapAction("http://www.portalfiscal.inf.br/nfe/wsdl/NfeInutilizacao2/nfeInutilizacaoNF2");
							((SoapMessage)arg).setSoapAction(endpoint);
							
							StringSource ss = new StringSource(nfeCabecMsg(uf));
							SoapHeader soapHeader = ((SoapMessage)arg).getSoapHeader();
							Transformer transformer = TransformerFactory.newInstance().newTransformer();
							transformer.transform(ss, soapHeader.getResult());
							//BUG - Precisa dessa chamada aqui para atualizar o Header do Envelope SOAP.
							arg.writeTo(System.out);
							//arg.writeTo(new NullOutputStream());
						}
					},
					result);
		} catch (WebServiceIOException e) {
			if (e.contains(UnknownHostException.class))
				throw new RuntimeException("Host desconhecido", e.getCause());
			throw new RuntimeException("Problema ao conectar com o WebService", e.getCause());
		} 
		
		
//		System.out.println("\n=== RETORNO ===");
//		System.out.println(writerResult.toString());
		
		return new RetornoInutilizacaoDeserializador(
				xml,
				writerResult.toString()).deserializar();
	}
	
	private String nfeCabecMsg(Uf uf){
		Cabecalho cabecalho = new Cabecalho(uf);
		return cabecalho.inutilizacao();
	}
	
	private Document nfeDadosMsg(Inutilizacao inutilizacao, 
			Uf uf, Cnpj cnpj, Certificado certificado){
		Corpo corpo = new Corpo(inutilizacao,uf,cnpj, certificado);
		return corpo.gerar();
	}
	
}
