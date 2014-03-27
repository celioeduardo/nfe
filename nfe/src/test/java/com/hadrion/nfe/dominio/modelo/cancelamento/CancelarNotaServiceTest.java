package com.hadrion.nfe.dominio.modelo.cancelamento;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.comum.dominio.modelo.EventoDominioAssinante;
import com.hadrion.comum.dominio.modelo.EventoDominioPublicador;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.cancelamento.CancelamentoNfeService;
import com.hadrion.nfe.dominio.modelo.portal.cancelamento.RetornoCancelamento;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.MockNotaFiscalRepositorio;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.MockSolicitacaoCancelamentoRepositorio;

public class CancelarNotaServiceTest {

	private SolicitacaoCancelamentoRepositorio solicitacaoCancelamentoRepositorio;
	private NotaFiscalRepositorio notaFiscalRepositorio;
	private CancelarNotaService cancelarNotaService;
	
	
	private NotaFiscalId notaFiscalId;
	
	@Before
	public void setUp(){
		notaFiscalRepositorio = new MockNotaFiscalRepositorio();
		solicitacaoCancelamentoRepositorio = new MockSolicitacaoCancelamentoRepositorio();
		EventoDominioPublicador.instancia().reset();
	}
	
	@Test
	public void cancelar_nota_autorizada_em_homologacao(){
		CancelamentoNfeService cancelamentoNfeService = new CancelamentoNfeService() {
			@Override
			public RetornoCancelamento cancelar(SolicitacaoCancelamentoId solicitacao) {
				return new RetornoCancelamento(
						new NumeroProtocolo("CANC-1111"),
						new Mensagem(101, "Cancelamento Homologado"),
						new Date());
			}
		};
		
		EventoDominioPublicador.instancia().assinar(
				new EventoDominioAssinante<CancelamentoHomologado>() {

					@Override
					public void tratarEvento(
							CancelamentoHomologado eventoDominio) {
						assertEquals(Ambiente.HOMOLOGACAO,eventoDominio.ambiente());
						notaFiscalId = eventoDominio.notaFiscalId();
					}

					@Override
					public Class<CancelamentoHomologado> inscritoParaTipoEvento() {
						return CancelamentoHomologado.class;
					}
				});
		
		cancelarNotaService = new CancelarNotaService(
				cancelamentoNfeService, 
				solicitacaoCancelamentoRepositorio,
				notaFiscalRepositorio);
		
		
		notaFiscalId = null;
		notaFiscalRepositorio.salvar(fixtureNotaAutorizadaEmHomologacao("1111"));
		cancelarNotaService.cancelarEmHomologacao(new NotaFiscalId("1111"));
		assertEquals(new NotaFiscalId("1111"), notaFiscalId);
		
	}
	@Test
	public void cancelar_nota_autorizada_em_producao(){
		CancelamentoNfeService cancelamentoNfeService = new CancelamentoNfeService() {
			@Override
			public RetornoCancelamento cancelar(SolicitacaoCancelamentoId solicitacao) {
				return new RetornoCancelamento(
						new NumeroProtocolo("CANC-1111"),
						new Mensagem(101, "Cancelamento Homologado"),
						new Date());
			}
		};
		
		EventoDominioPublicador.instancia().assinar(
				new EventoDominioAssinante<CancelamentoHomologado>() {
					
					@Override
					public void tratarEvento(
							CancelamentoHomologado eventoDominio) {
						assertEquals(Ambiente.PRODUCAO,eventoDominio.ambiente());
						notaFiscalId = eventoDominio.notaFiscalId();
					}
					
					@Override
					public Class<CancelamentoHomologado> inscritoParaTipoEvento() {
						return CancelamentoHomologado.class;
					}
				});
		
		cancelarNotaService = new CancelarNotaService(
				cancelamentoNfeService, 
				solicitacaoCancelamentoRepositorio,
				notaFiscalRepositorio);
		
		notaFiscalId = null;
		notaFiscalRepositorio.salvar(fixtureNotaAutorizadaEmProducao("1111"));
		cancelarNotaService.cancelarEmProducao(new NotaFiscalId("1111"));
		assertEquals(new NotaFiscalId("1111"), notaFiscalId);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_cancelar_em_homologacao_nota_emitida(){
		cancelarNotaService = new CancelarNotaService(
				null, 
				solicitacaoCancelamentoRepositorio,
				notaFiscalRepositorio);
		
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