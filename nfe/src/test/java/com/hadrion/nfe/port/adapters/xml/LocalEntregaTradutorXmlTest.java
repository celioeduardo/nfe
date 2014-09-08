package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalEntrega;
import com.hadrion.nfe.tipos.Cnpj;

public class LocalEntregaTradutorXmlTest extends AbstractXmlTest{
	
	private static final String XML = 
			"<entrega>\r\n" + 
			"	<CNPJ>86675642000106</CNPJ>\r\n" + 
			"	<xLgr>RODOVIA BR 020 KM 449</xLgr>\r\n" + 
			"	<nro>S/N</nro>\r\n" + 
			"	<xCpl>F.ASA BRAN</xCpl>\r\n" + 
			"	<xBairro>RODA VELHA</xBairro>\r\n" + 
			"	<cMun>2928901</cMun>\r\n" + 
			"	<xMun>SAO DESIDERIO</xMun>\r\n" + 
			"	<UF>BA</UF>\r\n" + 
			"</entrega>";
	
	private LocalEntrega local;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("entrega", LocalEntrega.class);
		local = new LocalEntrega(
			new Cnpj(86675642000106L),
			null,
			new Endereco(
				"RODOVIA BR 020 KM 449", 
				"S/N",
				"F.ASA BRAN",
				"RODA VELHA",
			    new Municipio(2928901,"SAO DESIDERIO",Uf.BA),
			    null,
			    null,
			    null));
	}
	
	@Test
	public void serializar(){
		assertXMLEquals(XML,toXML(local));
	}
	
	@Test
	public void deserializar(){
		assertEquals(local,fromXML(XML));
	}
	
}
