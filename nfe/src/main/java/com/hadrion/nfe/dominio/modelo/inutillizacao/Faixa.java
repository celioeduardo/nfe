package com.hadrion.nfe.dominio.modelo.inutillizacao;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class Faixa {
	private int inicio;
	private int fim;
	
	public Faixa(int numeracaoInicial, int numeracaoFinal) {
		setInicio(numeracaoInicial);
		setFim(numeracaoFinal);
		assertIntervalo();
	}
	
	private void setInicio(int numeracaoInicial) {
		this.inicio = numeracaoInicial;
	}

	private void assertIntervalo() {
		if ((this.fim < inicio) ||
				(this.fim <= 0)) 
			throw new IllegalArgumentException("Intervalo Inválido");	
	}

	private void setFim(int numeracaoFinal) {
		this.fim = numeracaoFinal;
	}

	public int inicio(){
		return this.inicio;
	}
	public int fim(){
		return this.fim;
	}
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Faixa objetoTipado = (Faixa) objeto;
			objetosIguais = new EqualsBuilder()
				.append(inicio(), objetoTipado.inicio())
				.append(fim(), objetoTipado.fim())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(71549,11)
			.append(inicio())
			.append(fim())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Faixa [inicio=" + inicio()
				+ ",fim=" + fim()
				+ "]";
	}
}