package com.hadrion.nfe.dominio.modelo.nf.cobranca;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.hadrion.nfe.tipos.Dinheiro;

public class DuplicataTest {
	@Test
	public void novaDuplicata(){
		
		Date vencimento = new Date();
		
		assertEquals(
				new Duplicata("DM-24/7", vencimento, Dinheiro.ZERO),
				new Duplicata("DM-24/7", vencimento, Dinheiro.ZERO));
	}

}
