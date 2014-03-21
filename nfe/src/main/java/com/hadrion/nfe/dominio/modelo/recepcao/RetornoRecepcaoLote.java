package com.hadrion.nfe.dominio.modelo.recepcao;

import com.hadrion.nfe.dominio.modelo.Mensagem;

public class RetornoRecepcaoLote {
	
	private ReciboLote recibo;
	private Mensagem erro;
	
	public RetornoRecepcaoLote(ReciboLote recibo){
		this.setRecibo(recibo);
	}
	
	public RetornoRecepcaoLote(Mensagem erro){
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
