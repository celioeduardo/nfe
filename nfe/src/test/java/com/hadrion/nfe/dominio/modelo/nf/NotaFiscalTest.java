package com.hadrion.nfe.dominio.modelo.nf;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;

public class NotaFiscalTest {
	
	@Test
	public void novaNotaEmProducao(){
		NotaFiscal nf = new NotaFiscal(
				Ambiente.PRODUCAO,
				new NotaFiscalId("123456"));
		
		assertEquals(Ambiente.PRODUCAO,nf.ambiente());
	}
	
	@Test
	public void novaNotaEmHomologacao(){
		NotaFiscal nf = new NotaFiscal(
				Ambiente.HOMOLOGACAO,
				new NotaFiscalId("123456"));
		
		assertEquals(Ambiente.HOMOLOGACAO,nf.ambiente());
	}
	
	
}
