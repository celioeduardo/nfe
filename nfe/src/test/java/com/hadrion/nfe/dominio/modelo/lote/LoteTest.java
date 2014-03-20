package com.hadrion.nfe.dominio.modelo.lote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.MockLoteRepositorio;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.MockNotaFiscalRepositorio;


public class LoteTest {
	private LoteRepositorio loteRepositorio;
	private NotaFiscalRepositorio notaFiscalRepositorio;
	
	private NotaFiscalId notaFiscalId;
	private LoteId loteId;
	
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
		
	}
	
	@Test
	public void gerar_lote(){
		Set<NotaFiscalId> notas = new HashSet<NotaFiscalId>();
		notas.add(new NotaFiscalId("1234"));
		
		/*loteId = null;
		notaFiscalId = null;
		
		EventoDominioAssinante<InclusaoNotaSolicitada> assinante = 
				new EventoDominioAssinante<InclusaoNotaSolicitada>(){
			@Override
			public void tratarEvento(InclusaoNotaSolicitada eventoDominio) {
				loteId = eventoDominio.loteId();
				notaFiscalId = eventoDominio.notaFiscalId();
			}

			@Override
			public Class<InclusaoNotaSolicitada> inscritoParaTipoEvento() {
				return InclusaoNotaSolicitada.class;
			}
			
		};
		
		EventoDominioPublicador.instancia().assinar(assinante);*/
		
		Lote lote = Lote.gerar(notas,loteRepositorio);
		assertEquals(1,lote.quantidadeNotas());
		
	}
	
	@Test
	public void cancelar_lote(){
		Lote lote = Lote.gerar(listaNotaFiscalId("1234"), loteRepositorio);
		lote.cancelar();
	}
	
	@Test
	public void lote_transmitido(){
		Lote lote = Lote.gerar(listaNotaFiscalId("1234"), loteRepositorio);
		lote.transmitido();
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
	
	private Set<NotaFiscalId> listaNotaFiscalId(String... lista){
		Set<NotaFiscalId> result = new HashSet<NotaFiscalId>();
		for (String string : lista) {
			result.add(new NotaFiscalId(string));
		}
		return result;
	}
}
