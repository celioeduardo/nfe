package com.hadrion.nfe.port.adapters.portal.evento;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.custommonkey.xmlunit.XMLAssert;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.util.DataUtil;

public class EventoSerializadorTest {
	
	@Test
	public void novoEvento() throws SAXException, IOException{
		
		Certificado certificado = new Certificado(
				FileUtils.getFile("src","test","resources","assinatura","certificado.pfx"), 
				"12345678");
		
		Evento evento = new EventoCancelamento(
				Uf.MG,
				Ambiente.PRODUCAO,
				new Cnpj(86675642000106L),
				new ChaveAcesso("31150186675642000106550020002638781002993906"),
				DataUtil.dataHora("27/01/2015 09:04:22", "GMT-02:00"), 
				new NumeroProtocolo("131151658576199"),
				"EMITIDA INDEVIDAMENTE.................");
		
		EventoSerializador serializador = new EventoSerializador(certificado);
		XMLAssert.assertXMLEqual(xmlAssinado(), serializador.serializar(evento));
		
	}
	
	private String xmlAssinado(){
		final File arquivoXml = FileUtils.getFile("src","test","resources","evento","evento-assinado.xml");
		
		String xml; 
		try {
			xml = FileUtils.readFileToString(arquivoXml,Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return xml;
	}
	
}
