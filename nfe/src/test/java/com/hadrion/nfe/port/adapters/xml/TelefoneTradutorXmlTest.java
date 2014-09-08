package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.tipos.Telefone;

public class TelefoneTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("fone", Telefone.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals("<fone>1638779968</fone>",toXML(new Telefone("(16) 3877-9968")));
	}
	
	@Test
	public void deserializar(){
		assertEquals(new Telefone("1638779968"),fromXML("<fone>1638779968</fone>"));
	}
	
}
