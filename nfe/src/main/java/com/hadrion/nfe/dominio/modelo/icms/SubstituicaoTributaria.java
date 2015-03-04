package com.hadrion.nfe.dominio.modelo.icms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Percentual;

@Embeddable
@Access(AccessType.FIELD)
public class SubstituicaoTributaria {
	
	public static final SubstituicaoTributaria NULA = 
			new SubstituicaoTributaria(
			Dinheiro.ZERO,Aliquota.ZERO,Dinheiro.ZERO,Percentual.ZERO, null, Percentual.ZERO);
	
	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="ICMS_ST_PER_RBC"))
	private Percentual percentualReducaoBaseCalculo;

	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="ICMS_ST_ALIQUOTA"))
	private Aliquota aliquota;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ICMS_ST_DET_BC")
	private DeterminacaoBaseCalculoSt determinacaoBaseCalculo;
	
	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="ICMS_ST_PER_MVA"))	
	private Percentual percentualMargemValorAdicionado;

	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="ICMS_ST_BASE_CALCULO"))
	protected Dinheiro baseCalculo;

	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="ICMS_ST_VALOR"))
	protected Dinheiro valor;
	
	public SubstituicaoTributaria(
			Dinheiro baseCalculo,
			Aliquota aliquota,
			Dinheiro valor,
			Percentual percentualReducaoBaseCalculo,
			DeterminacaoBaseCalculoSt determinacaoBaseCalculo,
			Percentual percentualMargemValorAdicionado) {
		this.baseCalculo = baseCalculo;
		this.aliquota = aliquota;
		this.valor = valor;
		this.percentualReducaoBaseCalculo = percentualReducaoBaseCalculo;
		this.determinacaoBaseCalculo = determinacaoBaseCalculo;
		this.percentualMargemValorAdicionado = percentualMargemValorAdicionado;
	}
	
	public Percentual percentualReducaoBaseCalculo() {
		return percentualReducaoBaseCalculo;
	}
	
	public Aliquota aliquota() {
		if (aliquota == null)
			return Aliquota.ZERO;
		return aliquota;
	}

	public Dinheiro valor() {
		if (valor == null)
			return Dinheiro.ZERO;
		return valor;
	}
	
	public Dinheiro calcularImpostoBase(){
		return baseCalculo()
		.multiplicar(aliquota().valorDecimal());
	}
	
	public Dinheiro baseCalculo(){
		if(baseCalculo == null)
			return Dinheiro.ZERO;
		return baseCalculo;
	}
	
	public DeterminacaoBaseCalculoSt determinacaoBaseCalculo() {
		return determinacaoBaseCalculo;
	}

	public Percentual percentualMargemValorAdicionado() {
		return percentualMargemValorAdicionado;
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			SubstituicaoTributaria objetoTipado = (SubstituicaoTributaria) objeto;
			objetosIguais = new EqualsBuilder()
				.append(baseCalculo(), objetoTipado.baseCalculo())
				.append(aliquota(), objetoTipado.aliquota())
				.append(valor(), objetoTipado.valor())
				.append(percentualReducaoBaseCalculo(), objetoTipado.percentualReducaoBaseCalculo())
				.append(determinacaoBaseCalculo(), objetoTipado.determinacaoBaseCalculo())
				.append(percentualMargemValorAdicionado(), objetoTipado.percentualMargemValorAdicionado())
				.isEquals();
		}

		return objetosIguais;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(6645,71)
		.append(baseCalculo())
		.append(aliquota())
		.append(valor())
		.append(percentualReducaoBaseCalculo())
		.append(determinacaoBaseCalculo())
		.append(percentualMargemValorAdicionado())			
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "SubstituicaoTributaria [" 
				+",baseCalculo=" + baseCalculo() 
				+",aliquota=" + aliquota() 
				+",valor=" + valor() 
				+",determinacaoBaseCalculo=" + determinacaoBaseCalculo() 
				+"percentualReducaoBaseCalculo=" + percentualReducaoBaseCalculo() 
				+",percentualMargemValorAdicionado=" + percentualMargemValorAdicionado() 
				+ "]";
	}
	
	/*
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private SubstituicaoTributaria(){}
}
