package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.FormaPagamento;

public class FormaPagamentoTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("indPag", FormaPagamento.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals("<indPag>0</indPag>",xstream.toXML(FormaPagamento.A_VISTA));
		assertXMLEquals("<indPag>1</indPag>",xstream.toXML(FormaPagamento.A_PRAZO));
		assertXMLEquals("<indPag>2</indPag>",xstream.toXML(FormaPagamento.OUTROS));
	}
	
	@Test
	public void deserializar(){
		assertEquals(FormaPagamento.A_VISTA,xstream.fromXML("<indPag>0</indPag>"));
		assertEquals(FormaPagamento.A_PRAZO,xstream.fromXML("<indPag>1</indPag>"));
		assertEquals(FormaPagamento.OUTROS,xstream.fromXML("<indPag>2</indPag>"));
	}
	
}
