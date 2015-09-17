package com.hadrion.nfe.tipos;

import static com.hadrion.util.DataUtil.dataHora;
import static com.hadrion.util.DataUtil.formatarComTimezone;
import static com.hadrion.util.DataUtil.parseComTimezone;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

public class DataUtilTest {	
	
	@Test
	public void timezone(){
		TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
		Date data = dataHora("12/09/14 00:00:00", tz);
		assertEquals("2014-09-12T00:00:00-03:00",formatarComTimezone(data,tz));
	}
	@Test
	public void parseComTimezoneTest(){
		
		Date data = parseComTimezone("2014-09-12T00:00:00-03:00");
		System.out.println(data);
		
		assertEquals("2014-09-12T00:00:00-03:00",formatarComTimezone(data));
		
	}

}

