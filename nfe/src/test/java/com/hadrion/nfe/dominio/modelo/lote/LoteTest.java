package com.hadrion.nfe.dominio.modelo.lote;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.MockNotaFiscalRepositorio;


public class LoteTest {
	LoteService loteService;
	NotaFiscalRepositorio notaFiscalRepositorio;
	@Before
	public void setup(){
		
		notaFiscalRepositorio = new MockNotaFiscalRepositorio();
		
		for (NotaFiscal nf : fixtureNotasPendentesDeTransmissao()) 
			notaFiscalRepositorio.salvar(nf);
		
		notaFiscalRepositorio.salvar(fixtureNotaEmProcessamento());
		notaFiscalRepositorio.salvar(fixtureNotaAutorizada());
		notaFiscalRepositorio.salvar(fixtureNotaCancelada());
		notaFiscalRepositorio.salvar(fixtureNotaInutilizada());
		
		loteService = new LoteService(notaFiscalRepositorio);
	}
	
	@Test
	public void gerarLoteNotasPendentes(){
		
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1234"));
		notas.add(new NotaFiscalId("1235"));
		notas.add(new NotaFiscalId("1236"));
		
		Lote lote = loteService.gerarLote(notas);		
		assertEquals(3,lote.quantidade());
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoGeraLoteNotaEmProcessamento(){
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1237"));
		loteService.gerarLote(notas);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoGeraLoteNotaAutorizada(){
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1238"));
		loteService.gerarLote(notas);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoGeraLoteNotaCancelada(){
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1239"));
		loteService.gerarLote(notas);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoGeraLoteNotaInutilizada(){
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
		nf.erroTransmissao();
		result.add(nf);
		
		nf = new NotaFiscal(new NotaFiscalId("1236"));
		nf.emitida();
		nf.falhaConsistencia();
		result.add(nf);		
		
		return result;
	}
	
	private NotaFiscal fixtureNotaEmProcessamento(){		
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId("1237"));
		nf.emitida();
		nf.transmitida();
		return nf;		
	}

	private NotaFiscal fixtureNotaAutorizada(){		
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId("1238"));
		nf.emitida();
		nf.transmitida();
		nf.autorizada();
		return nf;		
	}
	
	private NotaFiscal fixtureNotaCancelada(){		
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId("1239"));
		nf.emitida();
		nf.transmitida();
		nf.autorizada();
		nf.cancelada();
		return nf;		
	}
	
	private NotaFiscal fixtureNotaInutilizada(){		
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId("1240"));
		nf.emitida();
		nf.inutilizada();
		return nf;		
	}
}
