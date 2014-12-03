package com.hadrion.nfe.dominio.modelo.nf;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Serie {

	@Column(name="SERIE")	
	private Long numero;

	public Serie(Long numero){
		if (numero==null)
			this.numero=0L;
		else
			this.numero=numero;
	}
	public Serie(int numero){
		this(new Long(numero));
	}
	public Long numero(){
		return numero;
	}
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Serie objetoTipado = (Serie) objeto;
			objetosIguais = this.numero.equals(objetoTipado.numero);
		}

		return objetosIguais;
	}
	
	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (33 * 947) 
				+ this.numero.hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return String.valueOf(numero);
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Serie(){} 
}
