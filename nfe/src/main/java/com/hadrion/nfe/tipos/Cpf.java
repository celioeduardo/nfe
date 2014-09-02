package com.hadrion.nfe.tipos;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class Cpf {
	
	private Long numero;

	public Cpf(Long numero) {
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
		return "Cpf [numero="+ numero
				+ "]";
	} 
}
