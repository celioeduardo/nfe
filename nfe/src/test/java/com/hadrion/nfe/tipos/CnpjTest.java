package com.hadrion.nfe.tipos;

import static org.junit.Assert.*;

import org.junit.Test;

public class CnpjTest {
	
	@Test
	public void comoStringFixa(){
		assertEquals("08495721000121", new Cnpj(8495721000121L).fixo());
		assertEquals("00495721000121", new Cnpj(495721000121L).fixo());
	}

}
