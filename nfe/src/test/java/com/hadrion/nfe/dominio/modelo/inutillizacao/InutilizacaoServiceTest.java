package com.hadrion.nfe.dominio.modelo.inutillizacao;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.InutilizacaoPortalService;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.RetornoInutilizacao;


public class InutilizacaoServiceTest extends DominioTest{
	
	@InjectMocks
	private InutilizacaoService inutilizacaoService;
	
	@Mock
	private InutilizacaoPortalService inutilizacaoPortalService;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		
		MockitoAnnotations.initMocks(this);
		
	}	
	
	@Test
	public void inutilizacao_homologada_em_homologacao(){
		Date dataHomologacao = new Date();
		
		when(inutilizacaoPortalService.inutilizar(any(SolicitacaoInutilizacao.class)))
			.thenReturn(new RetornoInutilizacao(
					new NumeroProtocolo("1111"), 
					new Mensagem(102, "Inutilização Homologada"), 
					dataHomologacao));
		
		SolicitacaoInutilizacao solicitacao = new SolicitacaoInutilizacao(
				new SolicitacaoInutilizacaoId("1"), 
				Ambiente.HOMOLOGACAO, 
				new Faixa(1,1));
		
		inutilizacaoService.inutilizar(solicitacao);			
		eventosEsperados(1);
		eventoEsperado(InutilizacaoHomologada.class);
		assertEquals(Ambiente.HOMOLOGACAO, solicitacao.ambiente());
		assertEquals(new NumeroProtocolo("1111"), solicitacao.numeroProtocolo());
		assertEquals(new Mensagem(102, "Inutilização Homologada"), solicitacao.retorno());
		assertEquals(dataHomologacao, solicitacao.dataHoraProcessamento());
		assertTrue(solicitacao.bemSucedida());
	}	

	@Test
	public void inutilizacao_nao_homologada_em_homologacao(){
		Date dataProcessamento = new Date();
		
		when(inutilizacaoPortalService.inutilizar(any(SolicitacaoInutilizacao.class)))
		.thenReturn(new RetornoInutilizacao(
				null, 
				new Mensagem(453, "Rejeição: Ano de inutilização não pode ser superior ao Ano atual"), 
				dataProcessamento));
		
		SolicitacaoInutilizacao solicitacao = new SolicitacaoInutilizacao(
				new SolicitacaoInutilizacaoId("1"), 
				Ambiente.HOMOLOGACAO, 
				new Faixa(1,1));
		
		inutilizacaoService.inutilizar(solicitacao);		
		eventosEsperados(0);	
		assertEquals(Ambiente.HOMOLOGACAO, solicitacao.ambiente());
		assertNull(solicitacao.numeroProtocolo());
		assertEquals(new Mensagem(453, "Rejeição: Ano de inutilização não pode ser superior ao Ano atual"), 
				solicitacao.retorno());
		assertEquals(dataProcessamento, solicitacao.dataHoraProcessamento());
		assertFalse(solicitacao.bemSucedida());
	}	

	@Test
	public void inutilizacao_homologada_em_producao(){
		Date dataHomologacao = new Date();
		when(inutilizacaoPortalService.inutilizar(any(SolicitacaoInutilizacao.class)))
			.thenReturn(new RetornoInutilizacao(
					new NumeroProtocolo("1111"), 
					new Mensagem(102, "Inutilização Homologada"), 
					dataHomologacao));
		
		SolicitacaoInutilizacao solicitacao = new SolicitacaoInutilizacao(
				new SolicitacaoInutilizacaoId("1"), 
				Ambiente.PRODUCAO, 
				new Faixa(1,1));
		inutilizacaoService.inutilizar(solicitacao);			
		eventosEsperados(1);
		eventoEsperado(InutilizacaoHomologada.class);
		assertEquals(Ambiente.PRODUCAO, solicitacao.ambiente());
		assertEquals(new NumeroProtocolo("1111"), solicitacao.numeroProtocolo());
		assertEquals(new Mensagem(102, "Inutilização Homologada"), solicitacao.retorno());
		assertEquals(dataHomologacao, solicitacao.dataHoraProcessamento());
		assertTrue(solicitacao.bemSucedida());
	}	

	@Test
	public void inutilizacao_nao_homologada_em_producao(){
		Date dataProcessamento = new Date();
		
		when(inutilizacaoPortalService.inutilizar(any(SolicitacaoInutilizacao.class)))
		.thenReturn(new RetornoInutilizacao(
				null, 
				new Mensagem(453, "Rejeição: Ano de inutilização não pode ser superior ao Ano atual"), 
				dataProcessamento));
		
		SolicitacaoInutilizacao solicitacao = new SolicitacaoInutilizacao(
				new SolicitacaoInutilizacaoId("1"), 
				Ambiente.PRODUCAO, 
				new Faixa(1,1));
		
		inutilizacaoService.inutilizar(solicitacao);
		
		eventosEsperados(0);	
		assertEquals(Ambiente.PRODUCAO, solicitacao.ambiente());
		assertNull(solicitacao.numeroProtocolo());
		assertEquals(new Mensagem(453, "Rejeição: Ano de inutilização não pode ser superior ao Ano atual"), 
				solicitacao.retorno());
		assertEquals(dataProcessamento, solicitacao.dataHoraProcessamento());
		assertFalse(solicitacao.bemSucedida());
	}	
}
