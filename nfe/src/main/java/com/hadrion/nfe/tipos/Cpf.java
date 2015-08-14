package com.hadrion.nfe.tipos;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Cpf {
	
	@Column(name="CPF")
	private Long numero;

	public Cpf(Long numero) {
		super();
		this.numero = numero;
	}
	
	public Long numero(){
		return numero;
	}
	/**
	 * Tamanho fixado em 11 posições
	 * preenchido com zeros à esquerda
	 * @return
	 */
	public String fixo(){
		return String.format("%011d", numero);
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Cpf objetoTipado = (Cpf) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero, objetoTipado.numero)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(6331,3697)
			.append(numero)
			.toHashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(numero);
	} 
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Cpf(){}
}
