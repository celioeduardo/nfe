package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.FormatoDanfe;

public class FormatoDanfeTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("tpImp", FormatoDanfe.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals("<tpImp>0</tpImp>",xstream.toXML(FormatoDanfe.SEM_GERACAO));
		assertXMLEquals("<tpImp>1</tpImp>",xstream.toXML(FormatoDanfe.RETRATO));
		assertXMLEquals("<tpImp>2</tpImp>",xstream.toXML(FormatoDanfe.PAISAGEM));
		assertXMLEquals("<tpImp>3</tpImp>",xstream.toXML(FormatoDanfe.SIMPLIFICADO));
		assertXMLEquals("<tpImp>4</tpImp>",xstream.toXML(FormatoDanfe.CONSUMIDOR));
		assertXMLEquals("<tpImp>5</tpImp>",xstream.toXML(FormatoDanfe.CONSUMIDOR_MSG));
	}
	
	@Test
	public void deserializar(){
		assertEquals(FormatoDanfe.SEM_GERACAO,xstream.fromXML("<tpImp>0</tpImp>"));
		assertEquals(FormatoDanfe.RETRATO,xstream.fromXML("<tpImp>1</tpImp>"));
		assertEquals(FormatoDanfe.PAISAGEM,xstream.fromXML("<tpImp>2</tpImp>"));
		assertEquals(FormatoDanfe.SIMPLIFICADO,xstream.fromXML("<tpImp>3</tpImp>"));
		assertEquals(FormatoDanfe.CONSUMIDOR,xstream.fromXML("<tpImp>4</tpImp>"));
		assertEquals(FormatoDanfe.CONSUMIDOR_MSG,xstream.fromXML("<tpImp>5</tpImp>"));
		
	}
	
}
