package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class DataTradutorXmlTest extends AbstractXmlTest{
	
	static class Calendario{
		public Date dataAtributo;
	}
	
	@Test
	public void serializar(){
		assertEquals("<date>2014-06-12</date>",xstream.toXML(data()));
	}
	
	@Test
	public void serializarInterna(){
		xstream.alias("root", Calendario.class);
		Calendario c = new Calendario();
		c.dataAtributo = data();
		assertXMLEquals("<root><dataAtributo>2014-06-12</dataAtributo></root>", xstream.toXML(c));
	}
	
	@Test
	public void deserializar(){
		assertEquals(data(),(Date)xstream.fromXML("<date>2014-06-12</date>"));
	}

	private Date data(){
		Calendar c = GregorianCalendar.getInstance();
		c.set(2014,Calendar.JUNE,12,0,0,0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	
	
}
