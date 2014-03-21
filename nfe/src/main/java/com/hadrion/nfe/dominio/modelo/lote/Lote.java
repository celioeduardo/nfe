package com.hadrion.nfe.dominio.modelo.lote;

import java.util.HashSet;
import java.util.Set;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class Lote {
	
	private LoteId loteId;
	private Set<LoteNotaFiscal> notas;
	private SituacaoLote situacao;
	
	public int quantidadeNotas() {
		return notas.size();
	}

	protected Lote(){
		
	}

	public static Lote gerar(Set<NotaFiscalId> lista,
			LoteRepositorio loteRepositorio) {
		
		Lote lote = new Lote();
		lote.loteId = loteRepositorio.proximaIdentidade();
		lote.situacao = SituacaoLote.NAO_ENVIADO;
		lote.notas = new HashSet<LoteNotaFiscal>();
		
		for (NotaFiscalId notaFiscalId : lista){
			lote.notas.add(new LoteNotaFiscal(notaFiscalId));
//			EventoDominioPublicador.instancia().pulicar(
//				new InclusaoNotaSolicitada(lote.loteId, notaFiscalId));
		}
		
		return lote;
	}

	public LoteId loteId(){
		return loteId;
	}
	
	public void cancelar() {
		if (situacao != SituacaoLote.NAO_ENVIADO)
			throw new UnsupportedOperationException(
					"Não é permitido cancelar um lote com situação: "+situacao);
		situacao = SituacaoLote.CANCELADO;
	}
	public void transmitido() {
		if (situacao != SituacaoLote.NAO_ENVIADO)
			throw new UnsupportedOperationException(
					"Não é permitido cancelar um lote com situação: "+situacao);
		situacao = SituacaoLote.CANCELADO;
	}
	
	public boolean temNota(NotaFiscalId notaFiscalId){
		for (LoteNotaFiscal loteNotaFiscal: notas) {
			if (loteNotaFiscal.notaFiscalId().equals(notaFiscalId))
				return true;
		}
		return false;
	}

	public boolean estaNaoEnviado() {
		return situacao == SituacaoLote.NAO_ENVIADO;
	}

	public String numero() {
		// TODO criar numero do lote
		return loteId.id();
	}

	public void emProcessamento() {
		situacao = SituacaoLote.EM_PROCESSAMENTO;
	}

	public boolean estaEmProcessamento() {
		return situacao == SituacaoLote.EM_PROCESSAMENTO;
	}
	
}
