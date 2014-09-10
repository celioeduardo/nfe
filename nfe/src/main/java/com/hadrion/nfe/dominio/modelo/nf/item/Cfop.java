package com.hadrion.nfe.dominio.modelo.nf.item;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Cfop {
	private Long numero;

	public Cfop(Long numero) {
		super();
		if (numero.equals(0L))
			throw new IllegalArgumentException("C.F.O.P. não pode ser zero.");
		this.numero = numero;
	}

	public Long numero() {
		return numero;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Cfop objetoTipado = (Cfop) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero(),objetoTipado.numero())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(3657,559)
			.append(numero())
			.toHashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(numero());
	} 

}
