package com.hadrion.nfe.aplicacao.nf;

public class DefinirNotaComoRejeitadaComando {
	
	private String notaFiscalId;
	private int msgCodigo;
	private String msgDescricao;
	
	public DefinirNotaComoRejeitadaComando(String notaFiscalId, int msgCodigo,
			String msgDescricao) {
		super();
		this.notaFiscalId = notaFiscalId;
		this.msgCodigo = msgCodigo;
		this.msgDescricao = msgDescricao;
	}
	public DefinirNotaComoRejeitadaComando() {
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
