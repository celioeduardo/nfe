package com.hadrion.nfe.dominio.modelo.icms;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Percentual;

public class Icms {
	
	public static final Icms NULO = 
			new Icms(null, null, null,Percentual.ZERO, Dinheiro.ZERO, 
					Aliquota.ZERO,null, Percentual.ZERO);
	
	private Origem origem;
	private Cst cst;
	private DeterminacaoBaseCalculo determinacaoBaseCalculo;
	private Percentual percentualReducaoBaseCalculo;
	private Dinheiro valorOperacao;
	private Aliquota aliquota;
	private SubstituicaoTributaria substituicaoTributaria;
	private Percentual percentualDiferimento; //CST 51
	//private Dinheiro valorDiferido; //CST51
	
	public Icms(Origem origem, Cst cst,
			DeterminacaoBaseCalculo determinacaoBaseCalculo,
			Percentual percentualReducaoBaseCalculo, Dinheiro valorOperacao,
			Aliquota aliquota,
			SubstituicaoTributaria substituicaoTributaria,
			Percentual percentualDiferimento) {
		super();
		this.origem = origem;
		this.cst = cst;
		this.determinacaoBaseCalculo = determinacaoBaseCalculo;
		this.percentualReducaoBaseCalculo = percentualReducaoBaseCalculo;
		this.valorOperacao = valorOperacao;
		this.aliquota = aliquota;
		this.substituicaoTributaria = substituicaoTributaria;
		this.percentualDiferimento = percentualDiferimento;
	}
	
	public Dinheiro baseCalculo(){
		return valorOperacao.multiplicar(percentualReducaoBaseCalculo().valorComplementarDecimal());
	}
	public Dinheiro valorOperacao(){
		return valorOperacao;
	}
	public Dinheiro valor(){
		return calcularImpostoBase()
			.multiplicar(percentualDiferimento().valorComplementarDecimalComoBigDecimal());
	}
	
	public Dinheiro calcularImpostoBase(){
		return baseCalculo()
		.multiplicar(aliquota().valorDecimal());
	}
	
	public Aliquota aliquota(){
		return aliquota;
	}
	
	public Origem origem() {
		return origem;
	}

	public Cst cst(){
		return cst;
	}
	
	public DeterminacaoBaseCalculo determinacaoBaseCalculo() {
		return determinacaoBaseCalculo;
	}
	
	public Percentual percentualReducaoBaseCalculo(){
		if (percentualReducaoBaseCalculo == null)
			return Percentual.ZERO;
		return percentualReducaoBaseCalculo;
	}
	
	public Percentual percentualDiferimento(){
		return percentualDiferimento;
	}
	
	public Dinheiro valorDiferido(){
		return calcularImpostoBase().subtrair(valor());
	}
	
	public Dinheiro valorSemDiferimento() {
		return calcularImpostoBase();
	}

	public SubstituicaoTributaria st(){
		if (substituicaoTributaria == null)
			return SubstituicaoTributaria.NULA;
		return substituicaoTributaria;
	}

	public static Icms tributacaoIntegral_00(
			Origem origem, Dinheiro baseCalculo, Aliquota aliquota, 
			DeterminacaoBaseCalculo determinacaoBaseCalculo){
		return new Icms(origem,
			Cst.CST_00,
			determinacaoBaseCalculo,
			Percentual.ZERO,
			baseCalculo,
			aliquota,
			null,
			Percentual.ZERO);
	}
	public static Icms cst_00(Origem origem, Dinheiro valorOperacao, Aliquota aliquota, 
			DeterminacaoBaseCalculo determinacaoBaseCalculo){
		return tributacaoIntegral_00(origem, valorOperacao, aliquota, determinacaoBaseCalculo);
	}
	
	public static Icms cst_51(Origem origem, Dinheiro valorOperacao,
			Aliquota aliquota, Percentual percentualReducaoBaseCalculo,
			Percentual percentualDiferimento,
			DeterminacaoBaseCalculo determinacaoBaseCalculo) {
		return new Icms(
				origem, 
				Cst.CST_51, 
				determinacaoBaseCalculo, 
				percentualReducaoBaseCalculo, 
				valorOperacao, 
				aliquota, 
				null, 
				percentualDiferimento);
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
				.append(valorOperacao, objetoTipado.valorOperacao)
				.append(aliquota, objetoTipado.aliquota)
				.append(substituicaoTributaria, objetoTipado.substituicaoTributaria)
				.append(percentualDiferimento, objetoTipado.percentualDiferimento)
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
		.append(st())
		.append(percentualDiferimento())
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Icms [origem=" + origem() 
			+ ",cst=" + cst()
			+ ",determinacaoBaseCalculo=" + determinacaoBaseCalculo()
			+ ",percentualReducaoBaseCalculo=" + percentualReducaoBaseCalculo() 
			+ ",valorOperacao=" + valorOperacao()
			+ ",aliquota=" + aliquota()
			+ ",valor=" + valor()
			+ ",substituicaoTributaria=" + st()
			+ ",percentualDiferimento=" + percentualDiferimento()
			+ "]";
	}

	public static Dinheiro calcularValorOperacao(Dinheiro baseCalculo,
			Percentual percentualReducaoBaseCalculo) {
		return baseCalculo.dividir(percentualReducaoBaseCalculo.valorComplementarDecimalComoBigDecimal());
	}
}
