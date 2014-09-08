package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.Finalidade;

public class FinalidadeTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("finNFe", Finalidade.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals("<finNFe>1</finNFe>",xstream.toXML(Finalidade.NORMAL));
		assertXMLEquals("<finNFe>2</finNFe>",xstream.toXML(Finalidade.COMPLEMENTAR));
		assertXMLEquals("<finNFe>3</finNFe>",xstream.toXML(Finalidade.AJUSTE));
		assertXMLEquals("<finNFe>4</finNFe>",xstream.toXML(Finalidade.DEVOLUCAO_RETORNO));
	}
	
	@Test
	public void deserializar(){
		assertEquals(Finalidade.NORMAL,xstream.fromXML("<finNFe>1</finNFe>"));
		assertEquals(Finalidade.COMPLEMENTAR,xstream.fromXML("<finNFe>2</finNFe>"));
		assertEquals(Finalidade.AJUSTE,xstream.fromXML("<finNFe>3</finNFe>"));
		assertEquals(Finalidade.DEVOLUCAO_RETORNO,xstream.fromXML("<finNFe>4</finNFe>"));
	}
	
}
