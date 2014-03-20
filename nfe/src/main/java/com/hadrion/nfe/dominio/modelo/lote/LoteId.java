package com.hadrion.nfe.dominio.modelo.lote;

public class LoteId {
	
	private String id; 
	
	public LoteId(String id){
		this.id=id;
	}
	
	public String id(){
		return this.id;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			LoteId objetoTipado = (LoteId) objeto;
			objetosIguais = this.id().equals(objetoTipado.id());
		} 

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (7844 * 5) 
				+ this.id().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return "LoteId [id=" + id + "]";
	}
}
