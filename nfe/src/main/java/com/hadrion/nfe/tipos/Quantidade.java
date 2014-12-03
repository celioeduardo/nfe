package com.hadrion.nfe.tipos;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Quantidade {
	
	@Column(name="QUANTIDADE")
	private Double quantidade;

	public Quantidade(Double quantidade) {
		super();
		this.quantidade = quantidade;
	}

	public Double quantidade() {
		return quantidade;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Quantidade objetoTipado = (Quantidade) objeto;
			objetosIguais = new EqualsBuilder()
				.append(quantidade(),objetoTipado.quantidade())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(9967,33)
			.append(quantidade())
			.toHashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(quantidade());
	} 
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Quantidade(){}
	
	
}
