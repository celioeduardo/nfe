package com.hadrion.nfe.dominio.modelo.cancelamento;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

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
		notaFiscalRepositorio.salvar(fixtureNotaAutorizadaEmHomologacao("1111"));
		cancelarNotaService.cancelarEmHomologacao(new NotaFiscalId("1111"));
		eventosEsperados(1);
		eventoEsperado(CancelamentoHomologado.class);
	}
	
	@Test
	public void cancelar_nota_autorizada_em_producao(){
		notaFiscalRepositorio.salvar(fixtureNotaAutorizadaEmProducao("1111"));
		cancelarNotaService.cancelarEmProducao(new NotaFiscalId("1111"));
		eventosEsperados(1);
		eventoEsperado(CancelamentoHomologado.class);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_cancelar_em_homologacao_nota_emitida(){
		notaFiscalRepositorio.salvar(this.fixtureNotaEmitida("10"));
		cancelarNotaService.cancelarEmHomologacao(new NotaFiscalId("10"));
	} 
	
	private NotaFiscal fixtureNotaEmitida(String numero){
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId(numero));
		nf.emitidaHomologacao();
		return nf;
	}
	
	private NotaFiscal fixtureNotaAutorizadaEmHomologacao(String numero){
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId(numero));
		nf.emitidaHomologacao();
		nf.autorizadaHomologacao();
		return nf;
	}
	private NotaFiscal fixtureNotaAutorizadaEmProducao(String numero){
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId(numero));
		nf.emitidaProducao();
		nf.autorizadaProducao();
		return nf;
	}
	
	
}