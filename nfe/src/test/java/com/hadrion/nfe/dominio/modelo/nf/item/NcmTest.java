package com.hadrion.nfe.dominio.modelo.nf.item;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NcmTest {
	@Test
	public void novo(){
		assertEquals(
				new Ncm(12345678L),
				new Ncm(12345678L)
		);
	}
	@Test(expected=IllegalArgumentException.class)
	public void naoPodeSerZero(){
		new Ncm(0L);
	}
}
