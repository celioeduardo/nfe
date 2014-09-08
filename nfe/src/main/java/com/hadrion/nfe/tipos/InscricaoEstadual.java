package com.hadrion.nfe.tipos;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class InscricaoEstadual {
	private String numero;

	public InscricaoEstadual(String numero) {
		super();
		this.numero = numero;
	}
	
	public String numero(){
		return numero;
	}
	public String obterSomenteDigitos() {
		return numero.replaceAll("[^0-9]", "");
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			InscricaoEstadual objetoTipado = (InscricaoEstadual) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero, objetoTipado.numero)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(133,197)
			.append(numero)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "InscricaoEstadual[numero="+ numero + "]";
	} 
}
