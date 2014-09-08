package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;

public class TipoOperacaoTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("tpNF", TipoOperacao.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals("<tpNF>0</tpNF>",xstream.toXML(TipoOperacao.ENTRADA));
		assertXMLEquals("<tpNF>1</tpNF>",xstream.toXML(TipoOperacao.SAIDA));
	}
	
	@Test
	public void deserializar(){
		assertEquals(TipoOperacao.ENTRADA,xstream.fromXML("<tpNF>0</tpNF>"));
		assertEquals(TipoOperacao.SAIDA,xstream.fromXML("<tpNF>1</tpNF>"));
	}
	
}
