package com.hadrion.nfe.dominio.modelo.nf.item;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Ncm {
	private Long numero;

	public Ncm(Long numero) {
		super();
		this.numero = numero;
	}
	
	public Ncm(String numero) {
		this(Long.parseLong(numero));
	}

	public Long numero(){
		return numero;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Ncm objetoTipado = (Ncm) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero(),objetoTipado.numero())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(7681,113)
			.append(numero())
			.toHashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(numero());
	} 

}
