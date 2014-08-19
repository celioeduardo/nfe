package com.hadrion.nfe.dominio.modelo.nf;

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
		int hashCodeValue = 
				+ (27851 * 57) 
				+ this.modelo().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return "Modelo [modelo=" + modelo + "]";
	}

}
