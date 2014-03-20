package com.hadrion.nfe.dominio.modelo.lote;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.MockLoteRepositorio;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.MockNotaFiscalRepositorio;


public class LoteServiceTest {
	LoteService loteService;
	LoteRepositorio loteRepositorio;
	NotaFiscalRepositorio notaFiscalRepositorio;
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
		
		loteService = new LoteService(
				loteRepositorio,
				notaFiscalRepositorio);
	}
	
	@Test
	public void gerarLoteNotasPendentes(){
		
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1234"));
		notas.add(new NotaFiscalId("1235"));
		notas.add(new NotaFiscalId("1236"));
		
		Lote lote = loteService.gerarLote(notas);		
		assertEquals(3,lote.quantidadeNotas());
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoGeraLoteNotaAutorizada(){
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1237"));
		loteService.gerarLote(notas);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoGeraLoteNotaCancelada(){
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1238"));
		loteService.gerarLote(notas);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoGeraLoteNotaInutilizada(){
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1239"));
		loteService.gerarLote(notas);		
	}
	@Test(expected=IllegalArgumentException.class)
	public void naoGeraLoteNotaDenegada(){
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1240"));
		loteService.gerarLote(notas);		
	}
	
	private Set<NotaFiscal> fixtureNotasPendentesDeTransmissao(){
		Set<NotaFiscal> result = new HashSet<NotaFiscal>();
		
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId("1234"));
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
}
