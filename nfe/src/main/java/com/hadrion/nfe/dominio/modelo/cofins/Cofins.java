package com.hadrion.nfe.dominio.modelo.cofins;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;

@Embeddable
@Access(AccessType.FIELD)
public class Cofins {
	public static final Cofins NULO = new Cofins(null, Dinheiro.ZERO, Aliquota.ZERO, 0.0, 0.0);
	
	@Enumerated
	@Column(name="COFINS_CST")
	private CstCofins cst;
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="COFINS_BC"))
	private Dinheiro baseCalculo;

	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="COFINS_ALIQUOTA"))
	private Aliquota aliquota;
	
	@Column(name="COFINS_QUANTIDADE")
	private Double quantidade;
	
	@Column(name="COFINS_REAL")
	private Double aliquotaEmReais;
	
	public Cofins(CstCofins cst, Dinheiro baseCalculo, Aliquota aliquota, 
			Double quantidade, Double aliquotaEmReais) {
		super();
		this.cst = cst;
		this.baseCalculo = baseCalculo;
		this.aliquota = aliquota;
		this.quantidade = quantidade;
		this.aliquotaEmReais = aliquotaEmReais;
	}

	public CstCofins cst() {
		return cst;
	}

	public Dinheiro baseCalculo() {
		return baseCalculo;
	}

	public Aliquota aliquota() {
		return aliquota;
	}

	public Dinheiro valor() {
		if (cst()==null)
			return Dinheiro.ZERO;
		switch (cst()) {
		case CST_03:
			return calcularPelaQuantidade();
		default:
			return baseCalculo().multiplicar(aliquota().valorDecimal());
		}
	}
	
	private Dinheiro calcularPelaQuantidade(){
		return new Dinheiro( 
				new BigDecimal(quantidade())
					.multiply(new BigDecimal(aliquotaEmReais())));
	}
	
	public Double quantidade() {
		return quantidade;
	}

	public Double aliquotaEmReais() {
		return aliquotaEmReais;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Cofins objetoTipado = (Cofins) objeto;
			objetosIguais = new EqualsBuilder()
				.append(cst, objetoTipado.cst)
				.append(baseCalculo(), objetoTipado.baseCalculo())
				.append(aliquota(), objetoTipado.aliquota())
				.append(quantidade(), objetoTipado.quantidade())
				.append(aliquotaEmReais(), objetoTipado.aliquotaEmReais())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(3219,121)
		.append(cst())
		.append(baseCalculo())
		.append(aliquota())
		.append(quantidade())
		.append(aliquotaEmReais())
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Cofins [cst=" + cst()
			+ ",baseCalculo=" + baseCalculo()
			+ ",aliquota=" + aliquota()
			+ ",quantidade=" + quantidade()
			+ ",aliquotaEmReais=" + aliquotaEmReais()
			+ "]";
	}	

	/*
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Cofins(){}
	
}
