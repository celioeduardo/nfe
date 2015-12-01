package com.hadrion.nfe.port.adapters.xml;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalFixture;
import com.hadrion.nfe.port.adapters.xml.nf.NotaFiscalSerializador;

public class NotaFiscalXmlSerializadorTest extends AbstractXmlTest{
	private Certificado certificado;	
	private String XML;
	private String XML_ASSINADO;
	
	@Before
	public void setUp() {
		super.setUp();

		certificado = new Certificado(
				FileUtils.getFile("src","test","resources","assinatura","certificado.pfx"), 
				"12345678");
		
		final File xml = FileUtils.getFile("src","test","resources","nf","nfe.xml");
		final File xmlAssinado = FileUtils.getFile("src","test","resources","nf","nfe-assinada.xml");

		try {
			XML = FileUtils.readFileToString(xml,Charset.forName("UTF-8"));
			XML_ASSINADO = FileUtils.readFileToString(xmlAssinado,Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test @Ignore
	public void serializar() throws NoSuchAlgorithmException{
		NotaFiscalSerializador serializador = 
				new NotaFiscalSerializador();
		assertXMLEquals(XML,serializador.serializar(NotaFiscalFixture.nfEmHomologacao()));
	}
	
	@Test @Ignore
	public void serializarComAssinatura(){
		NotaFiscalSerializador serializador = 
				new NotaFiscalSerializador(certificado);
		assertXMLEquals(XML_ASSINADO,serializador.serializar(NotaFiscalFixture.nfEmHomologacao()));
	}	
}
