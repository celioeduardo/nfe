package com.hadrion.nfe.port.adapters.xml;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.icms.Cst;
import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
import com.hadrion.nfe.dominio.modelo.icms.SubstituicaoTributaria;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Percentual;

class IcmsDeserializado extends Icms {
	
	private Dinheiro baseCalculo;
	private Dinheiro valor;
	
	public IcmsDeserializado(Origem origem, Cst cst,
			DeterminacaoBaseCalculo determinacaoBaseCalculo,
			Percentual percentualReducaoBaseCalculo, Dinheiro valorOperacao,
			Aliquota aliquota,
			Dinheiro valor,
			SubstituicaoTributaria substituicaoTributaria,
			Percentual percentualDiferimento,
			Dinheiro baseCalculo) {
		super(origem, cst, determinacaoBaseCalculo, percentualReducaoBaseCalculo,
				valorOperacao, aliquota, substituicaoTributaria,
				percentualDiferimento);
		this.baseCalculo = baseCalculo;
		this.valor = valor;
	}
	
	@Override
	public Dinheiro baseCalculo() {
		return this.baseCalculo;
	}

	@Override
	public Dinheiro valor() {
		return this.valor;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			IcmsDeserializado objetoTipado = (IcmsDeserializado) objeto;
			objetosIguais = new EqualsBuilder()
				.append(origem(), objetoTipado.origem())
				.append(cst(), objetoTipado.cst())
				.append(determinacaoBaseCalculo(), objetoTipado.determinacaoBaseCalculo())
				.append(percentualReducaoBaseCalculo(), objetoTipado.percentualReducaoBaseCalculo())
				.append(valorOperacao(), objetoTipado.valorOperacao())
				.append(aliquota(), objetoTipado.aliquota())
				.append(valor(), objetoTipado.valor())
				.append(st(), objetoTipado.st())
				.append(percentualDiferimento(), objetoTipado.percentualDiferimento())
				.append(baseCalculo(), objetoTipado.baseCalculo())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(4325,193)
		.append(origem())
		.append(cst())
		.append(determinacaoBaseCalculo())
		.append(percentualReducaoBaseCalculo())
		.append(valorOperacao())
		.append(aliquota())
		.append(valor())
		.append(st())
		.append(percentualDiferimento())
		.append(baseCalculo())
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "IcmsConvertido [origem=" + origem() 
			+ ",cst=" + cst()
			+ ",determinacaoBaseCalculo=" + determinacaoBaseCalculo()
			+ ",percentualReducaoBaseCalculo=" + percentualReducaoBaseCalculo() 
			+ ",valorOperacao=" + valorOperacao()
			+ ",aliquota=" + aliquota()
			+ ",valor=" + valor()
			+ ",substituicaoTributaria=" + st()
			+ ",percentualDiferimento=" + percentualDiferimento()
			+ ",baseCalculo=" + baseCalculo()
			+ "]";
	}
	
}
