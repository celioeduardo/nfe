package com.hadrion.nfe.dominio.modelo.nf.item;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;

public class ExportacaoIndireta {
	
	private Long numeroRegistro;
	private ChaveAcesso chaveAcesso;
	private Double quantidadeExportada;
	
	public ExportacaoIndireta(Long numeroRegistro, ChaveAcesso chaveAcesso, Double quantidadeExportada) {
		super();
		this.numeroRegistro = numeroRegistro;
		this.chaveAcesso = chaveAcesso;
		this.quantidadeExportada = quantidadeExportada;
	}

	public Long numeroRegistro() {
		return numeroRegistro;
	}

	public ChaveAcesso chaveAcesso() {
		return chaveAcesso;
	}
	
	public Double quantidadeExportada(){
		return quantidadeExportada;
	}
	
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			ExportacaoIndireta objetoTipado = (ExportacaoIndireta) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numeroRegistro(),objetoTipado.numeroRegistro())
				.append(chaveAcesso(),objetoTipado.chaveAcesso())
				.append(quantidadeExportada(),objetoTipado.quantidadeExportada)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(9967,17)
			.append(numeroRegistro())
			.append(chaveAcesso())
			.append(quantidadeExportada())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "ExportacaoIndireta [numeroRegistro=" + numeroRegistro()
				+ ",chaveAcesso="+ chaveAcesso()
				+ ",quantidadeExportada="+ quantidadeExportada()
				+ "]";
	} 
	
}
