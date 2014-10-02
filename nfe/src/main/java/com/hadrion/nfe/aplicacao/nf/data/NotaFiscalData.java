package com.hadrion.nfe.aplicacao.nf.data;

import java.util.Date;

public class NotaFiscalData {
	
	private Long numero;
	private String serie;
	private Date emissao;
	
	public NotaFiscalData(){}
	
	public NotaFiscalData(Long numero, String serie, Date emissao,
			Double valor) {
		super();
		this.numero = numero;
		this.serie = serie;
		this.emissao = emissao;
		this.valor = valor;
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
	private Double valor;
	
	

}
