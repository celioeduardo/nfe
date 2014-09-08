package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.endereco.Pais;

public class PaisTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("pais", Pais.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals("<pais><cPais>1058</cPais><xPais>BRASIL</xPais></pais>",
				toXML(new Pais(1058L, "BRASIL")));
	}
	
	@Test
	public void deserializar(){
		assertEquals(new Pais(1058L, "BRASIL"),
				fromXML("<pais><cPais>1058</cPais><xPais>BRASIL</xPais></pais>"));
	}
	
}
