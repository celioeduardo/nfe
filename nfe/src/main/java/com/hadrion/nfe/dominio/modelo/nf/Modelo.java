package com.hadrion.nfe.dominio.modelo.nf;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Modelo {

	private String modelo;

	public Modelo(String modelo){
		this.modelo=modelo;
	}

	public String modelo() {
		return modelo;
	}
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Modelo objetoTipado = (Modelo) objeto;
			objetosIguais = this.modelo().equals(objetoTipado.modelo());
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(27851,57) 
				.append(modelo)
				.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Modelo [modelo=" + modelo + "]";
	}

}
