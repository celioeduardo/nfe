package com.hadrion.nfe.dominio.modelo.nf.transporte;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.nf.item.Cfop;
import com.hadrion.nfe.tipos.Dinheiro;

@Embeddable
@Access(AccessType.FIELD)
public class RetencaoIcms {
	@Embedded
	private Dinheiro valorServico;
	
	@Embedded
	private Dinheiro baseCalculo;
	
	@Column(name="ALIQUOTA")
	private Double aliquota;
	
	@Embedded
	private Dinheiro valor;
	
	@Embedded
	private Cfop cfop;
	
	public RetencaoIcms(Dinheiro valorServico, Dinheiro baseCalculo,
			Double aliquota, Dinheiro valor, Cfop cfop) {
		super();
		this.valorServico = valorServico;
		this.baseCalculo = baseCalculo;
		this.aliquota = aliquota;
		this.valor = valor;
		this.cfop = cfop;
	}

	public Dinheiro valorServico() {
		return valorServico;
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

	public Cfop cfop() {
		return cfop;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			RetencaoIcms objetoTipado = (RetencaoIcms) objeto;
			objetosIguais = new EqualsBuilder()
				.append(valorServico(),objetoTipado.valorServico())
				.append(baseCalculo(),objetoTipado.baseCalculo())
				.append(aliquota(),objetoTipado.aliquota())
				.append(valor(),objetoTipado.valor())
				.append(cfop(),objetoTipado.cfop())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(8899,731)
			.append(valorServico())
			.append(baseCalculo())
			.append(aliquota())
			.append(valor())
			.append(cfop())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "RetencaoIcms [valorServico=" + valorServico()
				+ ",baseCalculo="+ baseCalculo()
				+ ",aliquota="+ aliquota()
				+ ",valor="+ valor()
				+ ",cfop="+ cfop()
				+ "]";
	}	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private RetencaoIcms(){}
	
}
