package com.hadrion.nfe.dominio.modelo.endereco;

import static org.apache.commons.lang.StringUtils.leftPad;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Cep {
	@Column(name="CEP")
	private String numero;

	public Cep(Long numero) {
		super();
		this.numero = leftPad(String.valueOf(numero), 8, "0");
	}
	public Cep(int numero) {
		super();
		this.numero = leftPad(String.valueOf(numero), 8, "0");
	}
	public String numero(){
		return leftPad(String.valueOf(numero), 8, "0");
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Cep objetoTipado = (Cep) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero, objetoTipado.numero)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(633,3147)
			.append(numero)
			.toHashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(numero);
	} 
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Cep(){}
}
