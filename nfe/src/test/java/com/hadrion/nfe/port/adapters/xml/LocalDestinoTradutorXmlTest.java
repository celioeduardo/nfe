package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;

public class LocalDestinoTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("idDest", LocalDestino.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals("<idDest>1</idDest>",xstream.toXML(LocalDestino.INTERNA));
		assertXMLEquals("<idDest>2</idDest>",xstream.toXML(LocalDestino.INTERESTADUAL));
		assertXMLEquals("<idDest>3</idDest>",xstream.toXML(LocalDestino.EXTERIOR));
	}
	
	@Test
	public void deserializar(){
		assertEquals(LocalDestino.INTERNA,xstream.fromXML("<idDest>1</idDest>"));
		assertEquals(LocalDestino.INTERESTADUAL,xstream.fromXML("<idDest>2</idDest>"));
		assertEquals(LocalDestino.EXTERIOR,xstream.fromXML("<idDest>3</idDest>"));
	}
	
}
