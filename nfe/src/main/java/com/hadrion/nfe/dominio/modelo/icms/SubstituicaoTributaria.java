package com.hadrion.nfe.dominio.modelo.icms;

import java.math.BigDecimal;

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
	
	public static final SubstituicaoTributaria NULA = new SubstituicaoTributaria(
			Percentual.ZERO,Dinheiro.ZERO,Aliquota.ZERO,null, Percentual.ZERO);
	
	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="ICMS_ST_PER_RBC"))
	private Percentual percentualReducaoBaseCalculo;
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="ICMS_ST_VAL_OPERACAO"))
	private Dinheiro valorOperacao;
	
	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="ICMS_ST_ALIQUOTA"))
	private Aliquota aliquota;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ICMS_ST_DET_BC")
	private DeterminacaoBaseCalculoSt determinacaoBaseCalculo;
	
	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="ICMS_ST_PER_MVA"))	
	private Percentual percentualMargemValorAdicionado;
	
	public SubstituicaoTributaria(Percentual percentualReducaoBaseCalculo,
			Dinheiro valorOperacao, Aliquota aliquota, 
			DeterminacaoBaseCalculoSt determinacaoBaseCalculo,
			Percentual percentualMargemValorAdicionado) {
		this.percentualReducaoBaseCalculo = percentualReducaoBaseCalculo;
		this.valorOperacao = valorOperacao;
		this.aliquota = aliquota;
		this.determinacaoBaseCalculo = determinacaoBaseCalculo;
		this.percentualMargemValorAdicionado = percentualMargemValorAdicionado;
	}
	
	public Percentual percentualReducaoBaseCalculo() {
		return percentualReducaoBaseCalculo;
	}
	
	public Aliquota aliquota() {
		return aliquota;
	}

	public Dinheiro valor() {
		return calcularImpostoBase();
	}
	
	public Dinheiro calcularImpostoBase(){
		return baseCalculo()
		.multiplicar(aliquota().valorDecimal());
	}
	
	public Dinheiro baseCalculo(){
		return valorOperacao
				.multiplicar(BigDecimal.ONE.add(percentualMargemValorAdicionado().valorDecimalComoBigDecimal()))
				.multiplicar(percentualReducaoBaseCalculo().valorComplementarDecimal());
	}
	
	public Dinheiro valorOperacao(){
		return valorOperacao;
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
				.append(percentualReducaoBaseCalculo, objetoTipado.percentualReducaoBaseCalculo)
				.append(valorOperacao, objetoTipado.valorOperacao)
				.append(aliquota, objetoTipado.aliquota)
				.append(determinacaoBaseCalculo, objetoTipado.determinacaoBaseCalculo)
				.append(percentualMargemValorAdicionado, objetoTipado.percentualMargemValorAdicionado)
				.isEquals();
		}

		return objetosIguais;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(6645,71)
		.append(percentualReducaoBaseCalculo())
		.append(valorOperacao())
		.append(aliquota())
		.append(determinacaoBaseCalculo())
		.append(percentualMargemValorAdicionado())			
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "SubstituicaoTributaria [" 
				+"percentualReducaoBaseCalculo=" + percentualReducaoBaseCalculo() 
				+",valorOperacao=" + valorOperacao() 
				+",aliquota=" + aliquota() 
				+",determinacaoBaseCalculo=" + determinacaoBaseCalculo() 
				+",percentualMargemValorAdicionado=" + percentualMargemValorAdicionado() 
				+ "]";
	}
	
	
	/*
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private SubstituicaoTributaria(){}
}
