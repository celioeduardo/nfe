package com.hadrion.nfe.dominio.modelo.cancelamento;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;

public class CancelarNotaServiceTest extends DominioTest {
	
	private CancelarNotaService cancelarNotaService;
	private CancelarNotaServiceFabrica cancelarNotaServiceFabrica;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		cancelarNotaServiceFabrica = MockCancelarNotaServiceFabrica.cancelamentoHomologado();
		cancelarNotaService = cancelarNotaServiceFabrica.criarCancelarNotaService();
	}
	
	@Test
	public void cancelar_nota_autorizada_em_homologacao(){
		NotaFiscal nota= notaAutorizadaHomologacaoPersistidaParaTest("1111");
		cancelarNotaService.cancelarEmHomologacao(nota);
		eventosEsperados(1);
		eventoEsperado(CancelamentoHomologado.class);
	}
	
	@Test
	public void cancelar_nota_autorizada_em_producao(){
		NotaFiscal nota = notaAutorizadaProducaoPersistidaParaTest("1111");
		cancelarNotaService.cancelarEmProducao(nota);
		eventosEsperados(1);
		eventoEsperado(CancelamentoHomologado.class);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_cancelar_em_homologacao_nota_emitida(){
		NotaFiscal nota = notaEmitidaHomologacaoPersistidaParaTest("10");
		cancelarNotaService.cancelarEmHomologacao(nota);
	} 
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_cancelar_em_producao_nota_emitida(){
		NotaFiscal nota = notaEmitidaProducaoPersistidaParaTest("10");
		cancelarNotaService.cancelarEmProducao(nota);
	} 
	
	
}