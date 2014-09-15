package com.hadrion.util;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;

public class DataUtilTest {
	@Test
	public void nova(){
		assertEquals(new DateTime(2014, 1, 31, 0, 0).toDate(),DataUtil.janeiro(31, 2014)); 
//		DataUtil.fevereiro(31, 2014); 
//		DataUtil.marco(31, 2014); 
//		DataUtil.abril(31, 2014); 
//		DataUtil.maio(31, 2014); 
//		DataUtil.junho(31, 2014); 
//		DataUtil.julho(31, 2014); 
//		DataUtil.agosto(31, 2014); 
//		DataUtil.setembro(31, 2014); 
//		DataUtil.outubro(31, 2014); 
//		DataUtil.novembro(31, 2014); 
//		DataUtil.dezembro(31, 2014); 
	}
}
