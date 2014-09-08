package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.endereco.Cep;

public class CepTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("cep", Cep.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals("<cep>14020060</cep>",toXML(new Cep(14020060L)));
	}
	
	@Test
	public void deserializar(){
		assertEquals(new Cep(14020060L),fromXML("<cep>14020060</cep>"));
	}
	
}
