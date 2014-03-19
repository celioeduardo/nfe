package com.hadrion.nfe.dominio.modelo.nf;

public class NotaFiscal {
	private NotaFiscalId notaFiscalId;
	private Situacao situacao;
	
	public NotaFiscal(NotaFiscalId notaFiscalId) {
		this.notaFiscalId = notaFiscalId;
		this.situacao=Situacao.INDEFINIDA;
	}
	public NotaFiscalId notaFiscalId(){
		return this.notaFiscalId;
	}
	
	public boolean pendenteDeTransmissao(){
		return this.situacao == Situacao.EMITIDA ||
				this.situacao == Situacao.FALHA_CONSISTENCIA ||
				this.situacao == Situacao.ERRO_TRANSMISSAO;
	}
	
	public void emitida(){
		assertSituacaoIgual("Situação inválida: "+this.situacao,Situacao.INDEFINIDA);
		this.situacao=Situacao.EMITIDA;
	}
	public void erroTransmissao(){
		assertSituacaoIgual("Situação inválida: "+this.situacao,
				Situacao.EMITIDA);
		this.situacao=Situacao.ERRO_TRANSMISSAO;
	}
	public void falhaConsistencia(){
		assertSituacaoIgual("Situação inválida: "+this.situacao,
				Situacao.EMITIDA, Situacao.EM_PROCESSAMENTO);
		this.situacao=Situacao.FALHA_CONSISTENCIA;
	}
	public void transmitida() {
		assertSituacaoIgual("Situação inválida: "+this.situacao,
				Situacao.EMITIDA, Situacao.FALHA_CONSISTENCIA, Situacao.ERRO_TRANSMISSAO);
		this.situacao=Situacao.EM_PROCESSAMENTO;
	}
	public void autorizada() {
		assertSituacaoIgual("Situação inválida: "+this.situacao,
				Situacao.EM_PROCESSAMENTO);
		this.situacao=Situacao.AUTORIZA;
	}
	public void cancelada() {
		assertSituacaoIgual("Situação inválida: "+this.situacao,
				Situacao.AUTORIZA);
		this.situacao=Situacao.CANCELADA;
	}
	public void inutilizada() {
		assertSituacaoIgual("Situação inválida: "+this.situacao,
				Situacao.EMITIDA, Situacao.FALHA_CONSISTENCIA);
		this.situacao=Situacao.INUTILIZADA;
	}
	private void assertSituacaoIgual(String mensagem,Situacao... esperadas){
		for (Situacao esperada : esperadas) {
			if (esperada == this.situacao)
				return;
		}
		throw new UnsupportedOperationException(mensagem);
	}
}
