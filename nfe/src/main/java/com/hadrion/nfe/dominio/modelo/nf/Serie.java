package com.hadrion.nfe.dominio.modelo.nf;

public class Serie {

	private Long numero;

	public Serie(Long numero){
		if (numero==null)
			this.numero=0L;
		else
			this.numero=numero;
	}
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Serie objetoTipado = (Serie) objeto;
			objetosIguais = this.numero.equals(objetoTipado.numero);
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (123457 * 347) 
				+ this.numero.hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return "Serie [numero=" + numero + "]";
	}

	
}
