package com.hadrion.nfe.aplicacao.nf.data;

import java.util.Date;

public class NotaFiscalData {
	private String notaFiscalId;
	private Long numero;
	private String serie;
	private Date emissao;
	private Double valor;
	private String publicoTipo;
	private Long publicoCodigo;
	private String publicoNome;
	private String tipo;
	private Long msgCodigo;
	private String msgDescricao;
	private String chave;
	
	public NotaFiscalData(){}
	
	public NotaFiscalData(String notaFiscalId,Long numero, String serie, 
			String chave,
			Date emissao,
			Double valor, String tipoPublico,Long codigoPublico,
			String nomePublico,String tipo,
			Long msgCodigo, String msgDescricao) {
		super();
		this.notaFiscalId=notaFiscalId;
		this.numero = numero;
		this.serie = serie;
		this.chave = chave;
		this.emissao = emissao;
		this.valor = valor;
		this.publicoTipo=tipoPublico;
		this.publicoCodigo=codigoPublico;
		this.publicoNome=nomePublico;
		this.tipo=tipo;
		this.msgCodigo = msgCodigo;
		this.msgDescricao = msgDescricao;
	}
	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public NotaFiscalData(String notaFiscalId,Long numero, String serie, Date emissao,
			Double valor, String nomePublico,String tipo,
			Long msgCodigo, String msgDescricao) {
		super();
		this.notaFiscalId=notaFiscalId;
		this.numero = numero;
		this.serie = serie;
		this.emissao = emissao;
		this.valor = valor;
		this.publicoNome=nomePublico;
		this.tipo=tipo;
		this.msgCodigo = msgCodigo;
		this.msgDescricao = msgDescricao;
	}

	public Long getMsgCodigo() {
		return msgCodigo;
	}

	public void setMsgCodigo(Long msgCodigo) {
		this.msgCodigo = msgCodigo;
	}

	public String getMsgDescricao() {
		return msgDescricao;
	}

	public void setMsgDescricao(String msgDescricao) {
		this.msgDescricao = msgDescricao;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Date getEmissao() {
		return emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getPublicoTipo() {
		return publicoTipo;
	}

	public void setPublicoTipo(String tipoPublico) {
		this.publicoTipo = tipoPublico;
	}

	public Long getPublicoCodigo() {
		return publicoCodigo;
	}

	public void setPublicoCodigo(Long codigoPublico) {
		this.publicoCodigo = codigoPublico;
	}

	public String getPublicoNome() {
		return publicoNome;
	}

	public void setPublicoNome(String nomePublico) {
		this.publicoNome = nomePublico;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNotaFiscalId() {
		return notaFiscalId;
	}

	public void setNotaFiscalId(String id) {
		this.notaFiscalId = id;
	}

}
