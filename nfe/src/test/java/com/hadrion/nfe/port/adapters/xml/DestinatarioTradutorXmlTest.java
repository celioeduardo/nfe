package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.endereco.Cep;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.endereco.Pais;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.publico.Destinatario;
import com.hadrion.nfe.dominio.modelo.nf.publico.IndicadorIe;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Email;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Telefone;

public class DestinatarioTradutorXmlTest extends AbstractXmlTest{
	
	private static final String XML = 
			"<dest>\r\n" + 
			"	<CNPJ>99999999000191</CNPJ>\r\n" + 
			"	<xNome>HADRION SISTEMAS INTEGRADOS LTDA</xNome>\r\n" + 
			"	<enderDest>\r\n" + 
			"		<xLgr>RUA CASEMIRO DE ABREU</xLgr>\r\n" + 
			"		<nro>256</nro>\r\n" + 
			"		<xBairro>VILA SEIXAS</xBairro>\r\n" + 
			"		<cMun>3543402</cMun>\r\n" + 
			"		<xMun>RIBEIRAO PRETO</xMun>\r\n" + 
			"		<UF>SP</UF>\r\n" + 
			"		<CEP>14020060</CEP>\r\n" + 
			"		<cPais>1058</cPais>\r\n" + 
			"		<xPais>BRASIL</xPais>\r\n" + 
			"		<fone>1639164500</fone>\r\n" + 
			"	</enderDest>\r\n" + 
			"	<indIEDest>2</indIEDest>\r\n" + 
			"	<IE>123456</IE>\r\n" +
			"	<ISUF>12345678</ISUF>\r\n" + 
			"	<email>hadrion@hadrion.com.br</email>\r\n" + 
			"</dest>\r\n";
	
	private Destinatario destinatario;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("dest", Destinatario.class);
		destinatario = new Destinatario(
			new Cnpj(99999999000191L),
			null,
			"",
			"HADRION SISTEMAS INTEGRADOS LTDA",
			null,
			new Endereco(
				"RUA CASEMIRO DE ABREU", 
				"256",
				null,
				"VILA SEIXAS",
			    new Municipio(3543402,"RIBEIRAO PRETO",Uf.SP),
			    Pais.BRASIL,
			    new Cep(14020060),
			    new Telefone("1639164500")),
			null,
			IndicadorIe.ISENTO,
			new InscricaoEstadual("123456"),
			12345678L,
			new Email("hadrion@hadrion.com.br"));
	}
	
	@Test
	public void serializar(){
		assertXMLEquals(XML,toXML(destinatario));
	}
	
	@Test
	public void deserializar(){
		assertEquals(destinatario,fromXML(XML));
	}
	
}
