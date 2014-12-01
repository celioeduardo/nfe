package com.hadrion.nfe.dominio.modelo.lote;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalFixture;


public class GeracaoLoteServiceTest extends AbstractLoteServiceTest {
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void gerar_lote_nota_em_homologacao_que_esta_em_producao(){
		
		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		notas.add(notaEmitidaHomologacaoPersistidaParaTest("1111"));
		notas.add(notaEmitidaHomologacaoPersistidaParaTest("1112"));
		
		Lote loteEmHomologacao = geracaoLoteService.gerarLoteEmHomologacao(notas);
		loteRepositorio.salvar(loteEmHomologacao);
		assertEquals(2,loteEmHomologacao.quantidadeNotas());
		assertEquals(loteEmHomologacao.ambiente(),Ambiente.HOMOLOGACAO);
		
		geracaoLoteService.gerarLoteEmProducao(notas);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void gerar_lote_nota_em_producao_que_esta_em_homologacao(){
		
		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		notas.add(notaEmitidaProducaoPersistidaParaTest("1111"));
		notas.add(notaEmitidaProducaoPersistidaParaTest("1112"));
		
		Lote loteEmProducao = geracaoLoteService.gerarLoteEmProducao(notas);
		loteRepositorio.salvar(loteEmProducao);
		assertEquals(2,loteEmProducao.quantidadeNotas());
		assertEquals(loteEmProducao.ambiente(),Ambiente.PRODUCAO);
		
		geracaoLoteService.gerarLoteEmHomologacao(notas);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_gerar_lote_com_notas_de_locais_diferentes(){
		NotaFiscal nfNormal = NotaFiscalFixture.nfEmProducao();
		nfNormal.emitida();
		
		NotaFiscal nfContingencia = NotaFiscalFixture.nfEmProducao();
		nfContingencia.emitida();
		nfContingencia.alterarTipoEmissaoParaContingencia();
		
		geracaoLoteService.gerarLoteEmProducao(notas(nfNormal,nfContingencia));
		
	}
	
	private Set<NotaFiscal> notas(NotaFiscal ... notas) {
		Set<NotaFiscal> lista = new HashSet<NotaFiscal>(notas.length);
		for (NotaFiscal nota : notas) 
			lista.add(nota);
		return lista;
	}
	
}
