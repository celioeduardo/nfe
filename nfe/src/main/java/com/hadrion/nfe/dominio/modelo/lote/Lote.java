package com.hadrion.nfe.dominio.modelo.lote;

import java.util.HashSet;
import java.util.Set;

import com.hadrion.nfe.dominio.modelo.Mensagem;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class Lote {
	
	private LoteId loteId;
	private Set<LoteNotaFiscal> notas;
	private SituacaoLote situacao;
	private NumeroReciboLote numeroRecibo;
	private Mensagem mensagemErro;
	
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

	private void emProcessamento() {
		assertLoteNaoEnviado();
		situacao = SituacaoLote.EM_PROCESSAMENTO;
	}

	public boolean estaEmProcessamento() {
		return situacao == SituacaoLote.EM_PROCESSAMENTO;
	}

	public NumeroReciboLote numeroRecibo() {
		return this.numeroRecibo;
	}
	
	public void recebido(NumeroReciboLote numeroRecibo){
		this.numeroRecibo = numeroRecibo;
		emProcessamento();
	}
	
	public void inconsistente(Mensagem erro){
		this.mensagemErro = erro;
		this.falhaConsistencia();
	}
	
	private void falhaConsistencia(){
		assertLoteNaoEnviado();
		this.situacao = SituacaoLote.FALHA_CONSISTENCIA;
	}

	public boolean estaInconsistente() {
		return situacao == SituacaoLote.FALHA_CONSISTENCIA;
	}
	
	private void assertLoteNaoEnviado(){
		if (situacao != SituacaoLote.NAO_ENVIADO)
			throw new UnsupportedOperationException(
					"Situação do Lote é diferente de Não Enviado"); 
	}
	
	public Mensagem mensagemErro(){
		return mensagemErro;
	}
	
}
