package com.hadrion.nfe.port.adapters.portal.ws;

import static com.hadrion.util.xml.XmlTestUtil.assertXMLEquals;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalFixture;

public class CorpoTest extends DominioTest {
	private Certificado certificado;
	
	private String XML;
	private Lote lote;
	private NotaFiscal nf;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	
		certificado = new Certificado(
				FileUtils.getFile("src","test","resources","assinatura","certificado.pfx"), 
				"12345678");
		
		XML = xml();
		nf = NotaFiscalFixture.nf();
		nf.emitidaHomologacao();
		lote = Lote.gerarEmHomologacao(nf);
	}
	
	@Test
	public void corpoAutorizacao(){
		Corpo corpo = new Corpo(lote,Collections.singleton(nf),certificado);
		assertXMLEquals(XML, corpo.autorizacao());
	}
	
	private String xml(){
		final File xml = FileUtils.getFile("src","test","resources","ws","nfeDadosMsg.xml");
		
		try {
			return FileUtils.readFileToString(xml);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
