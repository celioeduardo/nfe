package com.hadrion.nfe.aplicacao.nf.data;

import java.util.Date;

public class NotaFiscalData {
	
	private Long numero;
	private String serie;
	private Date emissao;
	private Double valor;
	private String publicoTipo;
	private Long publicoCodigo;
	private String publicoNome;
	private String tipo;
	
	
	public NotaFiscalData(){}
	
	public NotaFiscalData(Long numero, String serie, Date emissao,
			Double valor, String tipoPublico,Long codigoPublico,
			String nomePublico,String tipo) {
		super();
		this.numero = numero;
		this.serie = serie;
		this.emissao = emissao;
		this.valor = valor;
		this.publicoTipo=tipoPublico;
		this.publicoCodigo=codigoPublico;
		this.publicoNome=nomePublico;
		this.tipo=tipo;
		
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

}
