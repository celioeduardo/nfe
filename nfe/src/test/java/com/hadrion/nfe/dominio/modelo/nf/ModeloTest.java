package com.hadrion.nfe.dominio.modelo.nf;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ModeloTest {
	@Test
	public void equalsModelo(){
		assertEquals(new Modelo("55"),new Modelo("55"));
	}

}
