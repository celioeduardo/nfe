package com.hadrion.nfe.dominio.modelo.inutillizacao;

import static org.junit.Assert.*;

import org.junit.Test;

public class FaixaTest{
	
	@Test
	public void faixa(){
		Faixa faixa = new Faixa(0,1);
		assertEquals(faixa,new Faixa(0, 1));
	}
	
	@Test
	public void faixa_inicio_igual_a_fim_eh_valida(){
		new Faixa(1,1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void faixa_negativa_invalida(){
		new Faixa(-2, -2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void faixa_invalida(){
		new Faixa(1, 0);
	}
	@Test(expected=IllegalArgumentException.class)
	public void faixa_invalida_inicial_maior_que_final_e_nao_zerado(){
		new Faixa(1, 0);
	}
	@Test(expected=IllegalArgumentException.class)
	public void faixa_invalida_inicial_maior_que_final_e_zerado(){
		new Faixa(0, -1);
	}
	@Test(expected=IllegalArgumentException.class)
	public void faixa_invalida_inicial_menor_que_final_zerado(){
		new Faixa(-1, 0);
	}
	@Test(expected=IllegalArgumentException.class)
	public void faixa_invalida_inicial_menor_que_final_menor_que_zero(){
		new Faixa(-2, -1);
	}
}
