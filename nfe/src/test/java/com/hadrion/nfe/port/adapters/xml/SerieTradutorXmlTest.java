package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.Serie;

public class SerieTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("serie", Serie.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals(
				"<serie>1</serie>",
				xstream.toXML(new Serie(1L)));
	}
	
	@Test
	public void deserializar(){
		assertEquals(
				new Serie(999L),
				xstream.fromXML("<serie>999</serie>"));
	}
	
}
