package com.hadrion.nfe.dominio.modelo.icms;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Percentual;

public class IcmsTest {
	
	@Test
	public void tributadoIntegralmente(){
		
		Icms icms = Icms.tributacaoIntegral_00(
				Origem.NACIONAL, 
				new Dinheiro(1000),
				new Aliquota(18.0), 
				DeterminacaoBaseCalculo.VALOR_OPERACAO);
		
		assertEquals(new Dinheiro(180.0),icms.valor());
	}
	
	@Test
	public void tributadoIntegralmenteCriadoPelaCst(){
		
		assertEquals(
			Icms.tributacaoIntegral_00(
				Origem.NACIONAL, 
				new Dinheiro(1000), 
				new Aliquota(18.0), 				
				DeterminacaoBaseCalculo.VALOR_OPERACAO),
			Icms.cst_00(
				Origem.NACIONAL, 
				new Dinheiro(1000), 
				new Aliquota(18.0), 
				DeterminacaoBaseCalculo.VALOR_OPERACAO)
		);
		
	}
	
	@Test
	public void icmsCst51ComDiferimentoTotal(){
		Icms icms = Icms.cst_51(
				Origem.NACIONAL, 
				new Dinheiro(1000), 
				new Aliquota(18),
				Percentual.ZERO,
				Dinheiro.ZERO,
				Percentual.CEM,
				DeterminacaoBaseCalculo.VALOR_OPERACAO);
		
		assertEquals(Dinheiro.ZERO,icms.valor());
	}
	
	@Test
	public void icmsCst51ComDiferimentoDe60PorCento(){
		Icms icms = Icms.cst_51(
				Origem.NACIONAL, 
				new Dinheiro(1000), 
				new Aliquota(18),
				Percentual.ZERO,
				Dinheiro.ZERO,
				new Percentual(60),
				DeterminacaoBaseCalculo.VALOR_OPERACAO);
		
		assertEquals(new Dinheiro(72),icms.valor());
		assertEquals(new Dinheiro(108),icms.valorDiferido());
		assertEquals(new Dinheiro(180),icms.valorSemDiferimento());
	}
	
	@Test
	public void icmsCst51ComReducao33PorCentoDiferimentoDe60PorCento(){
		Icms icms = Icms.cst_51(
				Origem.NACIONAL, 
				new Dinheiro(1000), 
				new Aliquota(18),
				new Percentual(33),
				new Dinheiro(120.6),
				new Percentual(60),
				DeterminacaoBaseCalculo.VALOR_OPERACAO);
		assertEquals(new Dinheiro(670.0),icms.baseCalculo());
		assertEquals(new Dinheiro(48.24),icms.valor());
		assertEquals(new Dinheiro(72.36),icms.valorDiferido());
		assertEquals(new Dinheiro(120.6),icms.valorSemDiferimento());
		assertEquals(new Dinheiro(120.6),icms.descontoReducaoBaseCalculo());
	}
	
	@Test
	public void icmsCst51SemDiferimento(){
		Icms icms = Icms.cst_51(
				Origem.NACIONAL, 
				new Dinheiro(1000), 
				new Aliquota(18),
				Percentual.ZERO,
				Dinheiro.ZERO,
				Percentual.ZERO,
				DeterminacaoBaseCalculo.VALOR_OPERACAO);
		
		assertEquals(new Dinheiro(180),icms.valor());
		assertEquals(Dinheiro.ZERO,icms.valorDiferido());
	}
	
	@Test
	public void icmsCst51ComReducao33PorCentoSemDiferimento(){
		Icms icms = Icms.cst_51(
				Origem.NACIONAL, 
				new Dinheiro(1000), 
				new Aliquota(18),
				new Percentual(33),
				new Dinheiro(120.6),
				Percentual.ZERO,
				DeterminacaoBaseCalculo.VALOR_OPERACAO);
		assertEquals(new Dinheiro(670.0),icms.baseCalculo());
		assertEquals(new Dinheiro(120.6),icms.valor());
		assertEquals(Dinheiro.ZERO,icms.valorDiferido());
		assertEquals(new Dinheiro(120.6),icms.descontoReducaoBaseCalculo());
	}
	
}
