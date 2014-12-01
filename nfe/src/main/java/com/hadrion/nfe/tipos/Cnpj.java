package com.hadrion.nfe.tipos;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Cnpj {
	
	@Column(name="CNPJ")
	private Long numero;

	public Cnpj(Long numero) {
		super();
		this.numero = numero;
	}
	public Cnpj(long numero) {
		super();
		this.numero = numero;
	}
	
	public Long numero(){
		return numero;
	}
	
	/**
	 * Tamanho fixado em 14 posições
	 * preenchido com zeros à esquerda
	 * @return
	 */
	public String fixo(){
		return String.format("%014d", numero);
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Cnpj objetoTipado = (Cnpj) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero, objetoTipado.numero)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(7731,36997)
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
	private Cnpj(){}
	
}
