package com.hadrion.nfe.dominio.modelo.endereco;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Cep {
	private Long numero;

	public Cep(Long numero) {
		super();
		this.numero = numero;
	}
	public Cep(int numero) {
		super();
		this.numero = (long)numero;
	}
	public Long numero(){
		return numero;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Cep objetoTipado = (Cep) objeto;
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
		return "Cep [numero="+ numero
				+ "]";
	} 
}
