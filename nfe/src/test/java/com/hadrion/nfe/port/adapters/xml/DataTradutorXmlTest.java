package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

public class DataTradutorXmlTest extends AbstractXmlTest{
	
	static class Calendario{
		public Date dataAtributo;
	}
	
	@Test
	public void serializar(){
		assertEquals("<date>2014-06-12T00:00:00-03:00</date>",xstream.toXML(data()));
	}
	
	@Test
	public void serializarInterna(){
		xstream.alias("root", Calendario.class);
		Calendario c = new Calendario();
		c.dataAtributo = data();
		assertXMLEquals("<root><dataAtributo>2014-06-12T00:00:00-03:00</dataAtributo></root>", xstream.toXML(c));
	}
	
	@Test
	public void deserializar(){
		assertEquals(data(),(Date)xstream.fromXML("<date>2014-06-12T00:00:00-03:00</date>"));
	}

	private Date data(){
		DateTime date = new DateTime(2014, 6, 12, 0, 0, DateTimeZone.forOffsetHours(-3));
		return date.toDate();
	}
}
