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
	private String tipoEmissao;
	private Date dataHoraAutorizacao;
	private String numeroProtocoloAutorizacao;
	private Date dataHoraCancelamento;
	private String numeroProtocoloCancelamento;
	
	public NotaFiscalData(){}
	
	public NotaFiscalData(String notaFiscalId,Long numero, String serie, 
			String chave,
			String tipoEmissao,
			Date emissao,
			Double valor, String tipoPublico,Long codigoPublico,
			String nomePublico,String tipo,
			Long msgCodigo, String msgDescricao,
			Date dataHoraAutorizacao,
			String numeroProtocoloAutorizacao,
			Date dataHoraCancelamento,
			String numeroProtocoloCancelamento) {
		super();
		this.notaFiscalId=notaFiscalId;
		this.numero = numero;
		this.serie = serie;
		this.chave = chave;
		this.tipoEmissao = tipoEmissao;
		this.emissao = emissao;
		this.valor = valor;
		this.publicoTipo=tipoPublico;
		this.publicoCodigo=codigoPublico;
		this.publicoNome=nomePublico;
		this.tipo=tipo;
		this.msgCodigo = msgCodigo;
		this.msgDescricao = msgDescricao;
		this.dataHoraAutorizacao = dataHoraAutorizacao;
		this.numeroProtocoloAutorizacao = numeroProtocoloAutorizacao;
		this.dataHoraCancelamento = dataHoraCancelamento;
		this.numeroProtocoloCancelamento = numeroProtocoloCancelamento;
	}
	public String getNumeroProtocoloAutorizacao() {
		return numeroProtocoloAutorizacao;
	}

	public String getNumeroProtocoloCancelamento() {
		return numeroProtocoloCancelamento;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Date getDataHoraAutorizacao() {
		return dataHoraAutorizacao;
	}

	public void setDataHoraAutorizacao(Date dataHoraAutorizacao) {
		this.dataHoraAutorizacao = dataHoraAutorizacao;
	}

	public Date getDataHoraCancelamento() {
		return dataHoraCancelamento;
	}

	public void setDataHoraCancelamento(Date dataHoraCancelamento) {
		this.dataHoraCancelamento = dataHoraCancelamento;
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

	public String getTipoEmissao() {
		return tipoEmissao;
	}

	public void setTipoEmissao(String tipoEmissao) {
		this.tipoEmissao = tipoEmissao;
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
