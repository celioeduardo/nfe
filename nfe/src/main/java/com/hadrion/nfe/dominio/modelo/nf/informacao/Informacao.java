package com.hadrion.nfe.dominio.modelo.nf.informacao;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Informacao {
	private String texto;
	private List<Observacao> observacoes;
	
	public Informacao(String texto, List<Observacao> observacoes) {
		super();
		this.texto = texto;
		this.observacoes = observacoes;
	}

	public String texto() {
		return texto;
	}

	public List<Observacao> observacoes() {
		return observacoes;
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Informacao objetoTipado = (Informacao) objeto;
			objetosIguais = new EqualsBuilder()
				.append(texto(), objetoTipado.texto())
				.append(observacoes(), objetoTipado.observacoes())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(477,37)
		.append(texto())
		.append(observacoes())
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Informacao [texto=" + texto()
				+ ",observacoes=" + observacoes()
				+ "]";	
	}
	
}
