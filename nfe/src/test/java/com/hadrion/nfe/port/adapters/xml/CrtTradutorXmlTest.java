package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.publico.Crt;

public class CrtTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("crt", Crt.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals("<crt>1</crt>",xstream.toXML(Crt.SIMPLES_NACIONAL));
		assertXMLEquals("<crt>2</crt>",xstream.toXML(Crt.SIMPLES_NACIONAL_EXCESSO));
		assertXMLEquals("<crt>3</crt>",xstream.toXML(Crt.REGIME_NORMAL));
	}
	
	@Test
	public void deserializar(){
		assertEquals(Crt.SIMPLES_NACIONAL,xstream.fromXML("<crt>1</crt>"));
		assertEquals(Crt.SIMPLES_NACIONAL_EXCESSO,xstream.fromXML("<crt>2</crt>"));
		assertEquals(Crt.REGIME_NORMAL,xstream.fromXML("<crt>3</crt>"));
	}
	
}
