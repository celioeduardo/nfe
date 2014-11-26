package com.hadrion.nfe.dominio.modelo.lote;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;


public class GeracaoLoteServiceHomologacaoTest extends AbstractLoteServiceTest {
	
	@Before
	public void setup() throws Exception{
		super.setUp();
	}
	
	@Test
	public void gerar_lote_notas_pendentes(){
		
		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		notas.add(notaEmitidaHomologacaoPersistidaParaTest("1234"));
		notas.add(notaEmitidaHomologacaoPersistidaParaTest("1235"));		
		notas.add(notaEmitidaHomologacaoPersistidaParaTest("1236"));
		
		Lote lote = geracaoLoteService.gerarLoteEmHomologacao(notas);		
		assertEquals(3,lote.quantidadeNotas());
		assertEquals(lote.ambiente(),Ambiente.HOMOLOGACAO);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_gera_lote_nota_autorizada(){
		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		notas.add(notaAutorizadaHomologacaoPersistidaParaTest("1237"));
		geracaoLoteService.gerarLoteEmHomologacao(notas);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_gera_lote_nota_cancelada(){
		geracaoLoteService.gerarLoteEmHomologacao(
				notaCanceladaHomologacaoPersistidaParaTest("1238"));		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_gera_lote_nota_inutilizada(){
		geracaoLoteService.gerarLoteEmHomologacao(
				notaInutilizadaHomologacaoPersistidaParaTest("1239"));		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_gera_lote_nota_denegada(){
		geracaoLoteService.gerarLoteEmHomologacao(
				notaDenegadaHomologacaoPersistidaParaTest("1240"));		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nota_ja_existe_em_lote_nao_enviado(){
		NotaFiscal nf = notaEmitidaHomologacaoPersistidaParaTest("1111");
		loteGeradoEmHomologacaoPersistidoParaTest(nf);
		geracaoLoteService.gerarLoteEmHomologacao(nf);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nota_ja_existe_em_lote_processando(){
		NotaFiscal nf = notaEmitidaHomologacaoPersistidaParaTest("1112");
		loteProcessadoEmHomologacaoPersistidoParaTest(nf);
		geracaoLoteService.gerarLoteEmHomologacao(nf);
	}
	
}
