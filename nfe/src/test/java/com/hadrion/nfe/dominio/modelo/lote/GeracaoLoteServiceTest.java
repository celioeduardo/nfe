package com.hadrion.nfe.dominio.modelo.lote;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;


public class GeracaoLoteServiceTest extends AbstractLoteServiceTest {
	
	@Before
	public void setup(){
		for (NotaFiscal nf : fixtureNotasPendentesDeTransmissao()) 
			notaFiscalRepositorio.salvar(nf);
	}
	
	@Test
	public void gerar_lote_nota_em_homologacao_que_esta_em_producao(){
		
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1111"));
		notas.add(new NotaFiscalId("1112"));
		
		Lote loteEmHomologacao = geracaoLoteService.gerarLoteEmHomologacao(notas);
		loteRepositorio.salvar(loteEmHomologacao);
		assertEquals(2,loteEmHomologacao.quantidadeNotas());
		assertEquals(loteEmHomologacao.ambiente(),Ambiente.HOMOLOGACAO);
		
		Lote loteEmProducao = geracaoLoteService.gerarLoteEmProducao(notas);
		loteRepositorio.salvar(loteEmProducao);
		assertEquals(2,loteEmProducao.quantidadeNotas());
		assertEquals(loteEmProducao.ambiente(),Ambiente.PRODUCAO);
		
		assertEquals(2,loteRepositorio.lotesDaNota(new NotaFiscalId("1111")).size());
		assertEquals(2,loteRepositorio.lotesDaNota(new NotaFiscalId("1112")).size());
		
	}
	
	@Test
	public void gerar_lote_nota_em_producao_que_esta_em_homologacao(){
		
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1111"));
		notas.add(new NotaFiscalId("1112"));
		
		Lote loteEmProducao = geracaoLoteService.gerarLoteEmProducao(notas);
		loteRepositorio.salvar(loteEmProducao);
		assertEquals(2,loteEmProducao.quantidadeNotas());
		assertEquals(loteEmProducao.ambiente(),Ambiente.PRODUCAO);
		
		Lote loteEmHomologacao = geracaoLoteService.gerarLoteEmHomologacao(notas);
		loteRepositorio.salvar(loteEmHomologacao);
		assertEquals(2,loteEmHomologacao.quantidadeNotas());
		assertEquals(loteEmHomologacao.ambiente(),Ambiente.HOMOLOGACAO);
		
		assertEquals(2,loteRepositorio.lotesDaNota(new NotaFiscalId("1111")).size());
		assertEquals(2,loteRepositorio.lotesDaNota(new NotaFiscalId("1112")).size());
		
	}
	
	private Set<NotaFiscal> fixtureNotasPendentesDeTransmissao(){
		Set<NotaFiscal> result = new HashSet<NotaFiscal>();
		
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId("1111"));
		nf.emitidaHomologacao();
		result.add(nf);
		
		nf = new NotaFiscal(new NotaFiscalId("1112"));
		nf.emitidaHomologacao();
		result.add(nf);
		
		return result;
	}
	
}
