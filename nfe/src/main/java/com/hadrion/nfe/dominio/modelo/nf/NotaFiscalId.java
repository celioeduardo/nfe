package com.hadrion.nfe.dominio.modelo.nf;

public class NotaFiscalId {

	private String id;

	public NotaFiscalId(String id) {
		this.id = id;
	}

	public String id() {
		return this.id;
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			NotaFiscalId objetoTipado = (NotaFiscalId) objeto;
			objetosIguais = this.id().equals(objetoTipado.id());
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (2785 * 5) 
				+ this.id().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return id;
	}

}
