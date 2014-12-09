package com.hadrion.nfe.tipos;

import static org.junit.Assert.*;

import org.junit.Test;

public class CpfTest {
	
	@Test
	public void comoStringFixa(){
		assertEquals("15997427801", new Cpf(15997427801L).fixo());
		assertEquals("159974278011", new Cpf(159974278011L).fixo());
	}

}
