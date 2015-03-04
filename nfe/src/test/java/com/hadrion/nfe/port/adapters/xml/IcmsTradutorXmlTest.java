package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.icms.Cst;
import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculoSt;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
import com.hadrion.nfe.dominio.modelo.icms.SubstituicaoTributaria;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Percentual;

public class IcmsTradutorXmlTest extends AbstractXmlTest{
	private Icms icms;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("ICMS", Icms.class);
	}
	
	@Test
	public void serializarIcms00(){
		String XML = 
			"<ICMS>\r\n" + 
			"	<ICMS00>\r\n" + 
			"		<orig>0</orig>\r\n" + 
			"		<CST>00</CST>\r\n" + 
			"		<modBC>3</modBC>\r\n" + 
			"		<vBC>1200.32</vBC>\r\n" + 
			"		<pICMS>12.0000</pICMS>\r\n" + 
			"		<vICMS>144.03</vICMS>\r\n" + 
			"	</ICMS00>\r\n" + 
			"</ICMS>\r\n";
		
		icms = new Icms(
				Origem.NACIONAL, 
				Cst.CST_00, 
				DeterminacaoBaseCalculo.VALOR_OPERACAO, 
				Percentual.ZERO, 
				Dinheiro.ZERO, 
				new Dinheiro(1200.32), 
				new Aliquota(12.0), 
				null, 
				Percentual.ZERO);
		
		assertXMLEquals(XML,toXML(icms));
	}
	
	@Test
	public void deserializarIcms00(){
		String XML = 
				"<ICMS>\r\n" + 
				"	<ICMS00>\r\n" + 
				"		<orig>0</orig>\r\n" + 
				"		<CST>00</CST>\r\n" + 
				"		<modBC>3</modBC>\r\n" + 
				"		<vBC>1200.32</vBC>\r\n" + 
				"		<pICMS>12.0000</pICMS>\r\n" + 
				"		<vICMS>144.03</vICMS>\r\n" + 
				"	</ICMS00>\r\n" + 
				"</ICMS>\r\n";
		
		icms = new Icms(
				Origem.NACIONAL, 
				Cst.CST_00, 
				DeterminacaoBaseCalculo.VALOR_OPERACAO, 
				null,
				Dinheiro.ZERO,
				null, 
				new Dinheiro(1200.32), 
				new Aliquota(12.0), 
				new Dinheiro(144.03), 
				null, 
				null);
		assertEquals(icms,fromXML(XML));
	}

	@Test
	public void serializarIcms10(){
		String XML = 
				"<ICMS>\r\n" + 
				"	<ICMS10>\r\n" + 
				"		<orig>0</orig>\r\n" + 
				"		<CST>10</CST>\r\n" + 
				"		<modBC>3</modBC>\r\n" + 
				"		<vBC>2500.00</vBC>\r\n" + 
				"		<pICMS>8.0000</pICMS>\r\n" + 
				"		<vICMS>200.00</vICMS>\r\n" + 
				"		<modBCST>4</modBCST>\r\n" + 
				"		<pMVAST>10.00</pMVAST>\r\n" + 
				"		<pRedBCST>12.00</pRedBCST>\r\n" + 
				"		<vBCST>2420.00</vBCST>\r\n" +
				"		<pICMSST>18.00</pICMSST>\r\n" +
				"		<vICMSST>435.60</vICMSST>\r\n" +
				"	</ICMS10>\r\n" + 
				"</ICMS>\r\n";
		
		icms = new Icms(
				Origem.NACIONAL, 
				Cst.CST_10, 
				DeterminacaoBaseCalculo.VALOR_OPERACAO, 
				Percentual.ZERO,
				Dinheiro.ZERO,
				new Dinheiro(2500), 
				new Aliquota(8), 
				new SubstituicaoTributaria(
						new Dinheiro(2420), 
						new Aliquota(18),
						new Dinheiro(435.60),
						new Percentual(12), 
						DeterminacaoBaseCalculoSt.MVA, 
						new Percentual(10)),
				Percentual.ZERO);
		
		assertXMLEquals(XML,toXML(icms));
	}
	
	@Test
	public void deserializarIcms10(){
		String XML = 
				"<ICMS>\r\n" + 
				"	<ICMS10>\r\n" + 
				"		<orig>0</orig>\r\n" + 
				"		<CST>10</CST>\r\n" + 
				"		<modBC>3</modBC>\r\n" + 
				"		<vBC>2500.00</vBC>\r\n" + 
				"		<pICMS>8.0000</pICMS>\r\n" + 
				"		<vICMS>200.00</vICMS>\r\n" + 
				"		<modBCST>4</modBCST>\r\n" + 
				"		<pMVAST>10.00</pMVAST>\r\n" + 
				"		<pRedBCST>12.00</pRedBCST>\r\n" + 
				"		<vBCST>2420.00</vBCST>\r\n" +
				"		<pICMSST>18.00</pICMSST>\r\n" +
				"		<vICMSST>435.60</vICMSST>\r\n" +
				"	</ICMS10>\r\n" + 
				"</ICMS>\r\n";
		
		icms = new Icms(
				Origem.NACIONAL, 
				Cst.CST_10, 
				DeterminacaoBaseCalculo.VALOR_OPERACAO, 
				null, 
				Dinheiro.ZERO,
				null, 
				new Dinheiro(2500),
				new Aliquota(8),
				new Dinheiro(200),
				new SubstituicaoTributaria(
						new Dinheiro(2420),
						new Aliquota(18), 
						new Dinheiro(435.6),
						new Percentual(12), 
						DeterminacaoBaseCalculoSt.MVA, 
						new Percentual(10)),
				null);
		
		assertEquals(icms,fromXML(XML));
	}
	
	@Test
	public void serializarIcms20(){
		String XML = 
			"<ICMS>\r\n" + 
			"	<ICMS20>\r\n" + 
			"		<orig>0</orig>\r\n" + 
			"		<CST>20</CST>\r\n" + 
			"		<modBC>3</modBC>\r\n" + 
			"		<pRedBC>33.00</pRedBC>\r\n" + 
			"		<vBC>670.00</vBC>\r\n" + 
			"		<pICMS>18.0000</pICMS>\r\n" + 
			"		<vICMS>120.60</vICMS>\r\n" + 
			"	</ICMS20>\r\n" + 
			"</ICMS>";
		
		icms = new Icms(
				Origem.NACIONAL, 
				Cst.CST_20, 
				DeterminacaoBaseCalculo.VALOR_OPERACAO, 
				new Percentual(33),
				new Dinheiro(120.6),
				new Dinheiro(1000), 
				new Aliquota(18), 
				null, 
				Percentual.ZERO);
		
		assertXMLEquals(XML,toXML(icms));
	}
	
	@Test
	public void deserializarIcms20(){
		String XML = 
				"<ICMS>\r\n" + 
				"	<ICMS20>\r\n" + 
				"		<orig>0</orig>\r\n" + 
				"		<CST>20</CST>\r\n" + 
				"		<modBC>3</modBC>\r\n" + 
				"		<pRedBC>33.00</pRedBC>\r\n" + 
				"		<vBC>670.00</vBC>\r\n" + 
				"		<pICMS>18.0000</pICMS>\r\n" + 
				"		<vICMS>120.60</vICMS>\r\n" + 
				"	</ICMS20>\r\n" + 
				"</ICMS>\r\n";		
		
		icms = new Icms(
				Origem.NACIONAL, 
				Cst.CST_20, 
				DeterminacaoBaseCalculo.VALOR_OPERACAO, 
				new Percentual(33),
				new Dinheiro(59.4),
				null,
				new Dinheiro(670),
				new Aliquota(18.0), 
				new Dinheiro(120.6), 
				null, 
				null);
		assertEquals(icms,fromXML(XML));
	}
	
	@Test
	public void serializarIcms30(){
		String XML = 
			"<ICMS>\r\n" + 
			"	<ICMS30>\r\n" + 
			"		<orig>0</orig>\r\n" + 
			"		<CST>30</CST>\r\n" + 
			"		<modBCST>4</modBCST>\r\n" + 
			"		<pMVAST>10.00</pMVAST>\r\n" + 
			"		<pRedBCST>12.00</pRedBCST>\r\n" + 
			"		<vBCST>2420.00</vBCST>\r\n" +
			"		<pICMSST>18.00</pICMSST>\r\n" +
			"		<vICMSST>435.60</vICMSST>\r\n" +
			"	</ICMS30>\r\n" + 
			"</ICMS>";
		
		icms = new Icms(
				Origem.NACIONAL, 
				Cst.CST_30, 
				null, 
				null, 
				null,
				null, 
				null, 
				new SubstituicaoTributaria(
						new Dinheiro(2420),
						new Aliquota(18), 
						new Dinheiro(435.6),
						new Percentual(12), 
						DeterminacaoBaseCalculoSt.MVA, 
						new Percentual(10)), 
				Percentual.ZERO);
		
		assertXMLEquals(XML,toXML(icms));
	}
	
	@Test
	public void deserializarIcms30(){
		String XML = 
				"<ICMS>\r\n" + 
				"	<ICMS30>\r\n" + 
				"		<orig>0</orig>\r\n" + 
				"		<CST>30</CST>\r\n" + 
				"		<modBCST>4</modBCST>\r\n" + 
				"		<pMVAST>10.00</pMVAST>\r\n" + 
				"		<pRedBCST>12.00</pRedBCST>\r\n" + 
				"		<vBCST>2420.00</vBCST>\r\n" +
				"		<pICMSST>18.00</pICMSST>\r\n" +
				"		<vICMSST>435.60</vICMSST>\r\n" +
				"	</ICMS30>\r\n" + 
				"</ICMS>\r\n";
		
		icms = new Icms(
				Origem.NACIONAL, 
				Cst.CST_30, 
				null, 
				null, 
				null, 
				null, 
				null,
				null,
				null,
				new SubstituicaoTributaria(
						new Dinheiro(2420),
						new Aliquota(18), 
						new Dinheiro(435.6),
						new Percentual(12), 
						DeterminacaoBaseCalculoSt.MVA, 
						new Percentual(10)),
				null);
		
		assertEquals(icms,fromXML(XML));
	}
	
	@Test
	public void serializarIcms40(){
		String XML = 
			"<ICMS>\r\n" + 
			"	<ICMS40>\r\n" + 
			"		<orig>0</orig>\r\n" + 
			"		<CST>40</CST>\r\n" + 
			"	</ICMS40>\r\n" + 
			"</ICMS>";
		icms = new Icms(Origem.NACIONAL,Cst.CST_40,null,null,null,null,null,null,null);
		assertXMLEquals(XML,toXML(icms));
	}
	@Test
	public void deserializarIcms40(){
		String XML = 
				"<ICMS>\r\n" + 
				"	<ICMS40>\r\n" + 
				"		<orig>0</orig>\r\n" + 
				"		<CST>40</CST>\r\n" + 
				"	</ICMS40>\r\n" + 
				"</ICMS>\r\n";
		icms = new Icms(Origem.NACIONAL,
				Cst.CST_40,
				null,null,null,null,null,null,null,null,null);
		assertEquals(icms,fromXML(XML));
	}
	
	@Test
	public void serializarIcms41(){
		String XML = 
				"<ICMS>\r\n" + 
				"	<ICMS40>\r\n" + 
				"		<orig>0</orig>\r\n" + 
				"		<CST>41</CST>\r\n" + 
				"	</ICMS40>\r\n" + 
				"</ICMS>";
		icms = new Icms(Origem.NACIONAL,Cst.CST_41,null,null,null,null,null,null,null);
		assertXMLEquals(XML,toXML(icms));
	}
	@Test
	public void deserializarIcms41(){
		String XML = 
				"<ICMS>\r\n" + 
				"	<ICMS40>\r\n" + 
				"		<orig>0</orig>\r\n" + 
				"		<CST>41</CST>\r\n" + 
				"	</ICMS40>\r\n" + 
				"</ICMS>\r\n";
		icms = new Icms(Origem.NACIONAL,Cst.CST_41,null,null,null,null,null,null,null,null,null);
		assertEquals(icms,fromXML(XML));
	}
	
	@Test
	public void serializarIcms50(){
		String XML = 
				"<ICMS>\r\n" + 
				"	<ICMS40>\r\n" + 
				"		<orig>0</orig>\r\n" + 
				"		<CST>50</CST>\r\n" + 
				"	</ICMS40>\r\n" + 
				"</ICMS>";
		icms = new Icms(Origem.NACIONAL,Cst.CST_50,null,null,null,null,null,null,null);
		assertXMLEquals(XML,toXML(icms));
	}
	@Test
	public void deserializarIcms50(){
		String XML = 
				"<ICMS>\r\n" + 
				"	<ICMS50>\r\n" + 
				"		<orig>0</orig>\r\n" + 
				"		<CST>50</CST>\r\n" + 
				"	</ICMS50>\r\n" + 
				"</ICMS>\r\n";
		icms = new Icms(Origem.NACIONAL,Cst.CST_50,null,null,null,null,null,null,null,null,null);
		assertEquals(icms,fromXML(XML));
	}
	@Test
	public void serializarIcms51(){
		String XML = 
			"<ICMS>\r\n" + 
			"	<ICMS51>\r\n" + 
			"		<orig>0</orig>\r\n" + 
			"		<CST>51</CST>\r\n" + 
			"		<modBC>3</modBC>\r\n" + 
			"		<pRedBC>0.00</pRedBC>\r\n" + 
			"		<vBC>1000.00</vBC>\r\n" + 
			"		<pICMS>18.0000</pICMS>\r\n" + 
			"		<vICMSOp>180.00</vICMSOp>\r\n" + 
			"		<pDif>100</pDif>\r\n" + 
			"		<vICMSDif>180.00</vICMSDif>\r\n" + 
			"		<vICMS>0.00</vICMS>\r\n" + 
			"	</ICMS51>\r\n" + 
			"</ICMS>\r\n";
		
		icms = new Icms(
				Origem.NACIONAL, 
				Cst.CST_51, 
				DeterminacaoBaseCalculo.VALOR_OPERACAO, 
				Percentual.ZERO, 
				Dinheiro.ZERO,
				new Dinheiro(1000.0), 
				new Aliquota(18.0), 
				null, 
				Percentual.CEM);
		assertXMLEquals(XML,toXML(icms));
	}
	
	@Test
	public void deserializar51(){
		String XML = 
				"<ICMS>\r\n" + 
				"	<ICMS51>\r\n" + 
				"		<orig>0</orig>\r\n" + 
				"		<CST>51</CST>\r\n" + 
				"		<modBC>3</modBC>\r\n" + 
				"		<pRedBC>0.00</pRedBC>\r\n" + 
				"		<vBC>1000.00</vBC>\r\n" + 
				"		<pICMS>18.0000</pICMS>\r\n" + 
				"		<vICMSOp>180.00</vICMSOp>\r\n" + 
				"		<pDif>100.00</pDif>\r\n" + 
				"		<vICMSDif>180.00</vICMSDif>\r\n" + 
				"		<vICMS>0.00</vICMS>\r\n" + 
				"	</ICMS51>\r\n" + 
				"</ICMS>\r\n";		
		icms = new Icms(
				Origem.NACIONAL, 
				Cst.CST_51, 
				DeterminacaoBaseCalculo.VALOR_OPERACAO, 
				Percentual.ZERO, 
				Dinheiro.ZERO,
				null, 
				new Dinheiro(1000),
				new Aliquota(18.0), 
				new Dinheiro(0), 
				null, 
				Percentual.CEM);
		assertEquals(icms,fromXML(XML));
	}

	@Test
	public void serializarIcms60(){
		String XML = 
				"<ICMS>\r\n" + 
						"	<ICMS60>\r\n" + 
						"		<orig>0</orig>\r\n" + 
						"		<CST>60</CST>\r\n" + 
						"	</ICMS60>\r\n" + 
						"</ICMS>";
		icms = new Icms(Origem.NACIONAL,Cst.CST_60,null,null,null,null,null,null,null);
		assertXMLEquals(XML,toXML(icms));
	}

	@Test
	public void deserializarIcms60(){
		String XML = 
				"<ICMS>\r\n" + 
						"	<ICMS60>\r\n" + 
						"		<orig>0</orig>\r\n" + 
						"		<CST>60</CST>\r\n" + 
						"	</ICMS60>\r\n" + 
						"</ICMS>\r\n";
		icms = new Icms(Origem.NACIONAL,Cst.CST_60,null,null,null,null,null,null,null,null,null);
		assertEquals(icms,fromXML(XML));
	}

	@Test
	public void serializarIcms70(){
		String XML = 
			"<ICMS>\r\n" + 
			"	<ICMS70>\r\n" + 
			"		<orig>0</orig>\r\n" + 
			"		<CST>70</CST>\r\n" + 
			"		<modBC>3</modBC>\r\n" + 
			"		<pRedBC>40.00</pRedBC>\r\n" + 
			"		<vBC>1500.00</vBC>\r\n" + 
			"		<pICMS>18.0000</pICMS>\r\n" + 
			"		<vICMS>270.00</vICMS>\r\n" + 
			"		<modBCST>4</modBCST>\r\n" + 
			"		<pMVAST>10.00</pMVAST>\r\n" + 
			"		<pRedBCST>12.00</pRedBCST>\r\n" + 
			"		<vBCST>2420.00</vBCST>\r\n" +
			"		<pICMSST>18.00</pICMSST>\r\n" +
			"		<vICMSST>435.60</vICMSST>\r\n" +
			"	</ICMS70>\r\n" + 
			"</ICMS>\r\n";
		
		icms = new Icms(
				Origem.NACIONAL, 
				Cst.CST_70, 
				DeterminacaoBaseCalculo.VALOR_OPERACAO, 
				new Percentual(40), 
				new Dinheiro(270),
				new Dinheiro(2500.0), 
				new Aliquota(18.0), 
				new SubstituicaoTributaria(
						new Dinheiro(2420), 
						new Aliquota(18), 
						new Dinheiro(435.60), 
						new Percentual(12), 
						DeterminacaoBaseCalculoSt.MVA, 
						new Percentual(10)), 
				Percentual.ZERO);
		assertXMLEquals(XML,toXML(icms));
	}
	
	@Test
	public void deserializar70(){
		String XML = 
				"<ICMS>\r\n" + 
				"	<ICMS70>\r\n" + 
				"		<orig>0</orig>\r\n" + 
				"		<CST>70</CST>\r\n" + 
				"		<modBC>3</modBC>\r\n" + 
				"		<pRedBC>40.00</pRedBC>\r\n" + 
				"		<vBC>1500.00</vBC>\r\n" + 
				"		<pICMS>18.0000</pICMS>\r\n" + 
				"		<vICMS>270.00</vICMS>\r\n" + 
				"		<modBCST>4</modBCST>\r\n" + 
				"		<pMVAST>10.00</pMVAST>\r\n" + 
				"		<pRedBCST>12.00</pRedBCST>\r\n" + 
				"		<vBCST>2420.00</vBCST>\r\n" +
				"		<pICMSST>18.00</pICMSST>\r\n" +
				"		<vICMSST>435.60</vICMSST>\r\n" +
				"	</ICMS70>\r\n" + 
				"</ICMS>\r\n";		
		icms = new Icms(
				Origem.NACIONAL, 
				Cst.CST_70, 
				DeterminacaoBaseCalculo.VALOR_OPERACAO, 
				new Percentual(40),
				new Dinheiro(180), 
				null,
				new Dinheiro(1500),
				new Aliquota(18.0), 
				new Dinheiro(270), 
				new SubstituicaoTributaria(
						new Dinheiro(2420),
						new Aliquota(18), 
						new Dinheiro(435.6),
						new Percentual(12), 
						DeterminacaoBaseCalculoSt.MVA, 
						new Percentual(10)), 
				null);
		
		assertEquals(icms,fromXML(XML));
	}	

	@Test
	public void serializarIcms90(){
		String XML = 
			"<ICMS>\r\n" + 
			"	<ICMS90>\r\n" + 
			"		<orig>0</orig>\r\n" + 
			"		<CST>90</CST>\r\n" + 
			"	</ICMS90>\r\n" + 
			"</ICMS>";
		icms = new Icms(Origem.NACIONAL,Cst.CST_90,null,null,null,null,null,null,null);
		assertXMLEquals(XML,toXML(icms));
	}
	@Test
	public void deserializarIcms90(){
		String XML = 
				"<ICMS>\r\n" + 
				"	<ICMS90>\r\n" + 
				"		<orig>0</orig>\r\n" + 
				"		<CST>90</CST>\r\n" + 
				"	</ICMS90>\r\n" + 
				"</ICMS>\r\n";
		icms = new Icms(Origem.NACIONAL,Cst.CST_90,null,null,null,null,null,null,null,null,null);
		assertEquals(icms,fromXML(XML));
	}
}
