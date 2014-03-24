package com.hadrion.nfe.dominio.modelo.lote;

import java.util.Set;

import com.hadrion.nfe.dominio.modelo.Ambiente;
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
		assertPreCondicoes(notas, Ambiente.HOMOLOGACAO);		
		return Lote.gerarEmHomologacao(notas,loteRepositorio);
	}
	
	public Lote gerarLoteEmProducao(Set<NotaFiscalId> notas) {
		assertPreCondicoes(notas, Ambiente.PRODUCAO);
		return Lote.gerarEmProducao(notas, loteRepositorio);
	}
	
	private void assertPreCondicoes(Set<NotaFiscalId> notas, Ambiente ambiente){
		for (NotaFiscalId notaFiscalId : notas) {
			NotaFiscal nf = notaFiscalRepositorio.notaFiscalPeloId(notaFiscalId);
			assertNotaExiste(nf,notaFiscalId);
			assertNotaPendenteDeTransmissao(nf);
			assertNotaNaoEstaPendenteEmOutrosLotes(nf, ambiente);
		}
	}
	
	private void assertNotaNaoEstaPendenteEmOutrosLotes(NotaFiscal nf, 
			Ambiente ambiente){
		assertLotesNaoEstaoPendentes(
				loteRepositorio.lotesDaNota(nf.notaFiscalId()),nf,ambiente);
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
	
	private void assertLotesNaoEstaoPendentes(
			Set<Lote> lotes, NotaFiscal nf, Ambiente ambiente){
		for (Lote lote :lotes) 
			if (lote.ambiente() == ambiente && 
				(lote.estaNaoEnviado() || lote.estaEmProcessamento()))
				throw new IllegalArgumentException(
						"Nota Fiscal "+nf.notaFiscalId()+
						" já está no Lote "+lote.numero());
	}
	
	
	
}
