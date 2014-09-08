package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.tipos.InscricaoEstadual;

public class InscricaoEstadualTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("IE", InscricaoEstadual.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals("<IE>43619043</IE>",toXML(new InscricaoEstadual("43619043")));
		assertXMLEquals("<IE>43619043</IE>",toXML(new InscricaoEstadual("4.361.9-0/43")));
	}
	
	@Test
	public void deserializar(){
		assertEquals(new InscricaoEstadual("43619043"),fromXML("<IE>43619043</IE>"));
	}
	
}
