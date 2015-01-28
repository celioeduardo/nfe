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
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.cancelamento.CancelamentoService;
import com.hadrion.nfe.dominio.modelo.portal.cancelamento.RetornoCancelamento;

public class CancelarNotaServiceTest extends DominioTest {
	
	@InjectMocks
	CancelarNotaService cancelarNotaService;
	
	@Mock
	CancelamentoService cancelamentoService;

	@Mock
	NotaFiscalRepositorio notaFiscalRepositorio;
	
	NotaFiscal notaAutorizada;
	
	NotaFiscal notaEmitida;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		
		notaAutorizada = notaAutorizadaHomologacaoPersistidaParaTest("1111");
		
		notaEmitida = notaEmitidaProducaoPersistidaParaTest("10");
		
		MockitoAnnotations.initMocks(this);
		
		when(cancelamentoService.cancelar(any(SolicitacaoCancelamento.class)))
			.thenReturn(new RetornoCancelamento(
					new NumeroProtocolo("CANC-1111"),
					new Mensagem(101, "Cancelamento Homologado"),
					new Date()));
		
		when(notaFiscalRepositorio.notaFiscalPeloId(new NotaFiscalId("1111")))
			.thenReturn(notaAutorizada);
		
		when(notaFiscalRepositorio.notaFiscalPeloId(new NotaFiscalId("10")))
			.thenReturn(notaEmitida);
	}
	
	@Test
	public void cancelar_nota_autorizada_em_homologacao(){
		cancelarNotaService.cancelar(new SolicitacaoCancelamento(notaAutorizada.notaFiscalId(),"Erro no valor total da NF-e"));
		eventosEsperados(2);
		eventoEsperado(CancelamentoHomologado.class);
	}
	
	@Test
	public void cancelar_nota_autorizada_em_producao(){
		cancelarNotaService.cancelar(new SolicitacaoCancelamento(notaAutorizada.notaFiscalId(),"Erro no valor total da NF-e"));
		eventosEsperados(2);
		eventoEsperado(CancelamentoHomologado.class);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_cancelar_em_homologacao_nota_emitida(){
		cancelarNotaService.cancelar(new SolicitacaoCancelamento(notaEmitida.notaFiscalId(),"Erro no valor total da NF-e"));
	} 
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_cancelar_em_producao_nota_emitida(){
		cancelarNotaService.cancelar(new SolicitacaoCancelamento(notaEmitida.notaFiscalId(),"Erro no valor total da NF-e"));
	} 
	
	
}