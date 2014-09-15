package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.cofins.Cofins;
import com.hadrion.nfe.dominio.modelo.cofins.CstCofins;
import com.hadrion.nfe.dominio.modelo.icms.Cst;
import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
import com.hadrion.nfe.dominio.modelo.nf.item.imposto.Imposto;
import com.hadrion.nfe.dominio.modelo.pis.CstPis;
import com.hadrion.nfe.dominio.modelo.pis.Pis;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Percentual;

public class ImpostoTradutorXmlTest extends AbstractXmlTest{
	
	private static final String XML = 
			"<imposto>\r\n" + 
			"	<vTotTrib>0.00</vTotTrib>\r\n" + 
			"	<ICMS>\r\n" + 
			"		<ICMS51>\r\n" + 
			"			<orig>0</orig>\r\n" + 
			"			<CST>51</CST>\r\n" + 
			"			<modBC>3</modBC>\r\n" + 
			"			<pRedBC>0.00</pRedBC>\r\n" + 
			"			<vBC>1000.00</vBC>\r\n" + 
			"			<pICMS>0.00</pICMS>\r\n" + 
			"			<vICMSOp>0.00</vICMSOp>\r\n" + 
			"			<pDif>0.00</pDif>\r\n" + 
			"			<vICMSDif>0.00</vICMSDif>\r\n" + 
			"			<vICMS>0.00</vICMS>\r\n" + 
			"		</ICMS51>\r\n" + 
			"	</ICMS>\r\n" + 
			"	<PIS>\r\n" + 
			"		<PISOutr>\r\n" + 
			"			<CST>99</CST>\r\n" + 
			"			<vBC>0.00</vBC>\r\n" + 
			"			<pPIS>0.00</pPIS>\r\n" + 
			"			<vPIS>0.00</vPIS>\r\n" + 
			"		</PISOutr>\r\n" + 
			"	</PIS>\r\n" + 
			"	<COFINS>\r\n" + 
			"		<COFINSOutr>\r\n" + 
			"			<CST>99</CST>\r\n" + 
			"			<vBC>0.00</vBC>\r\n" + 
			"			<pCOFINS>0.00</pCOFINS>\r\n" + 
			"			<vCOFINS>0.00</vCOFINS>\r\n" + 
			"		</COFINSOutr>\r\n" + 
			"	</COFINS>\r\n" + 
			"</imposto>";
	
	private Imposto imposto;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("imposto", Imposto.class);
		
	}
	
	@Test
	public void serializar(){
		imposto = new Imposto(Dinheiro.ZERO, 
				Icms.cst_51(Origem.NACIONAL,new Dinheiro(1000), Aliquota.ZERO,
						Percentual.ZERO,Percentual.ZERO, DeterminacaoBaseCalculo.VALOR_OPERACAO), 
				new Pis(CstPis.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null), 
				new Cofins(CstCofins.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null));
		
		assertXMLEquals(XML,toXML(imposto));
	}
	
	@Test
	public void deserializar(){
		imposto = new Imposto(Dinheiro.ZERO, 
				new IcmsDeserializado(Origem.NACIONAL,Cst.CST_51, DeterminacaoBaseCalculo.VALOR_OPERACAO,
						Percentual.ZERO, new Dinheiro(1000), Aliquota.ZERO, Dinheiro.ZERO, null, 
						Percentual.ZERO, new Dinheiro(1000)), 
				new PisDeserializado(CstPis.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null,Dinheiro.ZERO), 
				new CofinsDeserializado(CstCofins.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null,Dinheiro.ZERO));
		assertEquals(imposto,fromXML(XML));
	}
	
}
