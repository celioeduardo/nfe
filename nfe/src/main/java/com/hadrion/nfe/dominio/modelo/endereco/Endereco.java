package com.hadrion.nfe.dominio.modelo.endereco;

public class Endereco {
	
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private Municipio municipio;
	private Pais pais;
	private Cep cep;
	
	public Endereco(String logradouro, String numero, String complemento,
			String bairro, Municipio municipio, Pais pais, Cep cep) {
		super();
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.municipio = municipio;
		this.pais = pais;
		this.cep = cep;
	}

	public String logradouro() {
		return logradouro;
	}

	public String numero() {
		return numero;
	}

	public String complemento() {
		return complemento;
	}

	public String bairro() {
		return bairro;
	}

	public Municipio municipio() {
		return municipio;
	}

	public Pais pais() {
		return pais;
	}

	public Cep cep() {
		return cep;
	}
	
	
	
}
