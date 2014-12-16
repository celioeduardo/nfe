package com.hadrion.util.xsd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.xml.sax.SAXException;

public class ValidadorLoteTest {
	private Validador validador;
	
	@Test
	public void validarOk() throws SAXException, IOException{
		Source source = new StreamSource(arquivoXmlOk());
		validador = new Validador(arquivoXsd(),source);
		assertFalse(validador.temErros());
		assertEquals(0,validador.quantidadeErros());
	}
	
	private File arquivoXsd(){
		return FileUtils.getFile("src","main","resources","xsd","PL_008e","enviNFe_v3.10.xsd");
	}
	private File arquivoXmlOk(){
		return FileUtils.getFile("src","test","resources","xsd","lote-ok.xml");
	}
	
}
