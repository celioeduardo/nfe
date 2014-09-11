package com.hadrion.nfe.dominio.modelo.nf.item.imposto;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.cofins.Cofins;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.pis.Pis;
import com.hadrion.nfe.tipos.Dinheiro;

public class Imposto {
	
	public static final Imposto NULO = new Imposto(
			Dinheiro.ZERO,Icms.NULO,Pis.NULO,Cofins.NULO);
	
	private Dinheiro valorTotalAproximado;
	private Icms icms;
	private Pis pis;
	private Cofins cofins;
	
	public Imposto(Dinheiro valorTotalAproximado, Icms icms, Pis pis,
			Cofins cofins) {
		super();
		this.valorTotalAproximado = valorTotalAproximado;
		this.icms = icms;
		this.pis = pis;
		this.cofins = cofins;
	}

	public Dinheiro valorTotalAproximado() {
		return valorTotalAproximado;
	}

	public Icms icms() {
		if (icms == null) 
			return Icms.NULO;
		return icms;
	}

	public Pis pis() {
		if (pis == null)
			return Pis.NULO;
		return pis;
	}

	public Cofins cofins() {
		if (cofins == null)
			return Cofins.NULO;
		return cofins;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Imposto objetoTipado = (Imposto) objeto;
			objetosIguais = new EqualsBuilder()
				.append(valorTotalAproximado(), objetoTipado.valorTotalAproximado())
				.append(pis(), objetoTipado.pis())
				.append(cofins(), objetoTipado.cofins())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(9811,11)
		.append(valorTotalAproximado())
		.append(pis())
		.append(cofins())
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Imposto [valorTotalAproximado=" + valorTotalAproximado() 
			+ ",pis=" + pis()
			+ ",cofins=" + cofins()
			+ "]";
	}
	
}
