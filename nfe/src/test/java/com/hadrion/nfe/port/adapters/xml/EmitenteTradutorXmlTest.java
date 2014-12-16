package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.endereco.Cep;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.endereco.Pais;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.publico.Crt;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Telefone;

public class EmitenteTradutorXmlTest extends AbstractXmlTest{
	
	private static final String XML = 
			"<emit>\r\n" + 
			"	<CNPJ>00891206000310</CNPJ>\r\n" + 
			"	<xNome>NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL</xNome>\r\n" + 
			"	<xFant>COOPROESTE</xFant>\r\n" + 
			"	<enderEmit>\r\n" + 
			"		<xLgr>RODOVIA BR 020 KM 449</xLgr>\r\n" + 
			"		<nro>S/N</nro>\r\n" + 
			"		<xCpl>F.ASA BRAN</xCpl>\r\n" + 
			"		<xBairro>RODA VELHA</xBairro>\r\n" + 
			"		<cMun>2928901</cMun>\r\n" + 
			"		<xMun>SAO DESIDERIO</xMun>\r\n" + 
			"		<UF>BA</UF>\r\n" + 
			"		<CEP>47820000</CEP>\r\n" + 
			"		<cPais>1058</cPais>\r\n" + 
			"		<xPais>BRASIL</xPais>\r\n" + 
			"		<fone>7736842141</fone>\r\n" + 
			"	</enderEmit>\r\n" + 
			"	<IE>43619043</IE>\r\n" + 
			"	<CRT>3</CRT>\r\n" + 
			"</emit>\r\n";
	
	private Emitente emitente;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("emit", Emitente.class);
		xstream.registerConverter(new EmitenteConverter());
		
		emitente = new Emitente(
			new Cnpj(891206000310L),
			null,
			"NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL",
			"COOPROESTE",
			new Endereco(
				"RODOVIA BR 020 KM 449", 
				"S/N",
				"F.ASA BRAN",
				"RODA VELHA",
			    new Municipio(2928901,"SAO DESIDERIO",Uf.BA),
			    Pais.BRASIL,
			    new Cep(47820000),
			    new Telefone("7736842141")),
			null,
			new InscricaoEstadual("43619043"),
			null,
			Crt.REGIME_NORMAL);
	}
	
	@Test
	public void serializar(){
		assertXMLEquals(XML,toXML(emitente));
	}
	
	@Test
	public void deserializar(){
		assertEquals(emitente,fromXML(XML));
	}
	
}
