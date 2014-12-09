package com.hadrion.nfe.tipos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AliquotaTest {
	@Test
	public void equalsAliquota(){
		assertEquals(Aliquota.ZERO,Aliquota.ZERO); 
		assertEquals(new Aliquota(1.1),new Aliquota(1.1)); 
		assertEquals(new Aliquota(1.1234),new Aliquota(1.1234)); 
		assertEquals(new Aliquota(999999999.9999),new Aliquota(999999999.9999)); 
	}
}
