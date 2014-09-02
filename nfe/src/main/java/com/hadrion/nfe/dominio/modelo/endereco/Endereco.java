package com.hadrion.nfe.dominio.modelo.endereco;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
	
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Endereco objetoTipado = (Endereco) objeto;
			objetosIguais = new EqualsBuilder()
				.append(logradouro, objetoTipado.logradouro)
				.append(numero, objetoTipado.numero)
				.append(complemento, objetoTipado.complemento)
				.append(bairro, objetoTipado.bairro)
				.append(municipio, objetoTipado.municipio)
				.append(pais, objetoTipado.pais)
				.append(cep, objetoTipado.cep)				
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(131,193)
			.append(logradouro)
			.append(numero)
			.append(complemento)
			.append(bairro)
			.append(municipio)
			.append(pais)
			.append(cep)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Endereco [logradouro=" + logradouro
				+ ",numero="+ numero
				+ ",complemento="+ complemento
				+ ",bairro=" + bairro
				+ ",municipio="+ municipio
				+ ",pais="+pais
				+ ",cep="+cep
				+ "]";
	} 
	
}
