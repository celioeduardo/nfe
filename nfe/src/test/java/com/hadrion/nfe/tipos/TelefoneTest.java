package com.hadrion.nfe.tipos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TelefoneTest {
	@Test
	public void equalsTelefone(){
		assertEquals(new Telefone("3732322434"),new Telefone("3732322434")); 
	}

}
