package com.hadrion.nfe.tipos;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Cnpj {
	
	private Long numero;

	public Cnpj(Long numero) {
		super();
		this.numero = numero;
	}
	
	public Long numero(){
		return numero;
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
		return "Cnpj [numero="+ numero
				+ "]";
	} 
}
