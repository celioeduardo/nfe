package com.hadrion.nfe.dominio.modelo.nf.item;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;

public class CideTest {
	@Test
	public void nova(){
		assertEquals(
				new Cide(new Dinheiro(10.75), new Aliquota(1.3), new Dinheiro(0.71)),
				new Cide(new Dinheiro(10.75), new Aliquota(1.3), new Dinheiro(0.71))
		);
	}
	@Test(expected=IllegalArgumentException.class)
	public void naoPodeSerZeroBaseCalculo(){
		new Cide(Dinheiro.ZERO, new Aliquota(1.3), new Dinheiro(0.71));
	}
	@Test
	public void aliquotaPodeSerZero(){
		new Cide(new Dinheiro(10.75), Aliquota.ZERO, new Dinheiro(0.71));
	}
	@Test(expected=IllegalArgumentException.class)
	public void naoPodeSerNegativaBaseCalculo(){
		new Cide(new Dinheiro(-1), new Aliquota(1.3), new Dinheiro(0.71));
	}
}
