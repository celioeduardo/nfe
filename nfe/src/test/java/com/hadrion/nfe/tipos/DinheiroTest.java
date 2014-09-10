package com.hadrion.nfe.tipos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DinheiroTest {
	@Test
	public void equalsDinheiro(){
		assertEquals(Dinheiro.ZERO,Dinheiro.ZERO); 
		assertEquals(new Dinheiro(1),new Dinheiro(1)); 
		assertEquals(new Dinheiro(1),new Dinheiro(1)); 
		assertEquals(new Dinheiro(1.1),new Dinheiro(1.1)); 
		assertEquals(new Dinheiro(1.1234),new Dinheiro(1.1234)); 
		assertEquals(new Dinheiro(999999999.9999),new Dinheiro(999999999.9999)); 
	}

}
