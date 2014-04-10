package com.hadrion.nfe.dominio.modelo.lote;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.MensagemSefaz;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.consulta.ConsultaProcessamentoLoteService;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.consulta.ProtocoloNotaProcessada;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.consulta.RetornoConsultaProcessamentoLote;

public class ProcessarRetornoLoteServiceTest extends AbstractLoteServiceTest{
	
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

		Lote lote = loteProcessandoEmHomologacaoPersistidoParaTest(
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
		Lote lote = loteProcessandoEmHomologacaoPersistidoParaTest(
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
		Lote lote = loteProcessandoEmHomologacaoPersistidoParaTest(
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
		
		NotaFiscal nf1111 = notaEmitidaHomologacaoPersistidaParaTest("1111");
		NotaFiscal nf1112 = notaEmitidaHomologacaoPersistidaParaTest("1112");
		
		Lote lote = loteProcessandoEmHomologacaoPersistidoParaTest(nf1111,nf1112);
		processarRetorno.processar(lote);
		eventosEsperados(2);
		eventoEsperado(NotaFiscalAutorizada.class,2);
		assertTrue("Lote processado.",lote.estaProcessado());
		assertTrue("Nota está autorizada",lote.estaAutorizada(nf1111.notaFiscalId()));
		assertTrue("Nota está autorizada",lote.estaAutorizada(nf1112.notaFiscalId()));
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
		NotaFiscal nf1111 = notaEmitidaHomologacaoPersistidaParaTest("1111");
		NotaFiscal nf1112 = notaEmitidaHomologacaoPersistidaParaTest("1112");
		
		Lote lote = loteProcessandoEmHomologacaoPersistidoParaTest(nf1111,nf1112);
		processarRetorno.processar(lote);
		assertTrue("Lote deve estar processado.",lote.estaProcessado());
		assertTrue("Nota não está rejeitada",lote.estaRejeitada(nf1111.notaFiscalId()));
		assertTrue("Nota não está rejeitada",lote.estaRejeitada(nf1112.notaFiscalId()));
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
		
		NotaFiscal nf1111 =notaEmitidaHomologacaoPersistidaParaTest("1111");
		NotaFiscal nf1112 =notaEmitidaHomologacaoPersistidaParaTest("1112");
		Lote lote = loteProcessandoEmHomologacaoPersistidoParaTest(nf1111,nf1112);
		processarRetorno.processar(lote);
		eventosEsperados(2);
		eventoEsperado(NotaFiscalDenegada.class,2);
		assertTrue("Lote deve estar processado.",lote.estaProcessado());
		assertTrue("Nota deve estar denegada",lote.estaDenegada(nf1111.notaFiscalId()));
		assertTrue("Nota deve estar denegada",lote.estaDenegada(nf1112.notaFiscalId()));
	}
	
}
