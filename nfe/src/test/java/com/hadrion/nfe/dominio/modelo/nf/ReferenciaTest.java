package com.hadrion.nfe.dominio.modelo.nf;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;

public class ReferenciaTest {
	@Test
	public void equalsReferencia(){
		assertEquals(Referencia.nfe(new ChaveAcesso("1")),Referencia.nfe(new ChaveAcesso("1")));
	}

}
