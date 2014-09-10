package com.hadrion.nfe.port.adapters.xml;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculoSt;
import com.hadrion.nfe.dominio.modelo.icms.SubstituicaoTributaria;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Percentual;

public class SubstituicaoTributariaConvertida extends SubstituicaoTributaria {
	private Dinheiro baseCalculo;
	private Dinheiro valor;
	
	public SubstituicaoTributariaConvertida(
			Percentual percentualReducaoBaseCalculo, Dinheiro valorOperacao,
			Aliquota aliquota,
			DeterminacaoBaseCalculoSt determinacaoBaseCalculo,
			Percentual percentualMargemValorAdicionado,
			Dinheiro baseCalculo,
			Dinheiro valor) {
		super(percentualReducaoBaseCalculo, valorOperacao, aliquota,
				determinacaoBaseCalculo, percentualMargemValorAdicionado);
		this.baseCalculo = baseCalculo;
		this.valor = valor;
	}

	@Override
	public Dinheiro baseCalculo() {
		return baseCalculo;
	}
	
	@Override
	public Dinheiro valor() {
		return valor;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			SubstituicaoTributaria objetoTipado = (SubstituicaoTributaria) objeto;
			objetosIguais = new EqualsBuilder()
				.append(percentualReducaoBaseCalculo(), objetoTipado.percentualReducaoBaseCalculo())
				.append(valorOperacao(), objetoTipado.valorOperacao())
				.append(aliquota(), objetoTipado.aliquota())
				.append(determinacaoBaseCalculo(), objetoTipado.determinacaoBaseCalculo())
				.append(percentualMargemValorAdicionado(), objetoTipado.percentualMargemValorAdicionado())
				.append(baseCalculo(), objetoTipado.baseCalculo())
				.append(valor(), objetoTipado.valor())
				.isEquals();
		}

		return objetosIguais;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(6647,71)
		.append(percentualReducaoBaseCalculo())
		.append(valorOperacao())
		.append(aliquota())
		.append(determinacaoBaseCalculo())
		.append(percentualMargemValorAdicionado())			
		.append(baseCalculo())			
		.append(valor())			
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "SubstituicaoTributariaConvertida [" 
				+"percentualReducaoBaseCalculo=" + percentualReducaoBaseCalculo() 
				+",valorOperacao=" + valorOperacao() 
				+",aliquota=" + aliquota() 
				+",determinacaoBaseCalculo=" + determinacaoBaseCalculo() 
				+",percentualMargemValorAdicionado=" + percentualMargemValorAdicionado() 
				+",baseCalculo=" + baseCalculo() 
				+",valor=" + valor() 
				+ "]";
	}
}
