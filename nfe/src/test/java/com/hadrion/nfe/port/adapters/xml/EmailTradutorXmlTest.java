package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.tipos.Email;

public class EmailTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("email", Email.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals(
				"<email>hadrion@hadrion.com.br</email>",
				toXML(new Email("hadrion@hadrion.com.br")));
	}
	
	@Test
	public void deserializar(){
		assertEquals(
				new Email("hadrion@hadrion.com.br"),
				fromXML("<email>hadrion@hadrion.com.br</email>"));
	}
	
}
