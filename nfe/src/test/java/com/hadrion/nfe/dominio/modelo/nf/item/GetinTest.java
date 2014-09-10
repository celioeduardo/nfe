package com.hadrion.nfe.dominio.modelo.nf.item;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GetinTest {
	@Test
	public void novo(){
		assertEquals(
				new Gtin(12345678L),
				new Gtin(12345678L)
		);
	}
	@Test(expected=IllegalArgumentException.class)
	public void naoPodeSerZero(){
		new Gtin(0L);
	}
}
