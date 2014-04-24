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
		
		when(inutilizacaoPortalService.inutilizar(any(SolicitacaoInutilizacao.class)))
			.thenReturn(new RetornoInutilizacao(
					new NumeroProtocolo("1111"), 
					new Mensagem(102, "Inutilização Homologada"), 
					new Date()));
		
		SolicitacaoInutilizacao solicitacaoInutilizacao = new SolicitacaoInutilizacao(
				new SolicitacaoInutilizacaoId("1"), 
				Ambiente.HOMOLOGACAO, 
				1, 
				1);
		inutilizacaoService.inutilizar(solicitacaoInutilizacao);			
		eventosEsperados(1);
		eventoEsperado(InutilizacaoHomologada.class);
	}	

	@Test
	public void inutilizacao_nao_homologada_em_homologacao(){
		
		when(inutilizacaoPortalService.inutilizar(any(SolicitacaoInutilizacao.class)))
		.thenReturn(new RetornoInutilizacao(
				null, 
				new Mensagem(453, "Rejeição: Ano de inutilização não pode ser superior ao Ano atual"), 
				new Date()));
		
		SolicitacaoInutilizacao solicitacaoInutilizacao = new SolicitacaoInutilizacao(
				new SolicitacaoInutilizacaoId("1"), 
				Ambiente.HOMOLOGACAO, 
				1, 
				1);
		inutilizacaoService.inutilizar(solicitacaoInutilizacao);		
		assertNotNull(solicitacaoInutilizacao.ambiente());
		eventosEsperados(0);		
	}	

	@Test
	public void inutilizacao_homologada_em_producao(){
		
		when(inutilizacaoPortalService.inutilizar(any(SolicitacaoInutilizacao.class)))
			.thenReturn(new RetornoInutilizacao(
					new NumeroProtocolo("1111"), 
					new Mensagem(102, "Inutilização Homologada"), 
					new Date()));
		
		SolicitacaoInutilizacao solicitacaoInutilizacao = new SolicitacaoInutilizacao(
				new SolicitacaoInutilizacaoId("1"), 
				Ambiente.PRODUCAO, 
				1, 
				1);
		inutilizacaoService.inutilizar(solicitacaoInutilizacao);			
		eventosEsperados(1);
		eventoEsperado(InutilizacaoHomologada.class);
	}	

	@Test
	public void inutilizacao_nao_homologada_em_producao(){
		
		when(inutilizacaoPortalService.inutilizar(any(SolicitacaoInutilizacao.class)))
		.thenReturn(new RetornoInutilizacao(
				null, 
				new Mensagem(453, "Rejeição: Ano de inutilização não pode ser superior ao Ano atual"), 
				new Date()));
		
		SolicitacaoInutilizacao solicitacaoInutilizacao = new SolicitacaoInutilizacao(
				new SolicitacaoInutilizacaoId("1"), 
				Ambiente.PRODUCAO, 
				1, 
				1);
		inutilizacaoService.inutilizar(solicitacaoInutilizacao);		
		assertNotNull(solicitacaoInutilizacao.ambiente());
		eventosEsperados(0);		
	}	
}
