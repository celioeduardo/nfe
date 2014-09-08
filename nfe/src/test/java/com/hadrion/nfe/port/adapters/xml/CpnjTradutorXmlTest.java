package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.tipos.Cnpj;

public class CpnjTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("cnpj", Cnpj.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals(
				"<cnpj>01107827000161</cnpj>",
				toXML(new Cnpj(1107827000161L)));
	}
	
	@Test
	public void deserializar(){
		assertEquals(
				new Cnpj(1107827000161L),
				fromXML("<cnpj>01107827000161</cnpj>"));
	}
	
}
