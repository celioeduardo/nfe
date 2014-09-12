package com.hadrion.nfe.dominio.modelo.portal;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class ChaveAcesso {
	private String chave;

	public ChaveAcesso(String chave) {
		super();
		this.chave = chave;
	}
	
	public String chave(){
		return chave;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			ChaveAcesso objetoTipado = (ChaveAcesso) objeto;
			objetosIguais = this.chave().equals(objetoTipado.chave());
		} 

		return objetosIguais;
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(1723,5155)
		.append(chave)
		.toHashCode();
	}

	@Override
	public String toString() {
		return chave; 	
	}

	public int codigo() {
		return Integer.parseInt(chave.substring(0, 1));
	}
	
}
