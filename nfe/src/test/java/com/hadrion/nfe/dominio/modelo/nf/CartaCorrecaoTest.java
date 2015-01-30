package com.hadrion.nfe.dominio.modelo.nf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.DominioTest;

public class CartaCorrecaoTest extends DominioTest{
	
	private NotaFiscal nf;
	private NotaFiscal nfCorrigida;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		nf = NotaFiscalFixture.nfEmProducaoAutorizada();
		
		nfCorrigida = NotaFiscalFixture.nfEmProducaoAutorizada();
		nfCorrigida.registrarCartaCorrecao("Teste de Carta de Correção",new Date());
	}
	
	@Test
	public void registrar(){
		assertNull(nf.cartaCorrecaoAtual());
		
		Date dataRegistro = new Date();
		
		nf.registrarCartaCorrecao("Teste de Carta de Correção",dataRegistro);
		
		CartaCorrecao cartaCorrecaoAtual = nf.cartaCorrecaoAtual();
		
		assertEquals(1,cartaCorrecaoAtual.sequencia());
		assertEquals("Teste de Carta de Correção",cartaCorrecaoAtual.correcao());
		assertEquals(dataRegistro,cartaCorrecaoAtual.dataRegistro());
	}
	
	@Test
	public void variasCorrecoes(){
		Date dataRegistro = new Date();

		nf.registrarCartaCorrecao("Teste de Carta de Correção",dataRegistro);
		nf.registrarCartaCorrecao("Outra Carta de Correção",dataRegistro);
		
		assertEquals(2,nf.cartaCorrecaoAtual().sequencia());
		assertEquals("Outra Carta de Correção",nf.cartaCorrecaoAtual().correcao());
		assertEquals(dataRegistro,nf.cartaCorrecaoAtual().dataRegistro());
	}
	
}
