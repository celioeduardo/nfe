package com.hadrion.nfe.dominio.modelo.lote;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;


public class GeracaoLoteServiceProducaoTest extends AbstractLoteServiceTest {
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
	}
	
	@Test
	public void gerar_lote_notas_pendentes(){
		
		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		notas.add(notaEmitidaProducaoPersistidaParaTest("1234"));
		notas.add(notaEmitidaProducaoPersistidaParaTest("1235"));		
		notas.add(notaEmitidaProducaoPersistidaParaTest("1236"));
		
		Lote lote = geracaoLoteService.gerarLoteEmProducao(notas);		
		assertEquals(3,lote.quantidadeNotas());
		assertEquals(lote.ambiente(),Ambiente.PRODUCAO);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_gera_lote_nota_autorizada(){
		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		notas.add(notaAutorizadaProducaoPersistidaParaTest("1237"));
		geracaoLoteService.gerarLoteEmProducao(notas);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_gera_lote_nota_cancelada(){
		geracaoLoteService.gerarLoteEmProducao(
				notaCanceladaProducaoPersistidaParaTest("1238"));		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_gera_lote_nota_inutilizada(){
		geracaoLoteService.gerarLoteEmProducao(
				notaInutilizadaProducaoPersistidaParaTest("1239"));		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_gera_lote_nota_denegada(){
		geracaoLoteService.gerarLoteEmProducao(
				notaDenegadaProducaoPersistidaParaTest("1240"));		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nota_ja_existe_em_lote_nao_enviado(){
		NotaFiscal nf = notaEmitidaProducaoPersistidaParaTest("1111");
		loteGeradoEmProducaoPersistidoParaTest(nf);
		geracaoLoteService.gerarLoteEmProducao(nf);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nota_ja_existe_em_lote_processando(){
		NotaFiscal nf = notaEmitidaProducaoPersistidaParaTest("1112");
		loteProcessadoEmProducaoPersistidoParaTest(nf);
		geracaoLoteService.gerarLoteEmProducao(nf);
	}
}
