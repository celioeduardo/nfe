package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.cobranca.Cobranca;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Duplicata;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Fatura;
import com.hadrion.nfe.tipos.Dinheiro;

public class CobrancaTradutorXmlTest extends AbstractXmlTest{
	
	private static final String XML = 
			"<cobr>\r\n" + 
			"	<fat>\r\n" + 
			"		<nFat>DCO-19936</nFat>\r\n" + 
			"		<vOrig>1732.50</vOrig>\r\n" + 
			"		<vDesc>0.50</vDesc>\r\n" + 
			"		<vLiq>1732.00</vLiq>\r\n" + 
			"	</fat>\r\n" + 
			"	<dup>\r\n" + 
			"		<nDup>DCO-19936/1</nDup>\r\n" + 
			"		<dVenc>2013-10-28</dVenc>\r\n" + 
			"		<vDup>1732.00</vDup>\r\n" + 
			"	</dup>\r\n" + 
			"</cobr>\r\n";
	
	private Cobranca cob;
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("cobr", Cobranca.class);
	}
	
	@Test
	public void serializar(){
		cob = new Cobranca(
				new Fatura("DCO-19936", new Dinheiro(1732.5), new Dinheiro(0.5)), 
				new Duplicata("DCO-19936/1", data("28/10/2013") , new Dinheiro(1732)));
		assertXMLEquals(XML,toXML(cob));
	}
	
	@Test
	public void deserializar(){
		cob = new Cobranca(
				new Fatura("DCO-19936", new Dinheiro(1732.5), new Dinheiro(0.5)), 
				new Duplicata("DCO-19936/1", data("28/10/2013") , new Dinheiro(1732)));
		assertEquals(cob,fromXML(XML));
	}
	
	private Date data(String data){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			return formatter.parse(data);
		} catch (ParseException e) {
			return null;
		}	
		
	}	
}
