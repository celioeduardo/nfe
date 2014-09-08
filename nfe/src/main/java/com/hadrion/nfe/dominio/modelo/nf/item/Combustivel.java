package com.hadrion.nfe.dominio.modelo.nf.item;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.tipos.Quantidade;

public class Combustivel {
	private Long codAnp;
	private Quantidade quantidade;
	private Uf ufConsumo;
	private Cide cide;
	
	public Combustivel(Long codAnp, Quantidade quantidade, Uf ufConsumo,
			Cide cide) {
		super();
		this.codAnp = codAnp;
		this.quantidade = quantidade;
		this.ufConsumo = ufConsumo;
		this.cide = cide;
	}

	public Long codAnp() {
		return codAnp;
	}

	public Quantidade quantidade() {
		return quantidade;
	}

	public Uf ufConsumo() {
		return ufConsumo;
	}

	public Cide cide() {
		return cide;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Combustivel objetoTipado = (Combustivel) objeto;
			objetosIguais = new EqualsBuilder()
				.append(codAnp(),objetoTipado.codAnp())
				.append(quantidade(),objetoTipado.quantidade())
				.append(ufConsumo(),objetoTipado.ufConsumo())
				.append(cide(),objetoTipado.cide())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(5611,91)
			.append(codAnp())
			.append(quantidade())
			.append(ufConsumo())
			.append(cide())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Combustivel [codAnp=" + codAnp()
				+",quantidade = " + quantidade()
				+",ufConsumo = " + ufConsumo()
				+",cide = " + cide()
				+ "]";
	} 
	
}
