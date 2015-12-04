package com.hadrion.nfe.port.adapters.portal.evento;

import java.io.IOException;
import java.io.StringWriter;

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

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.portal.evento.EventoService;
import com.hadrion.nfe.dominio.modelo.portal.evento.RetornoLoteEvento;
import com.hadrion.nfe.port.adapters.portal.ws.Cabecalho;
import com.hadrion.nfe.port.adapters.portal.ws.EndPoints;
import com.hadrion.nfe.port.adapters.portal.ws.Local;
import com.hadrion.nfe.port.adapters.portal.ws.Servico;
import com.hadrion.nfe.port.adapters.portal.ws.Versao;
import com.hadrion.nfe.port.adapters.ws.WebServiceTemplateFabrica;

@Service
public class SoapEventoServiceAdapter implements EventoService{
	
	@Autowired
	private WebServiceTemplateFabrica webServiceTemplateFabrica;
	
	@Override
	public RetornoLoteEvento enviar(
			final LoteEvento lote, Certificado certificado,
			Ambiente ambiente, Local local) {
		
		final String endpoint = EndPoints.obter(
				ambiente,
				local,
				Versao.V1_00, 
				Servico.EVENTO);
		
		Document xml = nfeDadosMsg(lote,certificado);

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
		
		ws.sendSourceAndReceiveToResult(
				endpoint,
				source,
				new WebServiceMessageCallback() {
					@Override
					public void doWithMessage(WebServiceMessage arg) throws IOException,
							TransformerException {
						
						((SoapMessage)arg).setSoapAction(
								endpoint+"/nfeRecepcaoEvento");
						
						StringSource ss = new StringSource(nfeCabecMsg(lote));
						SoapHeader soapHeader = ((SoapMessage)arg).getSoapHeader();
						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						transformer.transform(ss, soapHeader.getResult());
					}
				},
				result);
		
		return new RetornoLoteEventoDeserializador(
				xml,
				writerResult.toString()).deserializar();
	}
	
	private String nfeCabecMsg(LoteEvento lote){
		Cabecalho cabecalho = new Cabecalho(lote.uf());
		return cabecalho.evento();
	}
	
	private Document nfeDadosMsg(LoteEvento lote, Certificado certificado){
		Corpo corpo = new Corpo(lote, certificado);
		return corpo.gerar();
	}
	
}
