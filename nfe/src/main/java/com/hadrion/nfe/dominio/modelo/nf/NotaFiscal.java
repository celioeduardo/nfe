package com.hadrion.nfe.dominio.modelo.nf;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;

public class NotaFiscal {
	private NotaFiscalId notaFiscalId;
	private Situacao situacaoHomologacao;
	private Situacao situacaoProducao;	
	private String naturezaOperacao;
	private FormaPagamento formaPagamento;
	private Modelo modelo;
	private Serie serie;
	private Long numero;
	private Date emissao;
	private Date dataHora;
	private TipoOperacao tipoOperacao;
	private LocalDestino localDestino;
	private Uf municipioFatoGerador;
	private FormatoDanfe formatoDanfe;
	private boolean consumidorFinal;
	private Finalidade finalidade;
	private Presenca presenca;
	private Processo processo;
	
	public NotaFiscal(NotaFiscalId notaFiscalId,
			String naturezaOperacao,
			FormaPagamento formaPagamento,
			Modelo modelo,
			Serie serie,
			Long numero, 
			Date emissao, 
			Date dataHora, 
			TipoOperacao tipoOperacao,
			LocalDestino localDestino, 
			Uf municipioFatoGerador,
			boolean consumidorFinal,
			Finalidade finalidade,
			Presenca presenca,
			Processo processo) {
		this.notaFiscalId = notaFiscalId;
		this.situacaoHomologacao=Situacao.INDEFINIDA;
		this.situacaoProducao=Situacao.INDEFINIDA;
		this.naturezaOperacao=naturezaOperacao;
		this.formaPagamento=formaPagamento;
		this.modelo=modelo;
		this.serie=serie;
		this.numero=numero;
		this.emissao=emissao;
		this.dataHora=dataHora;
		this.tipoOperacao=tipoOperacao;
		this.localDestino=localDestino;
		this.municipioFatoGerador=municipioFatoGerador;
		//this.formatoDanfe=formatoDanfe;
		this.consumidorFinal=consumidorFinal;
		this.finalidade=finalidade;
		this.presenca=presenca;
		this.processo=processo;
	}
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
		assertSituacaoIgualHomologacao("Situação inválida: "+this.situacaoHomologacao,Situacao.EMITIDA);
		this.situacaoHomologacao=Situacao.AUTORIZADA;
	}
	public void canceladaHomologacao() {
		assertSituacaoIgualHomologacao("Situação inválida: "+this.situacaoHomologacao,Situacao.AUTORIZADA);
		this.situacaoHomologacao=Situacao.CANCELADA;
	}
	public void inutilizadaHomologacao() {
		assertSituacaoIgualHomologacao("Situação inválida: "+this.situacaoHomologacao,Situacao.EMITIDA);
		this.situacaoHomologacao=Situacao.INUTILIZADA;
	}
	public void denegadaHomologacao() {
		assertSituacaoIgualHomologacao("Situação inválida: "+this.situacaoHomologacao,Situacao.EMITIDA);
		this.situacaoHomologacao=Situacao.DENEGADA;
	}
	
	public boolean pendenteDeTransmissaoProducao(){
		return this.situacaoProducao == Situacao.EMITIDA;
	}
	public void emitidaProducao(){
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,Situacao.INDEFINIDA);
		this.situacaoProducao=Situacao.EMITIDA;
	}
	public void autorizadaProducao() {
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,Situacao.EMITIDA);
		this.situacaoProducao=Situacao.AUTORIZADA;
	}
	public void canceladaProducao() {
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,Situacao.AUTORIZADA);
		this.situacaoProducao=Situacao.CANCELADA;
	}
	public void inutilizadaProducao() {
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,Situacao.EMITIDA);
		this.situacaoProducao=Situacao.INUTILIZADA;
	}
	public void denegadaProducao() {
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,Situacao.EMITIDA);
		this.situacaoProducao=Situacao.DENEGADA;
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
	public String naturezaOperacao() {
		return naturezaOperacao;
	}
	public FormaPagamento formaPagamento() {
		return formaPagamento;
	}
	public Modelo modelo() {
		return modelo;
	}
	public Serie serie() {
		return serie;
	}
	public Long numero() {
		return numero;
	}
	public Date emissao() {
		return emissao;
	}
	public Date dataHora() {
		return dataHora;
	}
	public TipoOperacao tipoOperacao() {
		return tipoOperacao;
	}
	public LocalDestino localDestino() {
		return localDestino;
	}
	public Uf municipioFatoGerador() {
		return municipioFatoGerador;
	}
	private void assertSituacaoIgualHomologacao(String mensagem,Situacao... esperadas){
		for (Situacao esperada : esperadas) {
			if (esperada == this.situacaoHomologacao)
				return;
		}
		throw new UnsupportedOperationException(mensagem);
	}
	private void assertSituacaoIgualProducao(String mensagem,Situacao... esperadas){
		for (Situacao esperada : esperadas) {
			if (esperada == this.situacaoProducao)
				return;
		}
		throw new UnsupportedOperationException(mensagem);
	}
	public FormatoDanfe formatoDanfe() {
		return formatoDanfe;
	}
	public boolean consumidorFinal() {
		return consumidorFinal;
	}
	public Finalidade finalidade() {
		return finalidade;
	}
	public Presenca presenca() {
		return presenca;
	}
	public Processo processo() {
		return processo;
	}
}
