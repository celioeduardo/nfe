package com.hadrion.nfe.dominio.modelo.lote;

import java.util.Set;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

public class GeracaoLoteService {
	
	private NotaFiscalRepositorio notaFiscalRepositorio;
	private LoteRepositorio loteRepositorio;
	
	public GeracaoLoteService(
			LoteRepositorio loteRepositorio,
			NotaFiscalRepositorio notaFiscalRepositorio){
		this.loteRepositorio=loteRepositorio;
		this.notaFiscalRepositorio=notaFiscalRepositorio;
	}
	
	public Lote gerarLoteEmHomologacao(Set<NotaFiscalId> notas){
		assertPreCondicoes(notas);		
		return Lote.gerarEmHomologacao(notas,loteRepositorio);
	}
	
	public Lote gerarLoteEmProducao(Set<NotaFiscalId> notas) {
		assertPreCondicoes(notas);
		return Lote.gerarEmProducao(notas, loteRepositorio);
	}
	
	private void assertPreCondicoes(Set<NotaFiscalId> notas){
		for (NotaFiscalId notaFiscalId : notas) {
			NotaFiscal nf = notaFiscalRepositorio.notaFiscalPeloId(notaFiscalId);
			assertNotaExiste(nf,notaFiscalId);
			assertNotaPendenteDeTransmissao(nf);
			assertNotaNaoEstaPendenteEmOutrosLotes(nf);
		}
	}
	
	private void assertNotaNaoEstaPendenteEmOutrosLotes(NotaFiscal nf){
		assertLotesNaoEstaoPendentes(
				loteRepositorio.lotesDaNota(nf.notaFiscalId()),nf);
	}
	
	private void assertNotaExiste(NotaFiscal nf, NotaFiscalId notaFiscalId){
		if (nf == null)
			throw new IllegalArgumentException(
					"Nota Fiscal "+notaFiscalId+
					" não encontrada.");
	}
	private void assertNotaPendenteDeTransmissao(NotaFiscal nf){
		if (!nf.pendenteDeTransmissao())
			throw new IllegalArgumentException(
					"Nota Fiscal "+nf.notaFiscalId()+
					" não está Pendente de Transmissão.");
	}
	
	private void assertLotesNaoEstaoPendentes(Set<Lote> lotes, NotaFiscal nf){
		for (Lote lote :lotes) 
			if (lote.estaNaoEnviado() || lote.estaEmProcessamento())
				throw new IllegalArgumentException(
						"Nota Fiscal "+nf.notaFiscalId()+
						" já está no Lote "+lote.numero());
	}
	
	
	
}
