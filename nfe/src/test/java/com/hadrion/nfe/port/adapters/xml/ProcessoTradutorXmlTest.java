package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.Processo;

public class ProcessoTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("procEmi", Processo.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals("<procEmi>0</procEmi>",xstream.toXML(Processo.APLICATIVO_CONTRIBUINTE));
		assertXMLEquals("<procEmi>1</procEmi>",xstream.toXML(Processo.AVULSA_FISCO));
		assertXMLEquals("<procEmi>2</procEmi>",xstream.toXML(Processo.AVULSA_CONTRIBUINTE));
		assertXMLEquals("<procEmi>3</procEmi>",xstream.toXML(Processo.APLICATIVO_FISCO));
	}
	
	@Test
	public void deserializar(){
		assertEquals(Processo.APLICATIVO_CONTRIBUINTE,xstream.fromXML("<procEmi>0</procEmi>"));
		assertEquals(Processo.AVULSA_FISCO,xstream.fromXML("<procEmi>1</procEmi>"));
		assertEquals(Processo.AVULSA_CONTRIBUINTE,xstream.fromXML("<procEmi>2</procEmi>"));
		assertEquals(Processo.APLICATIVO_FISCO,xstream.fromXML("<procEmi>3</procEmi>"));
	}
	
}
