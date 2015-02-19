package com.hadrion.nfe.aplicacao.nf;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.aplicacao.nf.DefinirNotaFiscalEmLoteComando;
import com.hadrion.nfe.aplicacao.nf.NotaFiscalAplicacaoService;
import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;

public class NotaFiscalAplicacaoServiceTest extends DominioTest{
	
	@Autowired
	private NotaFiscalAplicacaoService notaFiscalAplicacaoService;
	
	@Autowired
	EntityManager em;

	@Before
	public void setUp() throws Exception{
		super.setUp();
	}
	
	@Test
	public void definirNotaFiscalEmLote(){
		
		NotaFiscal nf = notaEmitidaHomologacaoPersistidaParaTest("1234");
		
		DefinirNotaFiscalEmLoteComando comando = 
			new DefinirNotaFiscalEmLoteComando(
					nf.notaFiscalId().id());
		
		notaFiscalAplicacaoService.definirNotaEmLote(comando);
		
		em.flush();
		em.clear();
		
		nf = notaFiscalRepositorio.notaFiscalPeloId(nf.notaFiscalId());
		
		assertTrue(nf.estaEmLote());
		assertFalse(nf.pendenteDeTransmissao());
		
	}
	
	
}
