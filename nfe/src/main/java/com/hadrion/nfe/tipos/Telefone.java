package com.hadrion.nfe.tipos;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Telefone {
	
	@Column(name="TELEFONE")
	private String numero;

	public Telefone(String numero) {
		super();
		this.numero = numero;
	}
	
	public String numero(){
		return numero;
	}
	
	public String obterDigitos() {
		return numero.replaceAll("[^0-9]", "");
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Telefone objetoTipado = (Telefone) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero, objetoTipado.numero)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(633,3147)
			.append(numero)
			.toHashCode();
	}

	@Override
	public String toString() {
		return numero;
	} 
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Telefone(){}
}
