package com.hadrion.nfe.dominio.modelo.inutilizacao;

import static com.hadrion.util.DataUtil.agora;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hadrion.comum.dominio.modelo.EventoDominioPublicador;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioRegistro;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;

public class InutilizacaoTest {
	
	@Mock
	private EventoDominioPublicador eventoDominioPublicador;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		DominioRegistro.setEventoDominioPublicador(eventoDominioPublicador);
	}
	
	@Test
	public void mockDominioRegistro(){
		assertNotNull(DominioRegistro.eventoDominioPublicador());
	}
	
	@Test
	public void novaInutilizacao(){
		Inutilizacao inutilizacao = new Inutilizacao(
				new InutilizacaoId("123456"),
				Ambiente.HOMOLOGACAO,
				new Serie(2),
				100,101,
				"Justificativa de inutilização",
				new FilialId("53-86675642000106"));
		
		assertEquals(new InutilizacaoId("123456"), inutilizacao.inutilizacaoId());
		assertEquals(new Serie(2), inutilizacao.serie());
		assertEquals(100, inutilizacao.numeroInicial());
		assertEquals(101, inutilizacao.numeroFinal());
		assertEquals("Justificativa de inutilização", inutilizacao.justificativa());
		assertEquals(new FilialId("53-86675642000106"), inutilizacao.filialId());
		assertFalse(inutilizacao.estaHomologada());
	}
	@Test
	public void falha(){
		Inutilizacao inutilizacao = InutilizacaoFixture.inutilizacao();
		
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
		
		inutilizacao.falhar(
				new Mensagem(201, "Rejeição: O numero máximo de numeração de NF-e a inutilizar ultrapassou o limite"),
				null,xmlRetorno);
		
		assertFalse(inutilizacao.estaHomologada());
		assertEquals(xmlRetorno, inutilizacao.xmlRetorno());
	}
	
	@Test
	public void autorizada(){
		Inutilizacao inutilizacao = InutilizacaoFixture.inutilizacao();
		
		Date dataHoraHomologacao = agora();
		String xmlRetorno = 
				"<retInutNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"2.00\">\n" + 
				"  <infInut>\n" + 
				"    <tpAmb>2</tpAmb>\n" + 
				"    <verAplic>SP_NFE_PL_006h</verAplic>\n" + 
				"    <cStat>102 </cStat>\n" + 
				"    <xMotivo>Inutilizacao de número homologado</xMotivo>\n" + 
				"    <cUF>31</cUF>\n" + 
				"  </infInut>\n" + 
				"</retInutNFe>\n";
		
		inutilizacao.homologar(
				dataHoraHomologacao,
				new NumeroProtocolo("INUT-1234"),
				new Mensagem(102, "Inutilizacao de número homologado"),
				null,
				xmlRetorno);
		
		verify(eventoDominioPublicador).publicar(any(InutilizacaoHomologada.class));
		
		assertEquals(dataHoraHomologacao, inutilizacao.dataHoraHomologacao());
		assertEquals(new NumeroProtocolo("INUT-1234"), inutilizacao.numeroProtocolo());
		assertEquals(xmlRetorno, inutilizacao.xmlRetorno());
		assertTrue(inutilizacao.estaHomologada());
	}
	
	@Test(expected=RuntimeException.class)
	public void numeroInicialNaoPodeSerMaiorQueNumeroFinal(){
		new Inutilizacao(
				new InutilizacaoId("123456"),
				Ambiente.HOMOLOGACAO,
				new Serie(2),
				101,100,
				"Justificativa de inutilização",
				new FilialId("53-86675642000106"));
	}
	
}
