package com.hadrion.nfe.dominio.modelo.nf.item;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ExportacaoItem {
	
	private Long numeroDrawBack;
	private ExportacaoIndireta exportacaoIndireta;
	
	public ExportacaoItem(Long numeroDrawBack, ExportacaoIndireta exportacaoIndireta) {
		super();
		this.numeroDrawBack = numeroDrawBack;
		this.exportacaoIndireta = exportacaoIndireta;
	}

	public Long numeroDrawBack() {
		return numeroDrawBack;
	}

	public ExportacaoIndireta exportacaoIndireta() {
		return exportacaoIndireta;
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			ExportacaoItem objetoTipado = (ExportacaoItem) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numeroDrawBack(),objetoTipado.numeroDrawBack())
				.append(exportacaoIndireta(),objetoTipado.exportacaoIndireta())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(12447,697)
			.append(numeroDrawBack())
			.append(exportacaoIndireta())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Exportacao [numeroDrawBack=" + numeroDrawBack()
				+ ",exportacaoIndireta="+ exportacaoIndireta()
				+ "]";
	} 
	
}

