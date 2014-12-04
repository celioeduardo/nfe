package com.hadrion.nfe.port.adapters.portal.autorizacao;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

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
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.ConsultaProcessamentoLoteService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.RetornoConsultaProcessamentoLote;
import com.hadrion.nfe.port.adapters.portal.ws.EndPoints;
import com.hadrion.nfe.port.adapters.portal.ws.Servico;
import com.hadrion.nfe.port.adapters.portal.ws.Versao;
import com.hadrion.nfe.port.adapters.ws.WebServiceTemplateFabrica;

@Service
public class SoapConsultaProcessamentoLoteServiceAdapter implements ConsultaProcessamentoLoteService{
	
	@Autowired
	private WebServiceTemplateFabrica webServiceTemplateFabrica;
	
	@Override
	public RetornoConsultaProcessamentoLote consultar(Lote lote, Certificado certificado) {
		final String endpoint = EndPoints.obter(
				lote.ambiente(),
				lote.local(),
				Versao.V3_10, 
				Servico.RET_AUTORIZACAO);
		
		StreamSource source = new StreamSource(
				new StringReader(nfeDadosMsg()));
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
		
		System.out.println("\nRETORNO:");
		System.out.println(writerResult.toString());
		
		return new RetornoConsultaProcessamentoLoteDeserializador(
				writerResult.toString()).deserializar();

	}
		
	private String nfeCabecMsg(){
		final File xml = FileUtils.getFile("src","test","resources","ws","RetAutorizacao-nfeCabecMsg.xml");
		
		try {
			return FileUtils.readFileToString(xml);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String nfeDadosMsg(){
		final File xml = FileUtils.getFile("src","test","resources","ws","RetAutorizacao-nfeDadosMsg.xml");
		
		try {
			return FileUtils.readFileToString(xml);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	
	
}
