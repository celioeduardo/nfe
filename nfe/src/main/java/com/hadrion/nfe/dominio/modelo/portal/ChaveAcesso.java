package com.hadrion.nfe.dominio.modelo.portal;

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
		int hashCodeValue = 
				+ (1722 * 5) 
				+ chave().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return "ChaveAcesso [chave=" + chave() +"]";
	}
	
}
