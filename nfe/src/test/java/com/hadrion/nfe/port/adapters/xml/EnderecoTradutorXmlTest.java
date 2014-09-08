package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.endereco.Cep;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.endereco.Pais;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.tipos.Telefone;

public class EnderecoTradutorXmlTest extends AbstractXmlTest{
	
	private static final String XML = 
			"<enderEmit>\r\n" + 
			"	<xLgr>RODOVIA BR 020 KM 449</xLgr>\r\n" + 
			"	<nro>S/N</nro>\r\n" + 
			"	<xCpl>F.ASA BRAN</xCpl>\r\n" + 
			"	<xBairro>RODA VELHA</xBairro>\r\n" + 
			"	<cMun>2928901</cMun>\r\n" + 
			"	<xMun>SAO DESIDERIO</xMun>\r\n" + 
			"	<UF>BA</UF>\r\n" + 
			"	<CEP>47820000</CEP>\r\n" + 
			"	<cPais>1058</cPais>\r\n" + 
			"	<xPais>BRASIL</xPais>\r\n" + 
			"	<fone>7736842141</fone>\r\n" + 
			"</enderEmit>\r\n";
	
	private Endereco end;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("enderEmit", Endereco.class);
		end = new Endereco(
			"RODOVIA BR 020 KM 449", 
			"S/N",
			"F.ASA BRAN",
			"RODA VELHA",
		    new Municipio(2928901,"SAO DESIDERIO",Uf.BA),
		    Pais.BRASIL,
		    new Cep(47820000),
		    new Telefone("7736842141"));
	}
	
	@Test
	public void serializar(){
		assertXMLEquals(XML,toXML(end));
	}
	
	@Test
	public void deserializar(){
		assertEquals(end,fromXML(XML));
	}
	
}
