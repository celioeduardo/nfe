package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;

public class MunicipioTradutorXmlTest extends AbstractXmlTest{
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("mun", Municipio.class);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals(
				"<mun><cMun>2928901</cMun><xMun>SAO DESIDERIO</xMun><UF>BA</UF></mun>",
				toXML(new Municipio(2928901, "SAO DESIDERIO", Uf.BA)));
	}
	
	@Test
	public void deserializar(){
		assertEquals(new Municipio(2928901, "SAO DESIDERIO", Uf.BA),
				fromXML("<mun><cMun>2928901</cMun><xMun>SAO DESIDERIO</xMun><UF>BA</UF></mun>"));
	}
	
}
