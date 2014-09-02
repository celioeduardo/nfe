package com.hadrion.nfe.tipos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EmailTest {
	@Test
	public void equalsTelefone(){
		assertEquals(new Email("kent@back"),new Email("kent@back")); 
	}

}
