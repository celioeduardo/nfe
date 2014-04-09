package com.hadrion.nfe.dominio.modelo.lote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.comum.dominio.modelo.EventoDominioAssinante;
import com.hadrion.comum.dominio.modelo.EventoDominioPublicador;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.MensagemSefaz;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.consulta.ConsultaProcessamentoLoteService;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.consulta.ProtocoloNotaProcessada;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.consulta.RetornoConsultaProcessamentoLote;

public class ProcessarRetornoLoteServiceTest extends AbstractLoteServiceTest{
	
	private List<NotaFiscalId> listaNotaFiscalId;
	
	@Before
	public void setup() throws Exception{
		super.setUp();
	}
	
	@Test
	public void lote_processado_em_homologacao(){
		ProcessarRetornoLoteService processarRetorno = new ProcessarRetornoLoteService(
				new ConsultaProcessamentoLoteService() {
					@Override
					public RetornoConsultaProcessamentoLote consultar(Lote lote) {
						return new RetornoConsultaProcessamentoLote(
								new Mensagem(104, "Lote processado"), 
								MensagemSefaz.vazia(), 
								Collections.<ProtocoloNotaProcessada>emptyList());
					}
				});

		Lote lote = LoteProcessandoEmHomologacaoPersistidoParaTest(
						notaEmitidaHomologacaoPersistidaParaTest("1234"));
		processarRetorno.processar(lote);
		assertTrue("Lote deve estar processado.",lote.estaProcessado());
	}
	
	@Test
	public void lote_processando_em_homologacao(){
		ProcessarRetornoLoteService processarRetorno = new ProcessarRetornoLoteService(
				new ConsultaProcessamentoLoteService() {
					@Override
					public RetornoConsultaProcessamentoLote consultar(Lote lote) {
						return new RetornoConsultaProcessamentoLote(
								new Mensagem(105, "Lote em processamento"), 
								MensagemSefaz.vazia(), 
								Collections.<ProtocoloNotaProcessada>emptyList());
					}
				});
		Lote lote = LoteProcessandoEmHomologacaoPersistidoParaTest(
						notaEmitidaHomologacaoPersistidaParaTest("1234"));
		processarRetorno.processar(lote);
		assertTrue("Lote deve estar processando.",lote.estaProcessando());
	}
	
	@Test
	public void lote_inconsistente_em_homologacao(){
		ProcessarRetornoLoteService processarRetorno = new ProcessarRetornoLoteService(
				new ConsultaProcessamentoLoteService() {
					@Override
					public RetornoConsultaProcessamentoLote consultar(Lote lote) {
						return new RetornoConsultaProcessamentoLote(
								new Mensagem(215, "Rejeição: Falha no schema XML"), 
								MensagemSefaz.vazia(), 
								Collections.<ProtocoloNotaProcessada>emptyList());
					}
				});
		Lote lote = LoteProcessandoEmHomologacaoPersistidoParaTest(
						notaEmitidaHomologacaoPersistidaParaTest("1234"));
		processarRetorno.processar(lote);
		assertTrue("Lote deve estar inconsistente.",lote.estaInconsistente());
	}
	
	@Test
	public void notas_autorizadas_em_homologacao(){
		ProcessarRetornoLoteService processarRetorno = new ProcessarRetornoLoteService(
				new ConsultaProcessamentoLoteService() {
					@Override
					public RetornoConsultaProcessamentoLote consultar(Lote lote) {
						List<ProtocoloNotaProcessada> protocolos = Arrays.asList(
								new ProtocoloNotaProcessada(
										new Date(), 
										new NumeroProtocolo("PROTO-1111"), 
										new Mensagem(100, "Autorizado o Uso da NF-e"), 
										new NotaFiscalId("1111")),
								new ProtocoloNotaProcessada(
										new Date(), 
										new NumeroProtocolo("PROTO-1112"), 
										new Mensagem(100, "Autorizado o Uso da NF-e"), 
										new NotaFiscalId("1112"))
								);
										
						return new RetornoConsultaProcessamentoLote(
								new Mensagem(104, "Lote processado"), 
								MensagemSefaz.vazia(), 
								protocolos);
					}
				});
		
		listaNotaFiscalId = new ArrayList<NotaFiscalId>();
		
		EventoDominioPublicador.instancia().assinar(
				new EventoDominioAssinante<NotaFiscalAutorizada>() {

					@Override
					public void tratarEvento(NotaFiscalAutorizada eventoDominio) {
						listaNotaFiscalId.add(eventoDominio.notaFiscalId());
						assertEquals(Ambiente.HOMOLOGACAO,eventoDominio.ambiente());
					}

					@Override
					public Class<NotaFiscalAutorizada> inscritoParaTipoEvento() {
						return NotaFiscalAutorizada.class;
					}
		});
		
		Lote lote = LoteProcessandoEmHomologacaoPersistidoParaTest(
						notaEmitidaHomologacaoPersistidaParaTest("1111"));
		processarRetorno.processar(lote);
		assertTrue("Lote processado.",lote.estaProcessado());
		assertTrue("Nota está autorizada",lote.estaAutorizada(new NotaFiscalId("1111")));
		assertTrue("Evento disparado", listaNotaFiscalId.contains(new NotaFiscalId("1111")));
	}
	
