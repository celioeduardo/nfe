package com.hadrion.nfe.dominio.modelo.endereco;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.tipos.Telefone;

@Embeddable
@Access(AccessType.FIELD)
public class Endereco {
	
	@Column(name="LOGRADOURO")
	private String logradouro;
	
	@Column(name="NUMERO")
	private String numero;
	
	@Column(name="COMPLEMENTO")
	private String complemento;
	
	@Column(name="BAIRRO")
	private String bairro;
	
	@Embedded
	private Municipio municipio;
	
	@Embedded
	private Pais pais;	
	
	@Embedded
	private Cep cep;
	
	@Embedded
	private Telefone telefone;
	
	public Endereco(String logradouro, String numero, String complemento,
			String bairro, Municipio municipio, Pais pais, Cep cep,
			Telefone telefone) {
		super();
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.municipio = municipio;
		this.pais = pais;
		this.cep = cep;
		this.telefone = telefone;
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
	
	public Telefone telefone(){
		return telefone;
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
				.append(telefone, objetoTipado.telefone)				
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
			.append(telefone)
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
				+ ",telefone="+telefone
				+ "]";
	} 
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Endereco(){}
}
