package com.hadrion.nfe.dominio.modelo.cancelamento;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.cancelamento.CancelamentoNfeService;
import com.hadrion.nfe.dominio.modelo.portal.cancelamento.RetornoCancelamento;

public class CancelarNotaServiceMockTest extends DominioTest {
	
	@InjectMocks
	CancelarNotaService cancelarNotaService;
	
	@Mock
	CancelamentoNfeService cancelamentoNfseService;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		
		MockitoAnnotations.initMocks(this);
		
		when(cancelamentoNfseService.cancelar(any(SolicitacaoCancelamentoId.class)))
			.thenReturn(new RetornoCancelamento(
					new NumeroProtocolo("CANC-1111"),
					new Mensagem(101, "Cancelamento Homologado"),
					new Date()));
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