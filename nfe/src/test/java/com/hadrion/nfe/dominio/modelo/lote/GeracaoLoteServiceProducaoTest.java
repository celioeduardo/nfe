package com.hadrion.nfe.dominio.modelo.lote;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.MockLoteRepositorio;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.MockNotaFiscalRepositorio;


public class GeracaoLoteServiceProducaoTest extends AbstractLoteServiceTest {
	private GeracaoLoteService loteService;
	
	@Before
	public void setup(){
		
		notaFiscalRepositorio = new MockNotaFiscalRepositorio();
		loteRepositorio = new MockLoteRepositorio();
		
		for (NotaFiscal nf : fixtureNotasPendentesDeTransmissao()) 
			notaFiscalRepositorio.salvar(nf);
		
		notaFiscalRepositorio.salvar(fixtureNotaAutorizada());
		notaFiscalRepositorio.salvar(fixtureNotaCancelada());
		notaFiscalRepositorio.salvar(fixtureNotaInutilizada());
		notaFiscalRepositorio.salvar(fixtureNotaDenegada());
		
		loteService = new GeracaoLoteService(
				loteRepositorio,
				notaFiscalRepositorio);
		
		loteRepositorio.salvar(fixtureLoteNaoEnviado());
		loteRepositorio.salvar(fixtureLoteProcessando());
	}
	
	@Test
	public void gerar_lote_notas_pendentes(){
		
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1234"));
		notas.add(new NotaFiscalId("1235"));
		notas.add(new NotaFiscalId("1236"));
		
		Lote lote = loteService.gerarLoteEmProducao(notas);		
		assertEquals(3,lote.quantidadeNotas());
		assertEquals(lote.ambiente(),Ambiente.PRODUCAO);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_gera_lote_nota_autorizada(){
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1237"));
		loteService.gerarLoteEmHomologacao(notas);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_gera_lote_nota_cancelada(){
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1238"));
		loteService.gerarLoteEmHomologacao(notas);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_gera_lote_nota_inutilizada(){
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1239"));
		loteService.gerarLoteEmHomologacao(notas);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nao_gera_lote_nota_denegada(){
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1240"));
		loteService.gerarLoteEmHomologacao(notas);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nota_ja_existe_em_lote_nao_enviado(){
		loteService.gerarLoteEmHomologacao(listaNotaFiscalId("1111"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nota_ja_existe_em_lote_processando(){
		loteService.gerarLoteEmHomologacao(listaNotaFiscalId("1112"));
	}
	
	private Set<NotaFiscal> fixtureNotasPendentesDeTransmissao(){
		Set<NotaFiscal> result = new HashSet<NotaFiscal>();
		
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId("1111"));
		nf.emitida();
		result.add(nf);
		
		nf = new NotaFiscal(new NotaFiscalId("1112"));
		nf.emitida();
		result.add(nf);
		
		nf = new NotaFiscal(new NotaFiscalId("1234"));
		nf.emitida();
		result.add(nf);
		
		nf = new NotaFiscal(new NotaFiscalId("1235"));
		nf.emitida();
		result.add(nf);
		
		nf = new NotaFiscal(new NotaFiscalId("1236"));
		nf.emitida();
		result.add(nf);		
		
		return result;
	}
	
	private NotaFiscal fixtureNotaAutorizada(){		
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId("1237"));
		nf.emitida();
		nf.autorizada();
		return nf;		
	}
	
	private NotaFiscal fixtureNotaCancelada(){		
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId("1238"));
		nf.emitida();
		nf.autorizada();
		nf.cancelada();
		return nf;		
	}
	
	private NotaFiscal fixtureNotaInutilizada(){		
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId("1239"));
		nf.emitida();
		nf.inutilizada();
		return nf;		
	}
	private NotaFiscal fixtureNotaDenegada(){		
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId("1240"));
		nf.emitida();
		nf.denegada();
		return nf;		
	}
	
	protected Lote fixtureLoteProcessando(){
		Lote lote = Lote.gerarEmHomologacao(listaNotaFiscalId("1112"), loteRepositorio);
		lote.recebido(new NumeroReciboLote(""));
		return lote;
	}
	
	protected Lote fixtureLoteNaoEnviado() {
		return Lote.gerarEmHomologacao(listaNotaFiscalId("1111"), loteRepositorio);
	}
}
