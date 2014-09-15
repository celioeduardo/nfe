package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.pis.CstPis;
import com.hadrion.nfe.dominio.modelo.pis.Pis;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;

public class PisTradutorXmlTest extends AbstractXmlTest{
	private Pis pis;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("PIS", Pis.class);
	}
	
	@Test
	public void serializarPis01(){
		String XML = 
			"<PIS>\r\n" + 
			"	<PISAliq>\r\n" + 
			"		<CST>01</CST>\r\n" + 
			"		<vBC>1000.00</vBC>\r\n" + 
			"		<pPIS>1.65</pPIS>\r\n" + 
			"		<vPIS>16.50</vPIS>\r\n" + 
			"	</PISAliq>\r\n" + 
			"</PIS>";
		pis = new Pis(CstPis.CST_01,new Dinheiro(1000),new Aliquota(1.65),null,null);
		assertXMLEquals(XML,toXML(pis));
	}
	
	@Test
	public void deserializarPis01(){
		String XML = 
				"<PIS>\r\n" + 
						"	<PISAliq>\r\n" + 
						"		<CST>01</CST>\r\n" + 
						"		<vBC>1000.00</vBC>\r\n" + 
						"		<pPIS>1.65</pPIS>\r\n" + 
						"		<vPIS>16.50</vPIS>\r\n" + 
						"	</PISAliq>\r\n" + 
						"</PIS>";
		pis = new PisDeserializado(
				CstPis.CST_01,new Dinheiro(1000),new Aliquota(1.65),null,null,new Dinheiro(16.5));
		assertEquals(pis,fromXML(XML));
	}
	
	@Test
	public void serializarPis02(){
		String XML = 
			"<PIS>\r\n" + 
			"	<PISAliq>\r\n" + 
			"		<CST>02</CST>\r\n" + 
			"		<vBC>1000.00</vBC>\r\n" + 
			"		<pPIS>1.65</pPIS>\r\n" + 
			"		<vPIS>16.50</vPIS>\r\n" + 
			"	</PISAliq>\r\n" + 
			"</PIS>";
		pis = new Pis(CstPis.CST_02,new Dinheiro(1000),new Aliquota(1.65),null,null);
		assertXMLEquals(XML,toXML(pis));
	}
	
	@Test
	public void deserializarPis02(){
		String XML = 
				"<PIS>\r\n" + 
				"	<PISAliq>\r\n" + 
				"		<CST>02</CST>\r\n" + 
				"		<vBC>1000.00</vBC>\r\n" + 
				"		<pPIS>1.65</pPIS>\r\n" + 
				"		<vPIS>16.50</vPIS>\r\n" + 
				"	</PISAliq>\r\n" + 
				"</PIS>";
		pis = new PisDeserializado(
				CstPis.CST_02,new Dinheiro(1000),new Aliquota(1.65),null,null,new Dinheiro(16.5));
		assertEquals(pis,fromXML(XML));
	}
	
	@Test
	public void serializarPis03(){
		String XML = 
			"<PIS>\r\n" + 
			"	<PISQtde>\r\n" + 
			"		<CST>03</CST>\r\n" + 
			"		<qBCProd>56.0000</qBCProd>\r\n" + 
			"		<vAliqProd>10.0000</vAliqProd>\r\n" + 
			"		<vPIS>560.00</vPIS>\r\n" + 
			"	</PISQtde>\r\n" + 
			"</PIS>";
		pis = new Pis(CstPis.CST_03,null,null,56.0,10.0);
		assertXMLEquals(XML,toXML(pis));
	}
	
	@Test
	public void deserializarPis03(){
		String XML = 
				"<PIS>\r\n" + 
				"	<PISQtde>\r\n" + 
				"		<CST>03</CST>\r\n" + 
				"		<qBCProd>56.0000</qBCProd>\r\n" + 
				"		<vAliqProd>10.0000</vAliqProd>\r\n" + 
				"		<vPIS>560.00</vPIS>\r\n" + 
				"	</PISQtde>\r\n" + 
				"</PIS>";
		pis = new PisDeserializado(CstPis.CST_03,null,null,56.0,10.0,new Dinheiro(560.0));
		assertEquals(pis,fromXML(XML));
	}
	
	@Test
	public void serializarPis04(){
		String XML = "<PIS><PISNT><CST>04</CST></PISNT></PIS>";
		assertXMLEquals(XML,toXML(new Pis(CstPis.CST_04,null,null,null,null)));
	}
	@Test
	public void deserializarPis04(){
		String XML = "<PIS><PISNT><CST>04</CST></PISNT></PIS>";
		pis = new PisDeserializado(CstPis.CST_04,null,null,null,null,null);
		assertEquals(pis,fromXML(XML));
	}
	@Test
	public void serializarPis05(){
		String XML = "<PIS><PISNT><CST>05</CST></PISNT></PIS>";
		assertXMLEquals(XML,toXML(new Pis(CstPis.CST_05,null,null,null,null)));
	}
	@Test
	public void deserializarPis05(){
		String XML = "<PIS><PISNT><CST>05</CST></PISNT></PIS>";
		pis = new PisDeserializado(CstPis.CST_05,null,null,null,null,null);
		assertEquals(pis,fromXML(XML));
	}
	@Test
	public void serializarPis06(){
		String XML = "<PIS><PISNT><CST>06</CST></PISNT></PIS>";
		assertXMLEquals(XML,toXML(new Pis(CstPis.CST_06,null,null,null,null)));
	}
	@Test
	public void deserializarPis06(){
		String XML = "<PIS><PISNT><CST>06</CST></PISNT></PIS>";
		pis = new PisDeserializado(CstPis.CST_06,null,null,null,null,null);
		assertEquals(pis,fromXML(XML));
	}
	@Test
	public void serializarPis07(){
		String XML = "<PIS><PISNT><CST>07</CST></PISNT></PIS>";
		assertXMLEquals(XML,toXML(new Pis(CstPis.CST_07,null,null,null,null)));
	}
	@Test
	public void deserializarPis07(){
		String XML = "<PIS><PISNT><CST>07</CST></PISNT></PIS>";
		pis = new PisDeserializado(CstPis.CST_07,null,null,null,null,null);
		assertEquals(pis,fromXML(XML));
	}
	@Test
	public void serializarPis08(){
		String XML = "<PIS><PISNT><CST>08</CST></PISNT></PIS>";
		assertXMLEquals(XML,toXML(new Pis(CstPis.CST_08,null,null,null,null)));
	}
	@Test
	public void deserializarPis08(){
		String XML = "<PIS><PISNT><CST>08</CST></PISNT></PIS>";
		pis = new PisDeserializado(CstPis.CST_08,null,null,null,null,null);
		assertEquals(pis,fromXML(XML));
	}
	@Test
	public void serializarPis09(){
		String XML = "<PIS><PISNT><CST>09</CST></PISNT></PIS>";
		assertXMLEquals(XML,toXML(new Pis(CstPis.CST_09,null,null,null,null)));
	}
	@Test
	public void deserializarPis09(){
		String XML = "<PIS><PISNT><CST>09</CST></PISNT></PIS>";
		pis = new PisDeserializado(CstPis.CST_09,null,null,null,null,null);
		assertEquals(pis,fromXML(XML));
	}
	@Test
	public void serializarPis99(){
		String XML = 
				"<PIS>\r\n" + 
				"	<PISOutr>\r\n" + 
				"		<CST>99</CST>\r\n" + 
				"		<vBC>0.00</vBC>\r\n" + 
				"		<pPIS>0.00</pPIS>\r\n" + 
				"		<vPIS>0.00</vPIS>\r\n" + 
				"	</PISOutr>\r\n" + 
				"</PIS>";
		pis = new Pis(CstPis.CST_99,Dinheiro.ZERO,Aliquota.ZERO,0.0,0.0);
		assertXMLEquals(XML,toXML(pis));
	}
	
	@Test
	public void deserializarPis99(){
		String XML = 
				"<PIS>\r\n" + 
				"	<PISOutr>\r\n" + 
				"		<CST>99</CST>\r\n" + 
				"		<vBC>0.00</vBC>\r\n" + 
				"		<pPIS>0.00</pPIS>\r\n" + 
				"		<vPIS>0.00</vPIS>\r\n" + 
				"	</PISOutr>\r\n" + 
				"</PIS>";
		pis = new PisDeserializado(CstPis.CST_99,Dinheiro.ZERO,Aliquota.ZERO,null,null,Dinheiro.ZERO);
		assertEquals(pis,fromXML(XML));
	}
}