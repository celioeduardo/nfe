package com.hadrion.nfe.dominio.modelo.hospede;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Hospede {

	@Column(name="HOSPEDE")
	private String id;

	public Hospede(String id){
		this.id = id;
	}
	
	public String id(){
		return this.id;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Hospede objetoTipado = (Hospede) objeto;
			objetosIguais = this.id().equals(objetoTipado.id());
		} 

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (215 * 23) 
				+ this.id().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return id;
	}
	
	@SuppressWarnings("unused")
	private Hospede(){}
}
