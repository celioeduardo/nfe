package com.hadrion.nfe.dominio.modelo.notista;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
@Access(AccessType.FIELD)
public class NotistaId {
	
	@Column(name="NOTISTA_ID")
	private String id;

	public NotistaId(String id){
		this.id = id;
	}
	
	public String id(){
		return this.id;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			NotistaId objetoTipado = (NotistaId) objeto;
			objetosIguais = this.id().equals(objetoTipado.id());
		} 

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (3215 * 53) 
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
	private NotistaId(){}
	
}
