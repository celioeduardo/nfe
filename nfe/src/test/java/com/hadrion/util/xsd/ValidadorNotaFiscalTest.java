package com.hadrion.util.xsd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.hadrion.nfe.port.adapters.xml.nf.ValidadorNotaFiscal;

public class ValidadorNotaFiscalTest {
	private Validador validador;
	
	@Test
	public void validarOk() throws SAXException, IOException{
		Source source = new StreamSource(arquivoXmlOk());
		validador = new ValidadorNotaFiscal(source);
		assertFalse(validador.temErros());
		assertEquals(0,validador.quantidadeErros());
	}
	
	@Test
	public void validarNok() throws SAXException, IOException{
		Source source = new StreamSource(arquivoXmlNok());
		validador = new ValidadorNotaFiscal(source);
		assertTrue(validador.temErros());
        assertEquals(27,validador.quantidadeErros());
	}
	
	private File arquivoXmlOk(){
		return FileUtils.getFile("src","test","resources","xsd","nf-ok.xml");
	}
	private File arquivoXmlNok(){
		return FileUtils.getFile("src","test","resources","xsd","nf-nok.xml");
	}
}
