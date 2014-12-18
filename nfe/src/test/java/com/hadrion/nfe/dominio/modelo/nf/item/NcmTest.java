package com.hadrion.nfe.dominio.modelo.nf.item;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NcmTest {
	@Test
	public void novo(){
		assertEquals(
				new Ncm("12345678"),
				new Ncm("12345678")
		);
	}
	
}
