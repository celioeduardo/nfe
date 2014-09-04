package com.hadrion.nfe.dominio.modelo.nf.cobranca;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.tipos.Dinheiro;

public class FaturaTest {
	@Test
	public void novaFatura(){
		assertEquals(
				new Fatura("1", new Dinheiro(10), Dinheiro.ZERO),
				new Fatura("1", new Dinheiro(10), Dinheiro.ZERO));
	}
	@Test
	public void valorLiquido(){
		assertEquals(
				new Dinheiro(370.15),
				new Fatura("1", new Dinheiro(680.78), new Dinheiro(310.63)).liquido());
	}
	@Test(expected=IllegalArgumentException.class)
	public void faturaTemQueTerValor(){
		new Fatura("1", Dinheiro.ZERO, Dinheiro.ZERO);
	}
}