	@Test
	public void notas_rejeitadas_em_homologacao(){
		ProcessarRetornoLoteService processarRetorno = new ProcessarRetornoLoteService(
				new ConsultaProcessamentoLoteService() {
					@Override
					public RetornoConsultaProcessamentoLote consultar(Lote lote) {
						List<ProtocoloNotaProcessada> protocolos = Arrays.asList(
								new ProtocoloNotaProcessada(
										new Date(), 
										null,
										new Mensagem(228, "Rejeição: Data de emissão muito atrasada"), 
										new NotaFiscalId("1111")),
								new ProtocoloNotaProcessada(
										new Date(), 
										null, 
										new Mensagem(229, "Rejeição: IE do emitente não informada"), 
										new NotaFiscalId("1112"))
								);
										
						return new RetornoConsultaProcessamentoLote(
								new Mensagem(104, "Lote processado"), 
								MensagemSefaz.vazia(), 
								protocolos);
					}
				});
		
		
		Lote lote = LoteProcessandoEmHomologacaoPersistidoParaTest(notaEmitidaHomologacaoPersistidaParaTest("1111"));
		processarRetorno.processar(lote);
		assertTrue("Lote deve estar processado.",lote.estaProcessado());
		assertTrue("Nota não está rejeitada",lote.estaRejeitada(new NotaFiscalId("1111")));
	}
	
	@Test
	public void notas_denegadas_em_homologacao(){
		ProcessarRetornoLoteService processarRetorno = new ProcessarRetornoLoteService(
				new ConsultaProcessamentoLoteService() {
					@Override
					public RetornoConsultaProcessamentoLote consultar(Lote lote) {
						List<ProtocoloNotaProcessada> protocolos = Arrays.asList(
								new ProtocoloNotaProcessada(
										new Date(), 
										null,
										new Mensagem(110, "Uso Denegado"), 
										new NotaFiscalId("1111")),
								new ProtocoloNotaProcessada(
										new Date(), 
										null, 
										new Mensagem(110, "Uso Denegado"), 
										new NotaFiscalId("1112"))
								);
										
						return new RetornoConsultaProcessamentoLote(
								new Mensagem(104, "Lote processado"), 
								MensagemSefaz.vazia(), 
								protocolos);
					}
				});
		
		listaNotaFiscalId = new ArrayList<NotaFiscalId>();
		
		EventoDominioPublicador.instancia().assinar(
				new EventoDominioAssinante<NotaFiscalDenegada>() {

					@Override
					public void tratarEvento(NotaFiscalDenegada eventoDominio) {
						listaNotaFiscalId.add(eventoDominio.notaFiscalId());
						assertEquals(Ambiente.HOMOLOGACAO,eventoDominio.ambiente());
					}

					@Override
					public Class<NotaFiscalDenegada> inscritoParaTipoEvento() {
						return NotaFiscalDenegada.class;
					}
		});
		
		Lote lote = LoteProcessandoEmHomologacaoPersistidoParaTest(notaEmitidaHomologacaoPersistidaParaTest("1111"));
		processarRetorno.processar(lote);
		assertTrue("Lote deve estar processado.",lote.estaProcessado());
		assertTrue("Nota não está denegada",lote.estaDenegada(new NotaFiscalId("1111")));
		assertTrue("Evento disparado", listaNotaFiscalId.contains(new NotaFiscalId("1111")));
	}
	
}
