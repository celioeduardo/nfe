package com.hadrion.nfe.tipos;

import com.hadrion.nfe.dominio.modelo.nf.Serie;

public class InscricaoEstadual {
	private String numero;

	public InscricaoEstadual(String numero) {
		super();
		this.numero = numero;
	}
	
	public String numero(){
		return numero;
	}
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			InscricaoEstadual objetoTipado = (InscricaoEstadual) objeto;
			objetosIguais = this.numero.equals(objetoTipado.numero);
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (1234579 * 3473) 
				+ this.numero.hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return "InscricaoEstadual [numero=" + numero + "]";
	}	
}
