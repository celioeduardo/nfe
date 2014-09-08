package com.hadrion.nfe.tipos;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Quantidade {
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

	
}
