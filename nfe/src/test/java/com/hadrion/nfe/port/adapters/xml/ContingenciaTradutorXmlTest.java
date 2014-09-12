package com.hadrion.nfe.port.adapters.xml;

import static com.hadrion.util.DataUtil.dataHora;
import static org.junit.Assert.assertEquals;

import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.Contingencia;

public class ContingenciaTradutorXmlTest extends AbstractXmlTest{
	
	private static final String XML = 
			"<cont>\r\n" + 
			"	<dhCont>2014-09-12T16:07:00-03:00</dhCont>\r\n" + 
			"	<xJust>Justificativa da entrada em contingência</xJust>\r\n" + 
			"</cont>\r\n";
	
	private Contingencia contingencia;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("cont", Contingencia.class);
		contingencia = new Contingencia(
				dataHora("12/09/14 16:07:00",TimeZone.getTimeZone("GMT-03:00")), 
				"Justificativa da entrada em contingência");
	}
	
	@Test
	public void serializar(){
		assertXMLEquals(XML,toXML(contingencia));
	}
	
	@Test
	public void deserializar(){
		assertEquals(contingencia,fromXML(XML));
	}
	
}