package com.hadrion.nfe.dominio.modelo.notista;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NotistaTest {
	
	@Test
	public void novaNotista(){
		
		Notista notista = new Notista(
				new NotistaId("antoniowilson"),
				"Antônio Wilson",
				"Insumos");
		
		assertEquals(new NotistaId("antoniowilson"),notista.notistaId());
		assertEquals("Antônio Wilson",notista.nome());
		assertEquals("Insumos",notista.descricao());
		
	}
	
	@Test
	public void alterar(){
		
		Notista notista = new Notista(
				new NotistaId("antoniowilson"),
				"Antônio Wilson",
				"Insumos");
		
		notista.renomear("Antônio Wilson da Silva");
		assertEquals("Antônio Wilson da Silva",notista.nome());
		notista.alterarDescricao("Departamento Insumos");
		assertEquals("Departamento Insumos",notista.descricao());
		
	}

}
