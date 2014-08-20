package com.hadrion.nfe.dominio.modelo;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
import com.hadrion.nfe.tipos.Dinheiro;

public class IcmsTest {
	
	@Test
	public void tributadoIntegralmente(){
		
		Icms icms = Icms.tributacaoIntegral_00(
				Origem.NACIONAL, 
				new Dinheiro(1000), 
				18.0, 
				DeterminacaoBaseCalculo.VALOR_OPERACAO);
		
		assertEquals(new Dinheiro(180.0),icms.valor());
	}
	@Test
	public void tributadoIntegralmenteCriadoPelaCst(){
		
		assertEquals(
			Icms.tributacaoIntegral_00(
				Origem.NACIONAL, 
				new Dinheiro(1000), 
				18.0, 
				DeterminacaoBaseCalculo.VALOR_OPERACAO),
			Icms.cst_00(
				Origem.NACIONAL, 
				new Dinheiro(1000), 
				18.0, 
				DeterminacaoBaseCalculo.VALOR_OPERACAO)
		);
		
	}
	
}
