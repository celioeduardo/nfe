package com.hadrion.nfe.dominio.modelo.nf.importacao;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Adicao {

	private int numero;
	private int sequencia;
	private String codigoFabricante;

	public Adicao(int numero, int sequencia, String codigoFabricante) {
		this.numero=numero;
		this.sequencia=sequencia;
		this.codigoFabricante=codigoFabricante;
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Adicao objetoTipado = (Adicao) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero, objetoTipado.numero)
				.append(sequencia, objetoTipado.sequencia)
				.append(codigoFabricante, objetoTipado.codigoFabricante)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(132079,137)
		.append(numero)
		.append(sequencia)
		.append(codigoFabricante)
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Adicao [numero=" + numero
				+ ",sequencia=" + sequencia
				+ ",codigoFabricante=" + codigoFabricante + "]";	
	}

	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Adicao(){}
}
