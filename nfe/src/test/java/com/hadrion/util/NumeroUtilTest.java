package com.hadrion.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NumeroUtilTest {
	@Test
	public void testaNumeroUtil(){
		Integer numero = NumeroUtil.randInt(1, 9);
		
		assertNotNull(numero);
		assertTrue(numero >= 1 && numero <= 9 );
	}
}
