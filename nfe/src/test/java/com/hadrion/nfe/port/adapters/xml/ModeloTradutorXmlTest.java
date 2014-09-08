package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.Modelo;

public class ModeloTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("mod", Modelo.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals(
				"<mod>55</mod>",
				xstream.toXML(new Modelo("55")));
	}
	
	@Test
	public void deserializar(){
		assertEquals(
				new Modelo("55"),
				xstream.fromXML("<mod>55</mod>"));
	}
	
}
