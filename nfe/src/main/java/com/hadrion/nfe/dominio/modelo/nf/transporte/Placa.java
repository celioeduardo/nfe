package com.hadrion.nfe.dominio.modelo.nf.transporte;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;

public class Placa {
	private Uf uf;
	private String numero;
	
	public Placa(Uf uf, String numero) {
		super();
		this.uf = uf;
		this.numero = numero;
	}
	public Uf uf() {
		return uf;
	}
	public String numero() {
		return numero;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Placa objetoTipado = (Placa) objeto;
			objetosIguais = new EqualsBuilder()
				.append(uf(),objetoTipado.uf())
				.append(numero(),objetoTipado.numero())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(7791,741)
			.append(uf())
			.append(numero())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Placa [uf=" + uf()
				+ ",numero="+ numero()
				+ "]";
	}	
	
}
