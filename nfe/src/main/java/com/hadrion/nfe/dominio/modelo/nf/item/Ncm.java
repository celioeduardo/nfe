package com.hadrion.nfe.dominio.modelo.nf.item;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Ncm {
	
	@Column(name="NCM")
	private Long numero;

	public Ncm(Long numero) {
		super();
		if (numero.equals(0L))
			throw new IllegalArgumentException("N.C.M. não pode ser zero.");
		
		this.numero = numero;
	}
	
	public Ncm(String numero) {
		this(Long.parseLong(numero));
	}

	public Long numero(){
		return numero;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Ncm objetoTipado = (Ncm) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero(),objetoTipado.numero())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(7681,113)
			.append(numero())
			.toHashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(numero());
	} 
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Ncm(){}
	

}
