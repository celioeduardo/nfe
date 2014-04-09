package com.hadrion.nfe.dominio.modelo.lote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.recepcao.MockRecepcaoLoteService;

public class EnviarLoteServiceTest  extends AbstractLoteServiceTest {
	
	@Before
	public void setup() throws Exception{
		super.setUp();
	}
	
	@Test
	public void enviar_com_sucesso_em_homologacao(){
		Lote lote = loteEmHomologacaoNaoEnviadoParaTest();
		EnviarLoteService enviarLoteService = new EnviarLoteService(
				new MockRecepcaoLoteService("123456"));
		enviarLoteService.enviar(lote);
		assertTrue("Lote tem que estar Processando",lote.estaProcessando());
		assertEquals(new NumeroReciboLote("123456"),lote.numeroRecibo());
		assertNull(lote.mensagemErro());		
	}

	@Test
	public void enviar_com_sucesso_em_producao(){
		Lote lote = loteEmProducaoNaoEnviadoParaTest();
		EnviarLoteService enviarLoteService = new EnviarLoteService(
				new MockRecepcaoLoteService("123456"));
		enviarLoteService.enviar(lote);
		assertTrue("Lote tem que estar Processando",lote.estaProcessando());
		assertEquals(new NumeroReciboLote("123456"),lote.numeroRecibo());
		assertNull(lote.mensagemErro());		
	}
	
	@Test
	public void enviar_com_falha_consistencia_em_homologacao(){
		Lote lote = loteEmHomologacaoNaoEnviadoParaTest();
		EnviarLoteService enviarLoteService = new EnviarLoteService(
				new MockRecepcaoLoteService(
						new Mensagem(214, "Rejeição: Tamanho da mensagem excedeu o limite estabelecido")));
		enviarLoteService.enviar(lote);
		assertTrue("Lote tem que estar Inconsistente",lote.estaInconsistente());
		assertNull("Número do Recibo tem que ser nulo",lote.numeroRecibo());
		assertEquals(new Mensagem(214, "Rejeição: Tamanho da mensagem excedeu o limite estabelecido"),lote.mensagemErro());
	}
	@Test
	public void enviar_com_falha_consistencia_em_producao(){
		Lote lote = loteEmProducaoNaoEnviadoParaTest();
		EnviarLoteService enviarLoteService = new EnviarLoteService(
				new MockRecepcaoLoteService(
						new Mensagem(214, "Rejeição: Tamanho da mensagem excedeu o limite estabelecido")));
		enviarLoteService.enviar(lote);
		assertTrue("Lote tem que estar Inconsistente",lote.estaInconsistente());
		assertNull("Número do Recibo tem que ser nulo",lote.numeroRecibo());
		assertEquals(new Mensagem(214, "Rejeição: Tamanho da mensagem excedeu o limite estabelecido"),lote.mensagemErro());
	}
	@Test
	public void enviar_com_erro_transmissao_em_homologacao(){
		Lote lote = loteEmHomologacaoNaoEnviadoParaTest();
		EnviarLoteService enviarLoteService = new EnviarLoteService(
				new MockRecepcaoLoteService(new Exception("Internet indisponível")));
		enviarLoteService.enviar(lote);
		assertTrue("Lote tem que estar com erro de tramissão",lote.comErroTransmissao());
		assertNull("Número do Recibo tem que ser nulo",lote.numeroRecibo());
		assertEquals(new Mensagem(-1, "Internet indisponível"),lote.mensagemErro());
	}
	@Test
	public void enviar_com_erro_transmissao_em_producao(){
		Lote lote = loteEmProducaoNaoEnviadoParaTest();
		EnviarLoteService enviarLoteService = new EnviarLoteService(
				new MockRecepcaoLoteService(new Exception("Internet indisponível")));
		enviarLoteService.enviar(lote);
		assertTrue("Lote tem que estar com erro de tramissão",lote.comErroTransmissao());
		assertNull("Número do Recibo tem que ser nulo",lote.numeroRecibo());
		assertEquals(new Mensagem(-1, "Internet indisponível"),lote.mensagemErro());
	}
	
	protected Lote loteEmHomologacaoNaoEnviadoParaTest() {
		Lote lote = geracaoLoteService.gerarLoteEmHomologacao(
				notaEmitidaHomologacaoPersistidaParaTest("1111"));
		loteRepositorio.salvar(lote);
		return lote;
	}	
	protected Lote loteEmProducaoNaoEnviadoParaTest() {
		Lote lote = geracaoLoteService.gerarLoteEmProducao(
				notaEmitidaProducaoPersistidaParaTest("1111"));
		loteRepositorio.salvar(lote);
		return lote;
	}	
}
