package com.hadrion.nfe.dominio.modelo.pis;

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
public class Pis {
	public static final Pis NULO = new Pis(null, Dinheiro.ZERO, Aliquota.ZERO, 0.0, 0.0);
	
	@Enumerated
	@Column(name="PIS_CST")
	private CstPis cst;
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="PIS_BC"))
	private Dinheiro baseCalculo;
	
	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="PIS_ALIQUOTA"))
	private Aliquota aliquota;
	
	@Column(name="PIS_QUANTIDADE")
	private Double quantidade;
	
	@Column(name="PIS_ALIQ_REAIS")
	private Double aliquotaEmReais;
	
	public Pis(CstPis cst, Dinheiro baseCalculo, Aliquota aliquota, 
			Double quantidade, Double aliquotaEmReais) {
		super();
		this.cst = cst;
		this.baseCalculo = baseCalculo;
		this.aliquota = aliquota;
		this.quantidade = quantidade;
		this.aliquotaEmReais = aliquotaEmReais;
	}

	public CstPis cst() {
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
			Pis objetoTipado = (Pis) objeto;
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
		return new HashCodeBuilder(1127,111)
		.append(cst())
		.append(baseCalculo())
		.append(aliquota())
		.append(quantidade())
		.append(aliquotaEmReais())
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Pis [cst=" + cst()
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
	private Pis(){}
}
