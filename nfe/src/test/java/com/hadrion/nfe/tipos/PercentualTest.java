package com.hadrion.nfe.tipos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PercentualTest {
	@Test
	public void equalsPercentual(){
		assertEquals(Percentual.ZERO,Percentual.ZERO); 
		assertEquals(Percentual.CEM,Percentual.CEM); 
		assertEquals(new Percentual(1.1),new Percentual(1.1)); 
		assertEquals(new Percentual(1.1234),new Percentual(1.1234)); 
		assertEquals(new Percentual(999999999.9999),new Percentual(999999999.9999)); 
	}

}
