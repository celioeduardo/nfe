package com.hadrion.nfe.tipos;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class InscricaoEstadual {
	public static final InscricaoEstadual ISENTO = new InscricaoEstadual("ISENTO");
	
	@Column(name="IE")
	private String numero;

	public InscricaoEstadual(String numero) {
		super();
		this.numero = numero;
	}
	
	public String numero(){
		return numero;
	}
	public String obterSomenteDigitos() {
		return numero.replaceAll("[^0-9]", "");
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			InscricaoEstadual objetoTipado = (InscricaoEstadual) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero, objetoTipado.numero)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(133,197)
			.append(numero)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "InscricaoEstadual[numero="+ numero + "]";
	}

	public boolean isento() {
		return ISENTO.equals(this);
	} 
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private InscricaoEstadual(){}
}
