package com.hadrion.nfe.dominio.modelo.icms;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.tipos.Dinheiro;

public class Icms {
	
	public static final Icms NULO = 
			new Icms(null, null, null, 0.0, Dinheiro.ZERO, 0.0, Dinheiro.ZERO, 
					null, 0.0, Dinheiro.ZERO);
	
	private Origem origem;
	private Cst cst;
	private DeterminacaoBaseCalculo determinacaoBaseCalculo;
	private Double percentualReducaoBaseCalculo;
	private Dinheiro baseCalculo;
	private Double aliquota;
	private Dinheiro valor;
	private SubstituicaoTributaria substituicaoTributaria;
	private Double percentualDiferimento; //CST 51
	private Dinheiro valorIcmsDiferio; //CST51
	
	public Icms(Origem origem, Cst cst,
			DeterminacaoBaseCalculo determinacaoBaseCalculo,
			Double percentualReducaoBaseCalculo, Dinheiro baseCalculo,
			Double aliquota, Dinheiro valor,
			SubstituicaoTributaria substituicaoTributaria,
			Double percentualDiferimento, Dinheiro valorIcmsDiferio) {
		super();
		this.origem = origem;
		this.cst = cst;
		this.determinacaoBaseCalculo = determinacaoBaseCalculo;
		this.percentualReducaoBaseCalculo = percentualReducaoBaseCalculo;
		this.baseCalculo = baseCalculo;
		this.aliquota = aliquota;
		this.valor = valor;
		this.substituicaoTributaria = substituicaoTributaria;
		this.percentualDiferimento = percentualDiferimento;
		this.valorIcmsDiferio = valorIcmsDiferio;
	}
	
	public Dinheiro baseCalculo(){
		return baseCalculo;
	}
	
	public Dinheiro valor(){
		return valor;
	}
	
	public SubstituicaoTributaria st(){
		if (substituicaoTributaria == null)
			return SubstituicaoTributaria.NULA;
		return substituicaoTributaria;
	}

	public static Icms tributacaoIntegral_00(
			Origem origem, Dinheiro baseCalculo, Double aliquota, 
			DeterminacaoBaseCalculo determinacaoBaseCalculo){
		return new Icms(origem,
			Cst.CST_00,
			determinacaoBaseCalculo,
			new Double(0.0),
			baseCalculo,
			new Double(100),
			baseCalculo.multiplicar(aliquota/100),
			null,
			new Double(0),
			Dinheiro.ZERO);
	}
	public static Icms cst_00(Origem origem, Dinheiro baseCalculo, Double aliquota, 
			DeterminacaoBaseCalculo determinacaoBaseCalculo){
		return tributacaoIntegral_00(origem, baseCalculo, aliquota, determinacaoBaseCalculo);
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Icms objetoTipado = (Icms) objeto;
			objetosIguais = new EqualsBuilder()
				.append(origem, objetoTipado.origem)
				.append(cst, objetoTipado.cst)
				.append(determinacaoBaseCalculo, objetoTipado.determinacaoBaseCalculo)
				.append(percentualReducaoBaseCalculo, objetoTipado.percentualReducaoBaseCalculo)
				.append(baseCalculo, objetoTipado.baseCalculo)
				.append(aliquota, objetoTipado.aliquota)
				.append(valor, objetoTipado.valor)
				.append(substituicaoTributaria, objetoTipado.substituicaoTributaria)
				.append(percentualDiferimento, objetoTipado.percentualDiferimento)
				.append(valorIcmsDiferio, objetoTipado.valorIcmsDiferio)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(4325,19)
		.append(origem)
		.append(cst)
		.append(determinacaoBaseCalculo)
		.append(percentualReducaoBaseCalculo)
		.append(baseCalculo)
		.append(aliquota)
		.append(valor)
		.append(substituicaoTributaria)
		.append(percentualDiferimento)
		.append(valorIcmsDiferio)			
		.toHashCode();
	}
	
	@Override
	public String toString() {
		//TODO complementar toString
		return "Icms [origem=" + origem + "]";
	}
}
