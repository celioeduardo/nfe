package com.hadrion.nfe.tipos;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Email {
	
	@Column(name="EMAIL")
	private String email;

	public Email(String email) {
		super();
		this.email = email;
	}
	
	public String email(){
		return email;
	}
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Email objetoTipado = (Email) objeto;
			objetosIguais = new EqualsBuilder()
				.append(email, objetoTipado.email)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(127,9931)
			.append(email)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Email [email="+ email
				+ "]";
	} 
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Email(){}
}
