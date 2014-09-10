package com.hadrion.nfe.dominio.modelo.nf.item;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CfopTest {
	@Test
	public void nova(){
		assertEquals(
				new Cfop(5109L),
				new Cfop(5109L)
		);
	}
	@Test(expected=IllegalArgumentException.class)
	public void naoPodeSerZero(){
		new Cfop(0L);
	}
}
