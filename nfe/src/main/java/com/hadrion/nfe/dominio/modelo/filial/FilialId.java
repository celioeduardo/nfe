package com.hadrion.nfe.dominio.modelo.filial;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class FilialId {
	
	@Column(name="FILIAL_ID")
	private String id; 
	
	public FilialId(String id){
		this.id=id;
	}
	
	public String id(){
		return this.id;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			FilialId objetoTipado = (FilialId) objeto;
			objetosIguais = this.id().equals(objetoTipado.id());
		} 

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (5547 * 37) 
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
	private FilialId(){}
}
