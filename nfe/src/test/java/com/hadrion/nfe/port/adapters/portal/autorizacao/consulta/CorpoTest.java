package com.hadrion.nfe.port.adapters.portal.autorizacao.consulta;

import static com.hadrion.util.xml.XmlTestUtil.assertXMLEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.lote.GeracaoLoteService;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.NumeroReciboLote;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalFixture;

public class CorpoTest extends DominioTest {
	@Autowired
	private GeracaoLoteService geracaoLoteService;
	
	private String XML;
	private Lote lote;
	private NotaFiscal nf;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	
		XML = xml();
		nf = NotaFiscalFixture.nfEmHomologacao();
		nf.emitida();
		lote = geracaoLoteService.gerarLoteEmHomologacao(nf);
		lote.transmitido(new NumeroReciboLote("310000035995659"));
	}
	
	@Test
	public void corpoRetornoAutorizacao(){
		Corpo corpo = new Corpo(lote);
		assertXMLEquals(XML, corpo.gerar());
	}
	
	private String xml(){
		final File xml = FileUtils.getFile("src","test","resources","ws","RetAutorizacao-nfeDadosMsg.xml");
		
		try {
			return FileUtils.readFileToString(xml);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
