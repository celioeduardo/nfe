package com.hadrion.nfe.dominio.modelo.lote;

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
import com.hadrion.nfe.dominio.modelo.Mensagem;
import com.hadrion.nfe.dominio.modelo.MensagemSefaz;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalAutorizada;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalDenegada;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.recepcao.consulta.ConsultaProcessamentoLoteService;
import com.hadrion.nfe.dominio.modelo.recepcao.consulta.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.recepcao.consulta.ProtocoloNotaProcessada;
import com.hadrion.nfe.dominio.modelo.recepcao.consulta.RetornoConsultaProcessamentoLote;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.MockLoteRepositorio;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.MockNotaFiscalRepositorio;

public class ProcessarRetornoLoteServiceTest extends AbstractLoteServiceTest{
	
	private GeracaoLoteService geracaoLoteService;
	
	private List<NotaFiscalId> listaNotaFiscalId;
	
	@Before
	public void setUp(){
		notaFiscalRepositorio = new MockNotaFiscalRepositorio();
		loteRepositorio = new MockLoteRepositorio();
		geracaoLoteService = new GeracaoLoteService(
				loteRepositorio, notaFiscalRepositorio);
		
		notaFiscalRepositorio.salvar(fixtureNotaFiscalEmitida("1111"));
		notaFiscalRepositorio.salvar(fixtureNotaFiscalEmitida("1112"));
		
		EventoDominioPublicador.instancia().reset();
	}
	
	@Test
	public void lote_processado(){
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
		Lote lote = fixtureLoteEmProcessamento();
		processarRetorno.processar(lote);
		assertTrue("Lote deve estar processado.",lote.estaProcessado());
	}
	
	@Test
	public void lote_em_processamento(){
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
		Lote lote = fixtureLoteEmProcessamento();
		processarRetorno.processar(lote);
		assertTrue("Lote deve estar em processamento.",lote.estaEmProcessamento());
	}
	
	@Test
	public void lote_inconsistente(){
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
		Lote lote = fixtureLoteEmProcessamento();
		processarRetorno.processar(lote);
		assertTrue("Lote deve estar inconsistente.",lote.estaInconsistente());
	}
	
	@Test
	public void notas_autorizadas(){
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
					}

					@Override
					public Class<NotaFiscalAutorizada> inscritoParaTipoEvento() {
						return NotaFiscalAutorizada.class;
					}
		});
		
		Lote lote = fixtureLoteEmProcessamento();
		processarRetorno.processar(lote);
		assertTrue("Lote processado.",lote.estaProcessado());
		assertTrue("Nota está autorizada",lote.estaAutorizada(new NotaFiscalId("1111")));
		assertTrue("Nota está autorizada",lote.estaAutorizada(new NotaFiscalId("1112")));
		assertTrue("Evento disparado", listaNotaFiscalId.contains(new NotaFiscalId("1111")));
		assertTrue("Evento disparado", listaNotaFiscalId.contains(new NotaFiscalId("1112")));
	}
	
	@Test
	public void notas_rejeitadas(){
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
		
		
		Lote lote = fixtureLoteEmProcessamento();
		processarRetorno.processar(lote);
		assertTrue("Lote deve estar processado.",lote.estaProcessado());
		assertTrue("Nota não está rejeitada",lote.estaRejeitada(new NotaFiscalId("1111")));
		assertTrue("Nota não está rejeitada",lote.estaRejeitada(new NotaFiscalId("1112")));
	}
	
	@Test
	public void notas_denegadas(){
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
					}

					@Override
					public Class<NotaFiscalDenegada> inscritoParaTipoEvento() {
						return NotaFiscalDenegada.class;
					}
		});
		
		Lote lote = fixtureLoteEmProcessamento();
		processarRetorno.processar(lote);
		assertTrue("Lote deve estar processado.",lote.estaProcessado());
		assertTrue("Nota não está denegada",lote.estaDenegada(new NotaFiscalId("1111")));
		assertTrue("Nota não está denegada",lote.estaDenegada(new NotaFiscalId("1112")));
		assertTrue("Evento disparado", listaNotaFiscalId.contains(new NotaFiscalId("1111")));
		assertTrue("Evento disparado", listaNotaFiscalId.contains(new NotaFiscalId("1112")));
	}
	
	protected Lote fixtureLoteEmProcessamento() {
		Lote lote = geracaoLoteService.gerarLoteEmHomologacao(listaNotaFiscalId("1111","1112"));
		lote.recebido(new NumeroReciboLote("123456"));
		return lote;
	}
	
	private NotaFiscal fixtureNotaFiscalEmitida(String numero){
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId(numero));
		nf.emitida();
		return nf;
	}
	
}
