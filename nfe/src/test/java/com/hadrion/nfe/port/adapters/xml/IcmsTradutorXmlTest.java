package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.icms.Cst;
import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Percentual;

public class IcmsTradutorXmlTest extends AbstractXmlTest{
	
	/*private static final String XML = 
			"<ICMS>\r\n" + 
			"	<ICMS51>\r\n" + 
			"		<orig>0</orig>\r\n" + 
			"		<CST>51</CST>\r\n" + 
			"		<modBC>3</modBC>\r\n" + 
			"		<pRedBC>0.00</pRedBC>\r\n" + 
			"		<vBC>0.00</vBC>\r\n" + 
			"		<pICMS>0.00</pICMS>\r\n" + 
			"		<vICMS>0.00</vICMS>\r\n" + 
			"	</ICMS51>\r\n" + 
			"</ICMS>\r\n";
	*/
	private Icms icms;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("ICMS", Icms.class);
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
			"		<pICMS>18.00</pICMS>\r\n" + 
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
				new Dinheiro(1000.0), 
				new Aliquota(18.0), 
				Dinheiro.ZERO, 
				null, 
				Percentual.CEM);
//		System.out.println(toXML(icms));
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
				"		<pICMS>18.00</pICMS>\r\n" + 
				"		<vICMSOp>180.00</vICMSOp>\r\n" + 
				"		<pDif>100.00</pDif>\r\n" + 
				"		<vICMSDif>180.00</vICMSDif>\r\n" + 
				"		<vICMS>0.00</vICMS>\r\n" + 
				"	</ICMS51>\r\n" + 
				"</ICMS>\r\n";		
		icms = new IcmsConvertido(
				Origem.NACIONAL, 
				Cst.CST_51, 
				DeterminacaoBaseCalculo.VALOR_OPERACAO, 
				Percentual.ZERO, 
				null, 
				new Aliquota(18.0), 
				new Dinheiro(180), 
				null, 
				Percentual.CEM,
				new Dinheiro(1000));
		assertEquals(icms,fromXML(XML));
	}
	
}
