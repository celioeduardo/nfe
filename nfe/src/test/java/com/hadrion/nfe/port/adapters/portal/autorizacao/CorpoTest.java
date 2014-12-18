package com.hadrion.nfe.port.adapters.portal.autorizacao;

import static com.hadrion.util.xml.XmlTestUtil.assertXMLEquals;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.lote.GeracaoLoteService;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalFixture;
import com.hadrion.nfe.port.adapters.portal.autorizacao.Corpo;

public class CorpoTest extends DominioTest {
	@Autowired
	private GeracaoLoteService geracaoLoteService;
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
		nf = NotaFiscalFixture.nfEmHomologacao();
		nf.emitida();
		lote = geracaoLoteService.gerarLoteEmHomologacao(nf);
	}
	
	@Test
	public void corpoAutorizacao(){
		Corpo corpo = new Corpo(lote,Collections.singleton(nf),certificado);
		assertXMLEquals(XML, corpo.gerarComoString());
	}
	
	private String xml(){
		final File xml = FileUtils.getFile("src","test","resources","ws","Autorizacao-nfeDadosMsg.xml");
		
		try {
			return FileUtils.readFileToString(xml);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
