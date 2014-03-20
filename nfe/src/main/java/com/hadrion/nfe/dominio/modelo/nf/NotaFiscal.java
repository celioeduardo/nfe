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
		return this.situacao == Situacao.EMITIDA;
	}
	
	public void emitida(){
		assertSituacaoIgual("Situação inválida: "+this.situacao,Situacao.INDEFINIDA);
		this.situacao=Situacao.EMITIDA;
	}
	public void autorizada() {
		assertSituacaoIgual("Situação inválida: "+this.situacao,
				Situacao.EMITIDA);
		this.situacao=Situacao.AUTORIZADA;
	}
	public void cancelada() {
		assertSituacaoIgual("Situação inválida: "+this.situacao,
				Situacao.AUTORIZADA);
		this.situacao=Situacao.CANCELADA;
	}
	public void inutilizada() {
		assertSituacaoIgual("Situação inválida: "+this.situacao,
				Situacao.EMITIDA);
		this.situacao=Situacao.INUTILIZADA;
	}
	public void denegada() {
		assertSituacaoIgual("Situação inválida: "+this.situacao,
				Situacao.EMITIDA);
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
