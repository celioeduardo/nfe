package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

public class QuantidadeTradutorXmlTest extends AbstractXmlTest{
	
	NumberFormat fmt;
	
	@Before
	public void setUp() {
		super.setUp();
		fmt = new DecimalFormat("#0.0000",DecimalFormatSymbols.getInstance(Locale.US));
	}
	
	@Test
	public void formatoDecimal(){
		assertEquals("12.1234", fmt.format(12.1234));
		assertEquals("0.1234", fmt.format(0.1234));
		assertEquals("0.1230", fmt.format(0.123));
		assertEquals("0.1200", fmt.format(0.12));
		assertEquals("0.1000", fmt.format(0.1));
		assertEquals("0.0000", fmt.format(0));
		
		assertEquals("1000.0000", fmt.format(1000));
		assertEquals("10000.0000", fmt.format(10000));
		
		assertEquals("36620.0000", fmt.format(36620.0000));
		assertEquals("36620.0000", fmt.format(36620));
	}
	
//	@Test
//	public void serializar(){
//		assertXMLEquals("<idDest>1</idDest>",xstream.toXML(LocalDestino.INTERNA));
//		assertXMLEquals("<idDest>2</idDest>",xstream.toXML(LocalDestino.INTERESTADUAL));
//		assertXMLEquals("<idDest>3</idDest>",xstream.toXML(LocalDestino.EXTERIOR));
//	}
//	
//	@Test
//	public void deserializar(){
//		assertEquals(LocalDestino.INTERNA,xstream.fromXML("<idDest>1</idDest>"));
//		assertEquals(LocalDestino.INTERESTADUAL,xstream.fromXML("<idDest>2</idDest>"));
//		assertEquals(LocalDestino.EXTERIOR,xstream.fromXML("<idDest>3</idDest>"));
//	}
	
}
