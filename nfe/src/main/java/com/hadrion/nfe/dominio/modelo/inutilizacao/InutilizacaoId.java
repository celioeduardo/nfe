package com.hadrion.nfe.dominio.modelo.inutilizacao;

import javax.persistence.Column;

public class InutilizacaoId {
	@Column(name="INUTILIZACAO_ID")
	private String id; 
	
	public InutilizacaoId(String id){
		this.id=id;
	}
	
	public String id(){
		return this.id;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			InutilizacaoId objetoTipado = (InutilizacaoId) objeto;
			objetosIguais = this.id().equals(objetoTipado.id());
		} 

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (157 * 97) 
				+ this.id().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return id;
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private InutilizacaoId(){}
}
