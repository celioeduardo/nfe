package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.tipos.Cpf;

public class CpfTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("cpf", Cpf.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals(
				"<cpf>09240038639</cpf>",
				xstream.toXML(new Cpf(9240038639L)));
	}
	
	@Test
	public void deserializar(){
		assertEquals(
				new Cpf(9240038639L),
				xstream.fromXML("<cpf>09240038639</cpf>"));
	}
	
}
