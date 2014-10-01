package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.item.Cfop;
import com.hadrion.nfe.dominio.modelo.nf.item.Cide;
import com.hadrion.nfe.dominio.modelo.nf.item.Combustivel;
import com.hadrion.nfe.dominio.modelo.nf.item.DescritorProduto;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoItem;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoIndireta;
import com.hadrion.nfe.dominio.modelo.nf.item.Gtin;
import com.hadrion.nfe.dominio.modelo.nf.item.Ncm;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Quantidade;

public class DescritorProdutoTradutorXmlTest extends AbstractXmlTest{
	
	private static final String XML = 
			"<prod>\r\n" + 
			"	<cProd>00000000000000009001</cProd>\r\n" + 
			"	<cEAN>12345678</cEAN>\r\n" + 
			"	<xProd>SOJA EM GRAOS DEPOSITO</xProd>\r\n" + 
			"	<NCM>12010010</NCM>\r\n" +
			"	<NVE>ABCDEF</NVE>\r\n" +
			"	<EXTIPI>123</EXTIPI>\r\n" +
			"	<CFOP>5906</CFOP>\r\n" + 
			"	<uCom>KG</uCom>\r\n" + 
			"	<qCom>36620.0000</qCom>\r\n" + 
			"	<vUnCom>1.0</vUnCom>\r\n" + 
			"	<vProd>36620.00</vProd>\r\n" + 
			"	<cEANTrib>12345678</cEANTrib>\r\n" + 
			"	<uTrib>KG</uTrib>\r\n" + 
			"	<qTrib>36620.0000</qTrib>\r\n" + 
			"	<vUnTrib>1.0</vUnTrib>\r\n" +
			"	<vFrete>100.00</vFrete>\r\n" +
			"	<vSeg>99.00</vSeg>\r\n" +
			"	<vDesc>88.00</vDesc>\r\n" +
			"	<vOutro>77.00</vOutro>\r\n" +
			"	<indTot>1</indTot>\r\n" +
			"	<detExport>\r\n" + 
			"		<nDraw>12345678901</nDraw>\r\n" + 
			"		<exportInd>\r\n" + 
			"			<nRE>123456789012</nRE>\r\n" + 
			"			<chNFe>29140600891206000310550010000110017000481161</chNFe>\r\n" + 
			"			<qExport>50.1234</qExport>\r\n" + 
			"		</exportInd>\r\n" + 
			"	</detExport>" +
			"	<comb>\r\n" + 
			"		<cProdANP>123456789</cProdANP>\r\n" + 
			"		<qTemp>568.1234</qTemp>\r\n" + 
			"		<UFCons>SP</UFCons>\r\n" + 
			"		<CIDE>\r\n" + 
			"			<qBCProd>500.78</qBCProd>\r\n" + 
			"			<vAliqProd>18.0000</vAliqProd>\r\n" + 
			"			<vCIDE>90.14</vCIDE>\r\n" + 
			"		</CIDE>\r\n" + 
			"	</comb>" +
			"</prod>\r\n";
	
	private DescritorProduto produto;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("prod", DescritorProduto.class);
		produto = new DescritorProduto(
			"00000000000000009001",
			new Gtin(12345678L),
			"SOJA EM GRAOS DEPOSITO",
			new Ncm(12010010L),
			"ABCDEF",
			"123",
			new Cfop(5906L),
			"KG",
			new Quantidade(36620.0),
			1.0,
			new Dinheiro(36620.0),
			new Gtin(12345678L),
			"KG",
			new Quantidade(36620.0),
			1.0,
			new Dinheiro(100.0),
			new Dinheiro(99.0),
			new Dinheiro(88.0),
			new Dinheiro(77.0),
			new ExportacaoItem(12345678901L, 
					new ExportacaoIndireta(
							123456789012L, 
							new ChaveAcesso("29140600891206000310550010000110017000481161"),
							new Quantidade(50.1234))),
			new Combustivel(123456789L, new Quantidade(568.1234), Uf.SP, 
					new Cide(new Dinheiro(500.78), new Aliquota(18.0), new Dinheiro(90.14))));
	}
	
	@Test
	public void serializar(){
		assertXMLEquals(XML,toXML(produto));
	}
	
	@Test
	public void deserializar(){
		assertEquals(produto,fromXML(XML));
	}
	
}