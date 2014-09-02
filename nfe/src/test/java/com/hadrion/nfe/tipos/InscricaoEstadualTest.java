package com.hadrion.nfe.tipos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InscricaoEstadualTest {
	@Test
	public void equalsInscricaoEstadual(){
		assertEquals(new InscricaoEstadual("452332065.00-50"),new InscricaoEstadual("452332065.00-50"));
	}

}
