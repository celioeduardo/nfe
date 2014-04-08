package com.hadrion.nfe.dominio.modelo.lote;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

@Service
public class GeracaoLoteService {
	
	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio;
	@Autowired
	private LoteRepositorio loteRepositorio;
	
	public Lote gerarLoteEmHomologacao(NotaFiscal nota){
		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		notas.add(nota);
		return gerarLoteEmHomologacao(notas);
	}
	public Lote gerarLoteEmHomologacao(Set<NotaFiscal> notas){
		assertPreCondicoes(notas, Ambiente.HOMOLOGACAO);		
		return Lote.gerarEmHomologacao(notas);
	}
	
	public Lote gerarLoteEmProducao(NotaFiscal nota) {
		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		notas.add(nota);
		return gerarLoteEmProducao(notas);
	}
	
	public Lote gerarLoteEmProducao(Set<NotaFiscal> notas) {
		assertPreCondicoes(notas, Ambiente.PRODUCAO);
		return Lote.gerarEmProducao(notas);
	}
	
	private void assertPreCondicoes(Set<NotaFiscal> notas, Ambiente ambiente){
		for (NotaFiscal nf : notas) 
			assertNotaNaoEstaPendenteEmOutrosLotes(nf, ambiente);
	}
	
	private void assertNotaNaoEstaPendenteEmOutrosLotes(NotaFiscal nf, 
			Ambiente ambiente){
		assertLotesNaoEstaoPendentes(
				loteRepositorio.lotesDaNota(nf.notaFiscalId()),nf,ambiente);
	}
	
	private void assertLotesNaoEstaoPendentes(
			Set<Lote> lotes, NotaFiscal nf, Ambiente ambiente){
		for (Lote lote :lotes) 
			if (lote.ambiente() == ambiente && 
				(lote.estaNaoEnviado() || lote.estaProcessando()))
				throw new IllegalArgumentException(
						"Nota Fiscal "+nf.notaFiscalId()+
						" já está no Lote "+lote.numero());
	}
	
	
	
}
