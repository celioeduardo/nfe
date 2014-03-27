package com.hadrion.nfe.dominio.modelo.nf;

public class NotaFiscal {
	private NotaFiscalId notaFiscalId;
	private Situacao situacaoHomologacao;
	private Situacao situacaoProducao;	
	
	public NotaFiscal(NotaFiscalId notaFiscalId) {
		this.notaFiscalId = notaFiscalId;
		this.situacaoHomologacao=Situacao.INDEFINIDA;
		this.situacaoProducao=Situacao.INDEFINIDA;
	}
	public NotaFiscalId notaFiscalId(){
		return this.notaFiscalId;
	}

	public boolean pendenteDeTransmissaoHomologacao(){
		return this.situacaoHomologacao == Situacao.EMITIDA;
	}
	public void emitidaHomologacao(){
		assertSituacaoIgualHomologacao("Situação inválida: "+this.situacaoHomologacao,Situacao.INDEFINIDA);
		this.situacaoHomologacao=Situacao.EMITIDA;
	}
	public void autorizadaHomologacao() {
		assertSituacaoIgualHomologacao("Situação inválida: "+this.situacaoHomologacao,
				Situacao.EMITIDA);
		this.situacaoHomologacao=Situacao.AUTORIZADA;
	}
	public void canceladaHomologacao() {
		assertSituacaoIgualHomologacao("Situação inválida: "+this.situacaoHomologacao,
				Situacao.AUTORIZADA);
		this.situacaoHomologacao=Situacao.CANCELADA;
	}
	public void inutilizadaHomologacao() {
		assertSituacaoIgualHomologacao("Situação inválida: "+this.situacaoHomologacao,
				Situacao.EMITIDA);
		this.situacaoHomologacao=Situacao.INUTILIZADA;
	}
	public void denegadaHomologacao() {
		assertSituacaoIgualHomologacao("Situação inválida: "+this.situacaoHomologacao,
				Situacao.EMITIDA);
		this.situacaoHomologacao=Situacao.INUTILIZADA;
	}
	
	private void assertSituacaoIgualHomologacao(String mensagem,Situacao... esperadas){
		for (Situacao esperada : esperadas) {
			if (esperada == this.situacaoHomologacao)
				return;
		}
		throw new UnsupportedOperationException(mensagem);
	}
	

	public boolean pendenteDeTransmissaoProducao(){
		return this.situacaoProducao == Situacao.EMITIDA;
	}
	public void emitidaProducao(){
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,Situacao.INDEFINIDA);
		this.situacaoProducao=Situacao.EMITIDA;
	}
	public void autorizadaProducao() {
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,
				Situacao.EMITIDA);
		this.situacaoProducao=Situacao.AUTORIZADA;
	}
	public void canceladaProducao() {
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,
				Situacao.AUTORIZADA);
		this.situacaoProducao=Situacao.CANCELADA;
	}
	public void inutilizadaProducao() {
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,
				Situacao.EMITIDA);
		this.situacaoProducao=Situacao.INUTILIZADA;
	}
	public void denegadaProducao() {
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,
				Situacao.EMITIDA);
		this.situacaoProducao=Situacao.INUTILIZADA;
	}
	
	private void assertSituacaoIgualProducao(String mensagem,Situacao... esperadas){
		for (Situacao esperada : esperadas) {
			if (esperada == this.situacaoProducao)
				return;
		}
		throw new UnsupportedOperationException(mensagem);
	}
	
	public boolean estaEmitidaEmHomologacao() {
		return this.situacaoHomologacao==Situacao.EMITIDA;
	}
	public boolean estaEmitidaEmProducao() {
		return this.situacaoProducao==Situacao.EMITIDA;
	}
	public boolean estaAutorizadaEmHomologacao() {
		return this.situacaoHomologacao==Situacao.AUTORIZADA;
	}
	public boolean estaAutorizadaEmProducao() {
		return this.situacaoProducao==Situacao.AUTORIZADA;
	}
	public boolean estaCanceladaEmHomologacao() {
		return this.situacaoHomologacao==Situacao.CANCELADA;
	}
	public boolean estaCanceladaEmProducao() {
		return this.situacaoProducao==Situacao.CANCELADA;
	}
}
