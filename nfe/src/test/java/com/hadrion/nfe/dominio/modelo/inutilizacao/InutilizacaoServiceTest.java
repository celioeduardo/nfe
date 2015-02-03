package com.hadrion.nfe.dominio.modelo.inutilizacao;

import static com.hadrion.util.DataUtil.dataHora;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.InutilizacaoPortalService;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.RetornoInutilizacao;

public class InutilizacaoServiceTest {
	
	@InjectMocks
	private InutilizacaoService inutilizacaoService;
	
	@Mock
	private InutilizacaoRepositorio inutilizacaoRepositorio;
	
	@Mock
	private InutilizacaoPortalService inutilizacaoPortalService;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	public void inutilizacaoHomologada(){
		
		String xmlRetorno = 
				"<retInutNFe versao=\"2.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">\n" + 
				"  <infInut>\n" + 
				"    <tpAmb>2</tpAmb>\n" + 
				"    <verAplic>SP_NFE_PL_006q</verAplic>\n" + 
				"    <cStat>102</cStat>\n" + 
				"    <xMotivo>Inutilização de número homologado</xMotivo>\n" + 
				"    <cUF>35</cUF>\n" + 
				"    <ano>10</ano>\n" + 
				"    <CNPJ>10142785000190</CNPJ>\n" + 
				"    <mod>55</mod>\n" + 
				"    <serie>2</serie>\n" + 
				"    <nNFIni>1</nNFIni>\n" + 
				"    <nNFFin>1</nNFFin>\n" + 
				"    <dhRecbto>2014-12-01T15:01:03-03:00</dhRecbto>\n" + 
				"    <nProt>135130006498891</nProt>\n" + 
				"  </infInut>";
		
		when(inutilizacaoPortalService.inutilizar(any(Inutilizacao.class)))
			.thenReturn(new RetornoInutilizacao(
					new NumeroProtocolo("135130006498891"), 
					new Mensagem(102, "Inutilização Homologada"), 
					dataHora("01/12/2014 15:01:03", "GMT-03:00"),
					xmlRetorno));
		
		Inutilizacao inutilizacao = InutilizacaoFixture.inutilizacao();
		
		inutilizacaoService.inutilizar(inutilizacao);
		
		verify(inutilizacaoRepositorio, atLeastOnce()).salvar(any(Inutilizacao.class));
		
		assertTrue(inutilizacao.estaHomologada());
		assertEquals(new NumeroProtocolo("135130006498891"),inutilizacao.numeroProtocolo());
		assertEquals(dataHora("01/12/2014 15:01:03", "GMT-03:00"),inutilizacao.dataHoraHomologacao());
		assertEquals(xmlRetorno,inutilizacao.xmlRetorno());
	}
	
	@Test
	public void falha(){
		String xmlRetorno = 
				"<retInutNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"2.00\">\n" + 
				"  <infInut>\n" + 
				"    <tpAmb>2</tpAmb>\n" + 
				"    <verAplic>SP_NFE_PL_006h</verAplic>\n" + 
				"    <cStat>201</cStat>\n" + 
				"    <xMotivo>Rejeição: O numero máximo de numeração de NF-e a inutilizar ultrapassou o limite</xMotivo>\n" + 
				"    <cUF>31</cUF>\n" + 
				"  </infInut>\n" + 
				"</retInutNFe>\n";
		
		when(inutilizacaoPortalService.inutilizar(any(Inutilizacao.class)))
			.thenReturn(new RetornoInutilizacao(
					null, 
					new Mensagem(201, "Rejeição: O numero máximo de numeração de NF-e a inutilizar ultrapassou o limite"), 
					null,
					xmlRetorno));
		
		Inutilizacao inutilizacao = InutilizacaoFixture.inutilizacao();
		
		inutilizacaoService.inutilizar(inutilizacao);
		
		verify(inutilizacaoRepositorio, atLeastOnce()).salvar(any(Inutilizacao.class));
		assertFalse(inutilizacao.estaHomologada());
		assertNull(inutilizacao.numeroProtocolo());
		assertNull(inutilizacao.dataHoraHomologacao());
		assertEquals(xmlRetorno,inutilizacao.xmlRetorno());
	}
	
}
