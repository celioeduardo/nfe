package com.hadrion.nfe.tipos;


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
		return "Cpf [numero=" + numero + "]";
	}
}
