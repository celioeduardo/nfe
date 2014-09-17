package com.hadrion.nfe.dominio.modelo.portal.autorizacao;

import com.hadrion.nfe.dominio.modelo.portal.Mensagem;

public class RetornoAutorizacao {
	
	private ReciboLote recibo;
	private Mensagem erro;
	
	public RetornoAutorizacao(ReciboLote recibo){
		this.setRecibo(recibo);
	}
	
	public RetornoAutorizacao(Mensagem erro){
		this.setErro(erro);
	}
	
	public boolean sucesso(){
		return recibo != null && recibo.numero() != null;
	}
	
	public ReciboLote recibo(){
		return recibo;
	}
	
	public Mensagem erro(){
		return erro;
	}
	
	private void setRecibo(ReciboLote recibo){
		this.recibo = recibo;
	}
	
	private void setErro(Mensagem erro){
		this.erro = erro;
	}
	
}
