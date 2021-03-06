package com.hadrion.nfe.dominio.modelo.icms;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Percentual;

public class SubstituicaoTributariaTest {
	
	@Test
	public void somenteAliquota(){
		SubstituicaoTributaria st = new SubstituicaoTributaria(
				new Dinheiro(1200), 
				new Aliquota(12), 
				new Dinheiro(144), 
				Percentual.ZERO, 
				DeterminacaoBaseCalculoSt.PRECO_TABELADO, 
				Percentual.ZERO);
		
		assertEquals(new Dinheiro(1200),st.baseCalculo());
		assertEquals(new Dinheiro(144),st.valor());
	}
	
	@Test
	public void margemValorAgregado(){
		SubstituicaoTributaria st = new SubstituicaoTributaria(
				new Dinheiro(1740), 
				new Aliquota(12), 
				new Dinheiro(208.8), 
				Percentual.ZERO, 
				DeterminacaoBaseCalculoSt.MVA, 
				new Percentual(45));
		
		assertEquals(new Dinheiro(1740),st.baseCalculo());
		assertEquals(new Dinheiro(208.8),st.valor());
	}
	
	@Test
	public void margemValorAgregadoComReducaoBaseCalculo(){
		SubstituicaoTributaria st = new SubstituicaoTributaria(
				new Dinheiro(2420), 
				new Aliquota(18), 
				new Dinheiro(435.6), 
				new Percentual(12), 
				DeterminacaoBaseCalculoSt.MVA, 
				new Percentual(10));
		
		assertEquals(new Dinheiro(2420),st.baseCalculo());
		assertEquals(new Dinheiro(435.6),st.valor());
	}

}
