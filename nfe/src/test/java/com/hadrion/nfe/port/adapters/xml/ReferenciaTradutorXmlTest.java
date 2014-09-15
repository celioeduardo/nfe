package com.hadrion.nfe.port.adapters.xml;

import static com.hadrion.util.DataUtil.data;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEstadual;

public class ReferenciaTradutorXmlTest extends AbstractXmlTest{
	
	private static final String XML_NFE =
			"<referencia>\r\n" +
			"	<NFref>\r\n" + 
			"		<refNFe>31131016832651000420550010000199361002699180</refNFe>\r\n" + 
			"	</NFref>\r\n"+
			"</referencia>\r\n";
	
	private static final String XML_1_1A =
			"<referencia>\r\n" +
			"	<NFref>\r\n" + 
			"		<refNF>\r\n" + 
			"			<cUF>31</cUF>\r\n" + 
			"			<AAMM>1305</AAMM>\r\n" + 
			"			<CNPJ>32750618000165</CNPJ>\r\n" + 
			"			<mod>1</mod>\r\n" + 
			"			<serie>3</serie>\r\n" + 
			"			<nNF>123456</nNF>\r\n" + 
			"		</refNF>\r\n" + 
			"	</NFref>\r\n" +
			"</referencia>\r\n";
	
	private static final String XML_PRODUTOR = 
			"<referencia>\r\n" +
			"	<NFref>\r\n" + 
			"		<refNFP>\r\n" + 
			"			<cUF>31</cUF>\r\n" + 
			"			<AAMM>1305</AAMM>\r\n" + 
			"			<CPF>56115316600</CPF>\r\n" + 
			"			<IE>ISENTO</IE>\r\n" + 
			"			<mod>4</mod>\r\n" + 
			"			<serie>2</serie>\r\n" + 
			"			<nNF>654321</nNF>\r\n" + 
			"		</refNFP>\r\n" + 
			"	</NFref>" +
			"</referencia>\r\n";
	
	private Referencia ref;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("referencia", Referencia.class);
	}
	
	@Test
	public void serializarNfe(){
		ref = Referencia.nfe(new ChaveAcesso("31131016832651000420550010000199361002699180"));
		assertXMLEquals(XML_NFE,toXML(ref));
	}
	
	@Test
	public void deserializarNfe(){
		ref = Referencia.nfe(new ChaveAcesso("31131016832651000420550010000199361002699180"));
		assertEquals(ref,fromXML(XML_NFE));
	}
	
	@Test
	public void serializarNf_1_1A(){
		ref = notaModelo1();
		assertXMLEquals(XML_1_1A,toXML(ref));
	}
	
	@Test
	public void deserializarNf_1_1A(){
		ref = notaModelo1();
		assertEquals(ref,fromXML(XML_1_1A));
	}
	@Test
	public void serializarNfProdutorRural(){
		ref = notaProdutorRural();
		printXML(ref);
		assertXMLEquals(XML_PRODUTOR,toXML(ref));
	}
	
	@Test
	public void deserializarNfProdutorRural(){
		ref = notaProdutorRural();
		assertEquals(ref,fromXML(XML_PRODUTOR));
	}
	
	private Referencia notaModelo1(){
		return ref = Referencia.modelo_1_1A(
				Modelo.MODELO_1, 
				Uf.MG, data("01/05/13"), 
				new Cnpj(32750618000165L), 
				new Serie(3), 123456L);
	}
	private Referencia notaProdutorRural(){
		return ref = Referencia.produtorRural(
				Uf.MG,data("01/05/13"),new Cpf(56115316600L), new Serie(2), 654321L, InscricaoEstadual.ISENTO);
	}
		
}
