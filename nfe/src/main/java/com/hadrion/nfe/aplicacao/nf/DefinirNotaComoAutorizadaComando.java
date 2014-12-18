package com.hadrion.nfe.aplicacao.nf;

public class DefinirNotaComoAutorizadaComando {
	
	private String notaFiscalId;
	private String numeroProtocolo;
	private int msgCodigo;
	private String msgDescricao;
	private String xmlProtocolo;
	
	public DefinirNotaComoAutorizadaComando(String notaFiscalId, String numeroProtocolo,
			int msgCodigo,String msgDescricao, String xmlProtocolo) {
		super();
		this.notaFiscalId = notaFiscalId;
		this.numeroProtocolo = numeroProtocolo;
		this.msgCodigo = msgCodigo;
		this.msgDescricao = msgDescricao;
		this.xmlProtocolo = xmlProtocolo;
	}
	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}
	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}
	public String getXmlProtocolo() {
		return xmlProtocolo;
	}
	public void setXmlProtocolo(String xml) {
		this.xmlProtocolo = xml;
	}
	public DefinirNotaComoAutorizadaComando() {
		super();
	}
	public String getNotaFiscalId() {
		return notaFiscalId;
	}
	public void setNotaFiscalId(String notaFiscalId) {
		this.notaFiscalId = notaFiscalId;
	}
	public int getMsgCodigo() {
		return msgCodigo;
	}
	public void setMsgCodigo(int msgCodigo) {
		this.msgCodigo = msgCodigo;
	}
	public String getMsgDescricao() {
		return msgDescricao;
	}
	public void setMsgDescricao(String msgDescricao) {
		this.msgDescricao = msgDescricao;
	}
	
	

}
