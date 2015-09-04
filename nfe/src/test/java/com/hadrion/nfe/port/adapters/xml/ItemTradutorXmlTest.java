package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.cofins.Cofins;
import com.hadrion.nfe.dominio.modelo.cofins.CstCofins;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.icms.Cst;
import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
import com.hadrion.nfe.dominio.modelo.nf.item.Cfop;
import com.hadrion.nfe.dominio.modelo.nf.item.Cide;
import com.hadrion.nfe.dominio.modelo.nf.item.Combustivel;
import com.hadrion.nfe.dominio.modelo.nf.item.DescritorProduto;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoIndireta;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoItem;
import com.hadrion.nfe.dominio.modelo.nf.item.Gtin;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.item.Ncm;
import com.hadrion.nfe.dominio.modelo.nf.item.imposto.Imposto;
import com.hadrion.nfe.dominio.modelo.pis.CstPis;
import com.hadrion.nfe.dominio.modelo.pis.Pis;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Percentual;
import com.hadrion.nfe.tipos.Quantidade;

public class ItemTradutorXmlTest extends AbstractXmlTest{
	
	private static final String XML = 
			"<det>\r\n" + 
			"	<prod>\r\n" + 
			"	  <cProd>00000000000000009001</cProd>\r\n" + 
			"	  <cEAN>12345678</cEAN>\r\n" + 
			"	  <xProd>SOJA EM GRAOS DEPOSITO</xProd>\r\n" + 
			"	  <NCM>12010010</NCM>\r\n" + 
			"	  <NVE>ABCDEF</NVE>\r\n" + 
			"	  <EXTIPI>123</EXTIPI>\r\n" + 
			"	  <CFOP>5906</CFOP>\r\n" + 
			"	  <uCom>KG</uCom>\r\n" + 
			"	  <qCom>36620.0000</qCom>\r\n" + 
			"	  <vUnCom>1.0</vUnCom>\r\n" + 
			"	  <vProd>36620.00</vProd>\r\n" + 
			"	  <cEANTrib>12345678</cEANTrib>\r\n" + 
			"	  <uTrib>KG</uTrib>\r\n" + 
			"	  <qTrib>36620.0000</qTrib>\r\n" + 
			"	  <vUnTrib>1.0</vUnTrib>\r\n" + 
			"	  <vFrete>100.00</vFrete>\r\n" + 
			"	  <vSeg>99.00</vSeg>\r\n" + 
			"	  <vDesc>88.00</vDesc>\r\n" + 
			"	  <vOutro>77.00</vOutro>\r\n" + 
			"	  <indTot>1</indTot>\r\n" + 
			"	  <detExport>\r\n" + 
			"		<nDraw>12345678901</nDraw>\r\n" + 
			"		<exportInd>\r\n" + 
			"		  <nRE>123456789012</nRE>\r\n" + 
			"		  <chNFe>29140600891206000310550010000110017000481161</chNFe>\r\n" + 
			"		  <qExport>50.1234</qExport>\r\n" + 
			"		</exportInd>\r\n" + 
			"	  </detExport>\r\n" + 
			"	  <comb>\r\n" + 
			"		<cProdANP>123456789</cProdANP>\r\n" + 
			"		<qTemp>568.1234</qTemp>\r\n" + 
			"		<UFCons>SP</UFCons>\r\n" + 
			"		<CIDE>\r\n" + 
			"		  <qBCProd>500.78</qBCProd>\r\n" + 
			"		  <vAliqProd>18.0000</vAliqProd>\r\n" + 
			"		  <vCIDE>90.14</vCIDE>\r\n" + 
			"		</CIDE>\r\n" + 
			"	  </comb>\r\n" + 
			"	</prod>\r\n" + 
			"	<imposto>\r\n" + 
			"	  <vTotTrib>0.00</vTotTrib>\r\n" + 
			"	  <ICMS>\r\n" + 
			"		<ICMS51>\r\n" + 
			"		  <orig>0</orig>\r\n" + 
			"		  <CST>51</CST>\r\n" + 
			"		  <modBC>3</modBC>\r\n" + 
			"		  <pRedBC>0.00</pRedBC>\r\n" + 
			"		  <vBC>1000.00</vBC>\r\n" + 
			"		  <pICMS>0.0000</pICMS>\r\n" + 
			"		  <vICMSOp>0.00</vICMSOp>\r\n" + 
			"		  <vICMSDif>0.00</vICMSDif>\r\n" + 
			"		  <vICMS>0.00</vICMS>\r\n" + 
			"		</ICMS51>\r\n" + 
			"	  </ICMS>\r\n" + 
			"	  <PIS>\r\n" + 
			"		<PISOutr>\r\n" + 
			"		  <CST>99</CST>\r\n" + 
			"		  <vBC>0.00</vBC>\r\n" + 
			"		  <pPIS>0.00</pPIS>\r\n" + 
			"		  <vPIS>0.00</vPIS>\r\n" + 
			"		</PISOutr>\r\n" + 
			"	  </PIS>\r\n" + 
			"	  <COFINS>\r\n" + 
			"		<COFINSOutr>\r\n" + 
			"		  <CST>99</CST>\r\n" + 
			"		  <vBC>0.00</vBC>\r\n" + 
			"		  <pCOFINS>0.00</pCOFINS>\r\n" + 
			"		  <vCOFINS>0.00</vCOFINS>\r\n" + 
			"		</COFINSOutr>\r\n" + 
			"	  </COFINS>\r\n" + 
			"	</imposto>\r\n" + 
			"	<infAdProd>Informação Adicional</infAdProd>\r\n" + 
			"</det>";
	
	private Item item;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("det", Item.class);
	}
	
	@Test
	public void serializar(){
		item = new Item( 
			new DescritorProduto(
					"00000000000000009001",
					new Gtin(12345678L),
					"SOJA EM GRAOS DEPOSITO",
					new Ncm("12010010"),
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
							new Cide(new Dinheiro(500.78), new Aliquota(18.0), new Dinheiro(90.14))),
					null),
			new Imposto(Dinheiro.ZERO, 
				Icms.cst_51(Origem.NACIONAL,new Dinheiro(1000), Aliquota.ZERO,
						Percentual.ZERO,Dinheiro.ZERO, Percentual.ZERO, DeterminacaoBaseCalculo.VALOR_OPERACAO), 
				new Pis(CstPis.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null), 
				new Cofins(CstCofins.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null)),
			"Informação Adicional");
		assertXMLEquals(XML,toXML(item));
	}
	
	@Test
	public void deserializar(){
		item = new Item( 
			new DescritorProduto(
					"00000000000000009001",
					new Gtin(12345678L),
					"SOJA EM GRAOS DEPOSITO",
					new Ncm("12010010"),
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
							new Cide(new Dinheiro(500.78), new Aliquota(18.0), new Dinheiro(90.14))),
					null),
			new Imposto(Dinheiro.ZERO, 
				new Icms(Origem.NACIONAL,Cst.CST_51, DeterminacaoBaseCalculo.VALOR_OPERACAO,
						Percentual.ZERO, Dinheiro.ZERO, new Dinheiro(1000), new Dinheiro(1000), Aliquota.ZERO, Dinheiro.ZERO, null, 
						Percentual.ZERO), 
				new PisDeserializado(CstPis.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null,Dinheiro.ZERO), 
				new CofinsDeserializado(CstCofins.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null,Dinheiro.ZERO)),
				"Informação Adicional");
		assertEquals(item,fromXML(XML));
	}

}
