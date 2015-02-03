package com.hadrion.nfe.dominio.modelo.inutilizacao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;

public class InutilizacaoTest {
	
	@Test
	public void novaInutilizacao(){
		Inutilizacao inutilizacao = new Inutilizacao(
				new InutilizacaoId("123456"),
				new Serie(2),
				100,101,
				"Justificativa de inutilização",
				new FilialId("53-86675642000106"));
		
		assertEquals(new InutilizacaoId("123456"), inutilizacao.inutilizacaoId());
		assertEquals(new Serie(2), inutilizacao.serie());
		assertEquals(100, inutilizacao.numeroInicial());
		assertEquals(101, inutilizacao.numeroFinal());
		assertEquals("Justificativa de inutilização", inutilizacao.justificativa());
		assertEquals(new FilialId("53-86675642000106"), inutilizacao.filialId());
	}
	
	@Test(expected=RuntimeException.class)
	public void numeroInicialNaoPodeSerMaiorQueNumeroFinal(){
		new Inutilizacao(
				new InutilizacaoId("123456"),
				new Serie(2),
				101,100,
				"Justificativa de inutilização",
				new FilialId("53-86675642000106"));
	}
	
}
