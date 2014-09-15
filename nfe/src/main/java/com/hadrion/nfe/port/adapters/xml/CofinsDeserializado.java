package com.hadrion.nfe.port.adapters.xml;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.cofins.Cofins;
import com.hadrion.nfe.dominio.modelo.cofins.CstCofins;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;

public class CofinsDeserializado extends Cofins{
	private Dinheiro valor;
	
	public CofinsDeserializado(CstCofins cst, Dinheiro baseCalculo, Aliquota aliquota,
			Double quantidade, Double aliquotaEmReais, Dinheiro valor) {
		super(cst, baseCalculo, aliquota, quantidade, aliquotaEmReais);
		this.valor = valor;
	}

	@Override
	public Dinheiro valor() {
		return valor;
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Cofins objetoTipado = (Cofins) objeto;
			objetosIguais = new EqualsBuilder()
				.append(cst(), objetoTipado.cst())
				.append(baseCalculo(), objetoTipado.baseCalculo())
				.append(aliquota(), objetoTipado.aliquota())
				.append(quantidade(), objetoTipado.quantidade())
				.append(aliquotaEmReais(), objetoTipado.aliquotaEmReais())
				.append(valor(), objetoTipado.valor())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(1171,111)
		.append(cst())
		.append(baseCalculo())
		.append(aliquota())
		.append(quantidade())
		.append(aliquotaEmReais())
		.append(valor())
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Cofins [cst=" + cst()
			+ ",baseCalculo=" + baseCalculo()
			+ ",aliquota=" + aliquota()
			+ ",quantidade=" + quantidade()
			+ ",aliquotaEmReais=" + aliquotaEmReais()
			+ ",valor=" + valor()
			+ "]";
	}		
}
