package com.hadrion.nfe.dominio.modelo.portal;


public class NumeroProtocolo {
	
	private String numero;

	public NumeroProtocolo(String numero) {
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
			NumeroProtocolo objetoTipado = (NumeroProtocolo) objeto;
			objetosIguais = this.numero().equals(objetoTipado.numero());
		} 

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (2735 * 3) 
				+ numero().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return numero();
	}

}
