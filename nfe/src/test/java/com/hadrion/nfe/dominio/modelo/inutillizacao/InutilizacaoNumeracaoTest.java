package com.hadrion.nfe.dominio.modelo.inutillizacao;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.InutilizacaoPortalService;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.RetornoInutilizacao;


public class InutilizacaoNumeracaoTest extends DominioTest{
	private InutilizacaoService inutilizacaoService;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
	}	
	@Test
	public void inutilizacao_homologada_em_homologacao(){
		inutilizacaoService = new InutilizacaoService(new InutilizacaoPortalService() {			
			@Override
			public RetornoInutilizacao inutilizar(SolicitacaoInutilizacao solicitacao) {
				return new RetornoInutilizacao(
						new NumeroProtocolo("1111"), 
						new Mensagem(102, "Inutilização Homologada"), 
						new Date());
			}
		});
		
		SolicitacaoInutilizacao solicitacaoInutilizacao = new SolicitacaoInutilizacao(1,1);
		inutilizacaoService.inutilizar(solicitacaoInutilizacao);			
		eventosEsperados(1);
		eventoEsperado(InutilizacaoHomologada.class);
		assertTrue("Solicitação de inutilização homologada.",solicitacaoInutilizacao.estaHomologada());
	}	

	@Test
	public void inutilizacao_nao_homologada_em_homologacao(){
		inutilizacaoService = new InutilizacaoService(new InutilizacaoPortalService() {			
			@Override
			public RetornoInutilizacao inutilizar(SolicitacaoInutilizacao solicitacao) {
				return new RetornoInutilizacao(
						null, 
						new Mensagem(453, "Rejeição: Ano de inutilização não pode ser superior ao Ano atual"), 
						new Date());
			}
		});
		
		SolicitacaoInutilizacao solicitacaoInutilizacao = new SolicitacaoInutilizacao(1,1);
		inutilizacaoService.inutilizar(solicitacaoInutilizacao);			
		eventosEsperados(0);		
		assertFalse("Solicitação de inutilização não homologada.",solicitacaoInutilizacao.estaHomologada());
	}	
}
