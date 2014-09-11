package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.cofins.CstCofins;
import com.hadrion.nfe.dominio.modelo.cofins.Cofins;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;

public class CofinsTradutorXmlTest extends AbstractXmlTest{
	private Cofins cofins;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("COFINS", Cofins.class);
	}
	
	@Test
	public void serializarCofins01(){
		String XML = 
			"<COFINS>\r\n" + 
			"	<COFINSAliq>\r\n" + 
			"		<CST>01</CST>\r\n" + 
			"		<vBC>1000.00</vBC>\r\n" + 
			"		<pCOFINS>1.65</pCOFINS>\r\n" + 
			"		<vCOFINS>16.50</vCOFINS>\r\n" + 
			"	</COFINSAliq>\r\n" + 
			"</COFINS>";
		cofins = new Cofins(CstCofins.CST_01,new Dinheiro(1000),new Aliquota(1.65),null,null);
		assertXMLEquals(XML,toXML(cofins));
	}
	
	@Test
	public void deserializarCofins01(){
		String XML = 
				"<COFINS>\r\n" + 
						"	<COFINSAliq>\r\n" + 
						"		<CST>01</CST>\r\n" + 
						"		<vBC>1000.00</vBC>\r\n" + 
						"		<pCOFINS>1.65</pCOFINS>\r\n" + 
						"		<vCOFINS>16.50</vCOFINS>\r\n" + 
						"	</COFINSAliq>\r\n" + 
						"</COFINS>";
		cofins = new CofinsConvertido(
				CstCofins.CST_01,new Dinheiro(1000),new Aliquota(1.65),null,null,new Dinheiro(16.5));
		assertEquals(cofins,fromXML(XML));
	}
	
	@Test
	public void serializarCofins02(){
		String XML = 
			"<COFINS>\r\n" + 
			"	<COFINSAliq>\r\n" + 
			"		<CST>02</CST>\r\n" + 
			"		<vBC>1000.00</vBC>\r\n" + 
			"		<pCOFINS>1.65</pCOFINS>\r\n" + 
			"		<vCOFINS>16.50</vCOFINS>\r\n" + 
			"	</COFINSAliq>\r\n" + 
			"</COFINS>";
		cofins = new Cofins(CstCofins.CST_02,new Dinheiro(1000),new Aliquota(1.65),null,null);
		assertXMLEquals(XML,toXML(cofins));
	}
	
	@Test
	public void deserializarCofins02(){
		String XML = 
				"<COFINS>\r\n" + 
				"	<COFINSAliq>\r\n" + 
				"		<CST>02</CST>\r\n" + 
				"		<vBC>1000.00</vBC>\r\n" + 
				"		<pCOFINS>1.65</pCOFINS>\r\n" + 
				"		<vCOFINS>16.50</vCOFINS>\r\n" + 
				"	</COFINSAliq>\r\n" + 
				"</COFINS>";
		cofins = new CofinsConvertido(
				CstCofins.CST_02,new Dinheiro(1000),new Aliquota(1.65),null,null,new Dinheiro(16.5));
		assertEquals(cofins,fromXML(XML));
	}
	
	@Test
	public void serializarCofins03(){
		String XML = 
			"<COFINS>\r\n" + 
			"	<COFINSQtde>\r\n" + 
			"		<CST>03</CST>\r\n" + 
			"		<qBCProd>56.0000</qBCProd>\r\n" + 
			"		<vAliqProd>10.0000</vAliqProd>\r\n" + 
			"		<vCOFINS>560.00</vCOFINS>\r\n" + 
			"	</COFINSQtde>\r\n" + 
			"</COFINS>";
		cofins = new Cofins(CstCofins.CST_03,null,null,56.0,10.0);
		assertXMLEquals(XML,toXML(cofins));
	}
	
	@Test
	public void deserializarCofins03(){
		String XML = 
				"<COFINS>\r\n" + 
				"	<COFINSQtde>\r\n" + 
				"		<CST>03</CST>\r\n" + 
				"		<qBCProd>56.0000</qBCProd>\r\n" + 
				"		<vAliqProd>10.0000</vAliqProd>\r\n" + 
				"		<vCOFINS>560.00</vCOFINS>\r\n" + 
				"	</COFINSQtde>\r\n" + 
				"</COFINS>";
		cofins = new CofinsConvertido(CstCofins.CST_03,null,null,56.0,10.0,new Dinheiro(560.0));
		assertEquals(cofins,fromXML(XML));
	}
	
	@Test
	public void serializarCofins04(){
		String XML = "<COFINS><COFINSNT><CST>04</CST></COFINSNT></COFINS>";
		assertXMLEquals(XML,toXML(new Cofins(CstCofins.CST_04,null,null,null,null)));
	}
	@Test
	public void deserializarCofins04(){
		String XML = "<COFINS><COFINSNT><CST>04</CST></COFINSNT></COFINS>";
		cofins = new CofinsConvertido(CstCofins.CST_04,null,null,null,null,null);
		assertEquals(cofins,fromXML(XML));
	}
	@Test
	public void serializarCofins05(){
		String XML = "<COFINS><COFINSNT><CST>05</CST></COFINSNT></COFINS>";
		assertXMLEquals(XML,toXML(new Cofins(CstCofins.CST_05,null,null,null,null)));
	}
	@Test
	public void deserializarCofins05(){
		String XML = "<COFINS><COFINSNT><CST>05</CST></COFINSNT></COFINS>";
		cofins = new CofinsConvertido(CstCofins.CST_05,null,null,null,null,null);
		assertEquals(cofins,fromXML(XML));
	}
	@Test
	public void serializarCofins06(){
		String XML = "<COFINS><COFINSNT><CST>06</CST></COFINSNT></COFINS>";
		assertXMLEquals(XML,toXML(new Cofins(CstCofins.CST_06,null,null,null,null)));
	}
	@Test
	public void deserializarCofins06(){
		String XML = "<COFINS><COFINSNT><CST>06</CST></COFINSNT></COFINS>";
		cofins = new CofinsConvertido(CstCofins.CST_06,null,null,null,null,null);
		assertEquals(cofins,fromXML(XML));
	}
	@Test
	public void serializarCofins07(){
		String XML = "<COFINS><COFINSNT><CST>07</CST></COFINSNT></COFINS>";
		assertXMLEquals(XML,toXML(new Cofins(CstCofins.CST_07,null,null,null,null)));
	}
	@Test
	public void deserializarCofins07(){
		String XML = "<COFINS><COFINSNT><CST>07</CST></COFINSNT></COFINS>";
		cofins = new CofinsConvertido(CstCofins.CST_07,null,null,null,null,null);
		assertEquals(cofins,fromXML(XML));
	}
	@Test
	public void serializarCofins08(){
		String XML = "<COFINS><COFINSNT><CST>08</CST></COFINSNT></COFINS>";
		assertXMLEquals(XML,toXML(new Cofins(CstCofins.CST_08,null,null,null,null)));
	}
	@Test
	public void deserializarCofins08(){
		String XML = "<COFINS><COFINSNT><CST>08</CST></COFINSNT></COFINS>";
		cofins = new CofinsConvertido(CstCofins.CST_08,null,null,null,null,null);
		assertEquals(cofins,fromXML(XML));
	}
	@Test
	public void serializarCofins09(){
		String XML = "<COFINS><COFINSNT><CST>09</CST></COFINSNT></COFINS>";
		assertXMLEquals(XML,toXML(new Cofins(CstCofins.CST_09,null,null,null,null)));
	}
	@Test
	public void deserializarCofins09(){
		String XML = "<COFINS><COFINSNT><CST>09</CST></COFINSNT></COFINS>";
		cofins = new CofinsConvertido(CstCofins.CST_09,null,null,null,null,null);
		assertEquals(cofins,fromXML(XML));
	}
	@Test
	public void serializarCofins99(){
		String XML = 
				"<COFINS>\r\n" + 
				"	<COFINSOutr>\r\n" + 
				"		<CST>99</CST>\r\n" + 
				"		<vBC>0.00</vBC>\r\n" + 
				"		<pCOFINS>0.00</pCOFINS>\r\n" + 
				"		<vCOFINS>0.00</vCOFINS>\r\n" + 
				"	</COFINSOutr>\r\n" + 
				"</COFINS>";
		cofins = new Cofins(CstCofins.CST_99,Dinheiro.ZERO,Aliquota.ZERO,0.0,0.0);
		assertXMLEquals(XML,toXML(cofins));
	}
	
	@Test
	public void deserializarCofins99(){
		String XML = 
				"<COFINS>\r\n" + 
				"	<COFINSOutr>\r\n" + 
				"		<CST>99</CST>\r\n" + 
				"		<vBC>0.00</vBC>\r\n" + 
				"		<pCOFINS>0.00</pCOFINS>\r\n" + 
				"		<vCOFINS>0.00</vCOFINS>\r\n" + 
				"	</COFINSOutr>\r\n" + 
				"</COFINS>";
		cofins = new CofinsConvertido(CstCofins.CST_99,Dinheiro.ZERO,Aliquota.ZERO,null,null,Dinheiro.ZERO);
		assertEquals(cofins,fromXML(XML));
	}
}