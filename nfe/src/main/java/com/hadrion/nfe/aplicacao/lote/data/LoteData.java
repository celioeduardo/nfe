package com.hadrion.nfe.aplicacao.lote.data;

public class LoteData {
	
	private String loteId;
	private String numero; 
	private String numeroRecibo;
	private String ambiente;
	private String msgErroCodigo;
	private String msgErroDescricao;
	private String msgProcCodigo;
	private String msgProcDescricao;
	private String msgSefazCodigo;
	private String msgSefazDescricao;
	
	public LoteData(String loteId, 
			String numero,
			String numeroRecibo,
			String ambiente,
			String msgErroCodigo,
			String msgErroDescricao, String msgProcCodigo,
			String msgProcDescricao, String msgSefazCodigo,
			String msgSefazDescricao) {
		super();
		this.loteId = loteId;
		this.numero = numero;
		this.ambiente = ambiente;
		this.numeroRecibo = numeroRecibo;
		this.msgErroCodigo = msgErroCodigo;
		this.msgErroDescricao = msgErroDescricao;
		this.msgProcCodigo = msgProcCodigo;
		this.msgProcDescricao = msgProcDescricao;
		this.msgSefazCodigo = msgSefazCodigo;
		this.msgSefazDescricao = msgSefazDescricao;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getLoteId() {
		return loteId;
	}

	public void setLoteId(String loteId) {
		this.loteId = loteId;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumeroRecibo() {
		return numeroRecibo;
	}

	public void setNumeroRecibo(String numeroRecibo) {
		this.numeroRecibo = numeroRecibo;
	}

	public String getMsgErroCodigo() {
		return msgErroCodigo;
	}

	public void setMsgErroCodigo(String msgErroCodigo) {
		this.msgErroCodigo = msgErroCodigo;
	}

	public String getMsgErroDescricao() {
		return msgErroDescricao;
	}

	public void setMsgErroDescricao(String msgErroDescricao) {
		this.msgErroDescricao = msgErroDescricao;
	}

	public String getMsgProcCodigo() {
		return msgProcCodigo;
	}

	public void setMsgProcCodigo(String msgProcCodigo) {
		this.msgProcCodigo = msgProcCodigo;
	}

	public String getMsgProcDescricao() {
		return msgProcDescricao;
	}

	public void setMsgProcDescricao(String msgProcDescricao) {
		this.msgProcDescricao = msgProcDescricao;
	}

	public String getMsgSefazCodigo() {
		return msgSefazCodigo;
	}

	public void setMsgSefazCodigo(String msgSefazCodigo) {
		this.msgSefazCodigo = msgSefazCodigo;
	}

	public String getMsgSefazDescricao() {
		return msgSefazDescricao;
	}

	public void setMsgSefazDescricao(String msgSefazDescricao) {
		this.msgSefazDescricao = msgSefazDescricao;
	}
		
}
