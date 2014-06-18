package com.hadrion.nfe.dominio.modelo.inutillizacao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FaixaTest{
	
	@Before
	public void setUp() throws Exception{
		
	}	
	
	@Test
	public void intervalo(){
		Faixa intervalo = new Faixa(0, 1);
		assertNotNull(intervalo);
	}		
	
	@Test(expected=IllegalArgumentException.class)
	public void intervalo_invalido(){
		@SuppressWarnings("unused")
		Faixa intervalo = new Faixa(1, 0);
	}
	@Test(expected=IllegalArgumentException.class)
	public void intervalo_invalido_inicial_maior_que_final_e_nao_zerado(){
		@SuppressWarnings("unused")
		Faixa intervalo = new Faixa(1, 0);
	}
	@Test(expected=IllegalArgumentException.class)
	public void intervalo_invalido_inicial_maior_que_final_e_zerado(){
		@SuppressWarnings("unused")
		Faixa intervalo = new Faixa(0, -1);
	}
	@Test(expected=IllegalArgumentException.class)
	public void intervalo_invalido_inicial_menor_que_final_zerado(){
		@SuppressWarnings("unused")
		Faixa intervalo = new Faixa(-1, 0);
	}
	@Test(expected=IllegalArgumentException.class)
	public void intervalo_invalido_inicial_menor_que_final_menor_que_zero(){
		@SuppressWarnings("unused")
		Faixa intervalo = new Faixa(-2, -1);
	}
}
