package com.hadrion.nfe.dominio.modelo.cce;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

public class RegistrarCartaCorrecaoServiceTest extends DominioTest{

	@Autowired
	private RegistrarCartaCorrecaoService registrarCartaCorrecaoService;
	
	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test @Ignore
	public void cancelar(){
		registrarCartaCorrecaoService.registar(
				new NotaFiscalId("H-03ADF8A01D1727DDE050007F01002730"), 
				"Teste de correção de Nota Fiscal");
		
		NotaFiscal nf = notaFiscalRepositorio.notaFiscalPeloId(new NotaFiscalId("H-03ADF8A01D1727DDE050007F01002730"));
		
		assertEquals("Teste de correção de Nota Fiscal", nf.cartaCorrecaoAtual().correcao());
		assertNotNull(nf.cartaCorrecaoAtual().dataRegistro());
	}
	
	
}
