package com.hadrion.nfe.dominio.modelo.lote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.AutorizacaoService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.ReciboLote;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.RetornoAutorizacao;

public class EnviarLoteServiceTest  extends AbstractLoteServiceTest {
	
	@InjectMocks
	private EnviarLoteService enviarLoteService;
	
	@Mock
	private AutorizacaoService recepcaoLoteService;
		
	@Before
	public void setup() throws Exception{
		super.setUp();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void enviar_com_sucesso_em_homologacao() throws Throwable{
		
		when(recepcaoLoteService.autorizar(any(Lote.class),any(Certificado.class))).thenReturn(
				new RetornoAutorizacao(
					new ReciboLote(new NumeroReciboLote("123456"))));
		
		Lote lote = loteEmHomologacaoNaoEnviadoParaTest();
		enviarLoteService.enviar(lote);
		assertTrue("Lote tem que estar Processando",lote.estaProcessando());
		assertEquals(new NumeroReciboLote("123456"),lote.numeroRecibo());
		assertNull(lote.mensagemErro());		
	}

	@Test
	public void enviar_com_sucesso_em_producao() throws Throwable{
		when(recepcaoLoteService.autorizar(any(Lote.class),any(Certificado.class))).thenReturn(
				new RetornoAutorizacao(
					new ReciboLote(new NumeroReciboLote("123456"))));
		Lote lote = loteEmProducaoNaoEnviadoParaTest();
		enviarLoteService.enviar(lote);
		assertTrue("Lote tem que estar Processando",lote.estaProcessando());
		assertEquals(new NumeroReciboLote("123456"),lote.numeroRecibo());
		assertNull(lote.mensagemErro());		
	}
	
	@Test
	public void enviar_com_falha_consistencia_em_homologacao() throws Throwable{
		when(recepcaoLoteService.autorizar(any(Lote.class),any(Certificado.class))).thenReturn(
				new RetornoAutorizacao(
						new Mensagem(214, "Rejeição: Tamanho da mensagem excedeu o limite estabelecido")));
		Lote lote = loteEmHomologacaoNaoEnviadoParaTest();
		enviarLoteService.enviar(lote);
		assertTrue("Lote tem que estar Inconsistente",lote.estaInconsistente());
		assertNull("Número do Recibo tem que ser nulo",lote.numeroRecibo());
		assertEquals(new Mensagem(214, "Rejeição: Tamanho da mensagem excedeu o limite estabelecido"),lote.mensagemErro());
	}
	
	@Test
	public void enviar_com_falha_consistencia_em_producao() throws Throwable{
		when(recepcaoLoteService.autorizar(any(Lote.class),any(Certificado.class))).thenReturn(
				new RetornoAutorizacao(
						new Mensagem(214, "Rejeição: Tamanho da mensagem excedeu o limite estabelecido")));
		Lote lote = loteEmProducaoNaoEnviadoParaTest();
		enviarLoteService.enviar(lote);
		assertTrue("Lote tem que estar Inconsistente",lote.estaInconsistente());
		assertNull("Número do Recibo tem que ser nulo",lote.numeroRecibo());
		assertEquals(new Mensagem(214, "Rejeição: Tamanho da mensagem excedeu o limite estabelecido"),lote.mensagemErro());
	}
	
	@Test
	public void enviar_com_erro_transmissao_em_homologacao() throws Throwable{
		when(recepcaoLoteService.autorizar(any(Lote.class),any(Certificado.class)))
			.thenThrow(new Exception("Internet indisponível"));
		
		Lote lote = loteEmHomologacaoNaoEnviadoParaTest();
		enviarLoteService.enviar(lote);
		assertTrue("Lote tem que estar com erro de tramissão",lote.comErroTransmissao());
		assertNull("Número do Recibo tem que ser nulo",lote.numeroRecibo());
		assertEquals(new Mensagem(-1, "Internet indisponível"),lote.mensagemErro());
	}
	
	@Test
	public void enviar_com_erro_transmissao_em_producao() throws Throwable{
		when(recepcaoLoteService.autorizar(any(Lote.class),any(Certificado.class)))
			.thenThrow(new Exception("Internet indisponível"));
		Lote lote = loteEmProducaoNaoEnviadoParaTest();
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
