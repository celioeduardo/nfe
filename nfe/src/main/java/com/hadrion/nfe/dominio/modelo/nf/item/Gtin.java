package com.hadrion.nfe.dominio.modelo.nf.item;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Gtin {
	private Long numero;

	public Gtin(Long numero) {
		super();
		if (numero.equals(0L))
			throw new IllegalArgumentException("Getin não pode ser zero.");		
		this.numero = numero;
	}
	
	public Gtin(String numero) {
		this(Long.parseLong(numero));
	}

	public Long numero(){
		return numero;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Gtin objetoTipado = (Gtin) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero(),objetoTipado.numero())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(2447,369)
			.append(numero())
			.toHashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(numero);
	} 

}
