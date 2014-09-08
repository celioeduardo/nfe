package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;

public class UfTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("cUF", Uf.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals(
				"<cUF>SP</cUF>",
				xstream.toXML(Uf.SP));
	}
	
	@Test
	public void deserializar(){
		assertEquals(
				Uf.MG,
				xstream.fromXML("<cUF>MG</cUF>"));
	}
	
}
