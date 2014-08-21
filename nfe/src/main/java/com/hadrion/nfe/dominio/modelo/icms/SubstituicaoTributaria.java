package com.hadrion.nfe.dominio.modelo.icms;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.tipos.Dinheiro;

public class SubstituicaoTributaria {
	
	public static final SubstituicaoTributaria NULA = new SubstituicaoTributaria(
			0.0,Dinheiro.ZERO,0.0,Dinheiro.ZERO, null, 0.0);
	
	private Double percentualReducaoBaseCalculo;
	private Dinheiro baseCalculo;
	private Double aliquota;
	private Dinheiro valor;
	private DeterminacaoBaseCalculoSt determinacaoBaseCalculo;
	private Double percentualMargemValorAdicionado;
	
	public SubstituicaoTributaria(Double percentualReducaoBaseCalculo,
			Dinheiro baseCalculo, Double aliquota, Dinheiro valor,
			DeterminacaoBaseCalculoSt determinacaoBaseCalculo,
			Double percentualMargemValorAdicionado) {
		this.percentualReducaoBaseCalculo = percentualReducaoBaseCalculo;
		this.baseCalculo = baseCalculo;
		this.aliquota = aliquota;
		this.valor = valor;
		this.determinacaoBaseCalculo = determinacaoBaseCalculo;
		this.percentualMargemValorAdicionado = percentualMargemValorAdicionado;
	}
	
	public Double percentualReducaoBaseCalculo() {
		return percentualReducaoBaseCalculo;
	}
	
	public Dinheiro baseCalculo() {
		return baseCalculo;
	}

	public Double aliquota() {
		return aliquota;
	}

	public Dinheiro valor() {
		return valor;
	}
	
	public DeterminacaoBaseCalculoSt determinacaoBaseCalculo() {
		return determinacaoBaseCalculo;
	}

	public Double percentualMargemValorAdicionado() {
		return percentualMargemValorAdicionado;
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			SubstituicaoTributaria objetoTipado = (SubstituicaoTributaria) objeto;
			objetosIguais = new EqualsBuilder()
				.append(percentualReducaoBaseCalculo, objetoTipado.percentualReducaoBaseCalculo)
				.append(baseCalculo, objetoTipado.baseCalculo)
				.append(aliquota, objetoTipado.aliquota)
				.append(valor, objetoTipado.valor)
				.append(determinacaoBaseCalculo, objetoTipado.determinacaoBaseCalculo)
				.append(percentualMargemValorAdicionado, objetoTipado.percentualMargemValorAdicionado)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(6645,71)
		.append(percentualReducaoBaseCalculo)
		.append(baseCalculo)
		.append(aliquota)
		.append(valor)
		.append(determinacaoBaseCalculo)
		.append(percentualMargemValorAdicionado)			
		.toHashCode();
	}
	
	@Override
	public String toString() {
		//TODO - complementar toString
		return "SubstituicaoTributaria [" 
				+"numero=" + percentualReducaoBaseCalculo 
				+ "]";
	}
	
	
}
