package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.Exportacao;

public class ExportacaoXmlTest extends AbstractXmlTest{
	
	private static final String XML = 
			"<exporta>\r\n" + 
			"	<UFSaidaPais>SP</UFSaidaPais>\r\n" + 
			"	<xLocExporta>LOCAL DE EXPORTACAO</xLocExporta>\r\n" + 
			"	<xLocDespacho>LOCAL DE DESPACHO</xLocDespacho>\r\n" + 
			"</exporta>\r\n";
	
	private Exportacao exportacao;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("exporta", Exportacao.class);
	}
	
	@Test
	public void serializar(){
		exportacao = new Exportacao(Uf.SP, "LOCAL DE EXPORTACAO","LOCAL DE DESPACHO");
		assertXMLEquals(XML,toXML(exportacao));
	}
	
	@Test
	public void deserializar(){
		exportacao = new Exportacao(Uf.SP, "LOCAL DE EXPORTACAO","LOCAL DE DESPACHO");
		assertEquals(exportacao,fromXML(XML));
	}
	
}
