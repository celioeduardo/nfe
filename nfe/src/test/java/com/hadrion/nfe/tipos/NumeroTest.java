package com.hadrion.nfe.tipos;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumeroTest {
	
	@Test
	public void problemaComDouble(){
		double val = 0.0;
		for (int i =0; i < 10; i++)
			val += 0.1;
		System.out.println(val);
		assertFalse(val == 1.0);
	}

	@Test
	public void multiplicacaoComDinheiro(){
		Dinheiro dinheiro = Dinheiro.ZERO;
		for (int i =0; i < 10; i++)
			dinheiro = dinheiro.soma(new Dinheiro(0.1));
		assertEquals(new Dinheiro(1.0), dinheiro);
	}
	
	@Test
	public void calculoImposto(){
		Dinheiro baseCalculo = new Dinheiro(1000);
		Aliquota aliquota = new Aliquota(18);
		assertEquals(new Dinheiro(180),baseCalculo.multiplicar(aliquota.valorDecimal()));
	}
	
	@Test
	public void calculoImpostoComDiferimentoTotal(){
		Dinheiro baseCalculo = new Dinheiro(1000);
		Aliquota aliquota = new Aliquota(18);
		Percentual percentualDiferimento = Percentual.CEM;
		
		assertEquals("valor do imposto sem diferimento", 
				new Dinheiro(180),
				baseCalculo.multiplicar(aliquota.valorDecimal()));
		
		assertEquals("valor do imposto", 
				Dinheiro.ZERO,
				baseCalculo
					.multiplicar(aliquota.valorDecimal())
					.multiplicar(1-percentualDiferimento.valorDecimal()));
		
		assertEquals("valor diferido", 
				new Dinheiro(180),
				baseCalculo
					.multiplicar(aliquota.valorDecimal())
					.multiplicar(percentualDiferimento.valorDecimal()));
	}
	
	@Test
	public void calculoImpostoComDiferimentoDe60PorCento(){
		Dinheiro baseCalculo = new Dinheiro(1000);
		Aliquota aliquota = new Aliquota(18);
		Percentual percentualDiferimento = new Percentual(60);
		
		assertEquals("valor do imposto sem diferimento", 
				new Dinheiro(180),
				baseCalculo.multiplicar(aliquota.valorDecimal()));
		
		assertEquals("valor do imposto", 
				new Dinheiro(72),
				baseCalculo
				.multiplicar(aliquota.valorDecimal())
				.multiplicar(1-percentualDiferimento.valorDecimal()));
		
		assertEquals("valor diferido", 
				new Dinheiro(108),
				baseCalculo
				.multiplicar(aliquota.valorDecimal())
				.multiplicar(percentualDiferimento.valorDecimal()));
	}
	
}
