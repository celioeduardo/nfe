package com.hadrion.nfe.tipos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QuantidadeTest {
	@Test
	public void equalsQuantidade(){
		assertEquals(Quantidade.ZERO,Quantidade.ZERO); 
		assertEquals(Quantidade.UM,Quantidade.UM); 
		assertEquals(new Quantidade(1.1),new Quantidade(1.1)); 
		assertEquals(new Quantidade(1.1234),new Quantidade(1.1234)); 
		assertEquals(new Quantidade(999999999.9999),new Quantidade(999999999.9999)); 
	}

}
