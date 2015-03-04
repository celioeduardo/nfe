package com.hadrion.nfe.dominio.modelo.icms;

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
import com.hadrion.nfe.tipos.Percentual;

@Embeddable
@Access(AccessType.FIELD)
public class Icms {
	
	public static final Icms NULO = 
			new Icms(null, null, null,Percentual.ZERO, Dinheiro.ZERO, Dinheiro.ZERO, 
					Aliquota.ZERO,null, Percentual.ZERO);
	
	@Enumerated
	@Column(name="ICMS_ORIGEM")
	private Origem origem;

	@Enumerated
	@Column(name="ICMS_CST")
	private Cst cst;
	@Enumerated
	@Column(name="ICMS_DET_BC")
	private DeterminacaoBaseCalculo determinacaoBaseCalculo;
	
	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="ICMS_PER_RBC"))
	private Percentual percentualReducaoBaseCalculo;
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="ICMS_VAL_DESCONTO_RBC"))
	private Dinheiro descontoReducaoBaseCalculo;

	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="ICMS_VAL_OPERACAO"))
	private Dinheiro valorOperacao;
	
	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="ICMS_ALIQUOTA"))
	private Aliquota aliquota;
	
	@Embedded
	private SubstituicaoTributaria substituicaoTributaria;
	
	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="ICMS_PER_DIFER"))
	private Percentual percentualDiferimento; //CST 51
	//private Dinheiro valorDiferido; //CST51

	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="ICMS_BASE_CALCULO"))
	protected Dinheiro baseCalculo;

	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="ICMS_VALOR"))
	protected Dinheiro valor;
	
	public Icms(Origem origem, Cst cst,
			DeterminacaoBaseCalculo determinacaoBaseCalculo,
			Percentual percentualReducaoBaseCalculo,
			Dinheiro descontoReducaoBaseCalculo,
			Dinheiro valorOperacao,
			Aliquota aliquota,
			SubstituicaoTributaria substituicaoTributaria,
			Percentual percentualDiferimento) {
		this(origem, 
			cst, 
			determinacaoBaseCalculo, 
			percentualReducaoBaseCalculo, 
			descontoReducaoBaseCalculo, 
			valorOperacao, 
			calcularBaseCalculo(valorOperacao, percentualReducaoBaseCalculo),
			aliquota, 
			calcularValor(
					calcularBaseCalculo(valorOperacao, percentualReducaoBaseCalculo), 
					aliquota, percentualDiferimento), 
			substituicaoTributaria, 
			percentualDiferimento);
	}
	public Icms(Origem origem, Cst cst,
			DeterminacaoBaseCalculo determinacaoBaseCalculo,
			Percentual percentualReducaoBaseCalculo,
			Dinheiro descontoReducaoBaseCalculo,
			Dinheiro valorOperacao,
			Dinheiro baseCalculo,
			Aliquota aliquota,
			Dinheiro valor,
			SubstituicaoTributaria substituicaoTributaria,
			Percentual percentualDiferimento) {
		super();
		this.origem = origem;
		this.cst = cst;
		this.determinacaoBaseCalculo = determinacaoBaseCalculo;
		this.percentualReducaoBaseCalculo = percentualReducaoBaseCalculo;
		this.descontoReducaoBaseCalculo = descontoReducaoBaseCalculo;
		this.valorOperacao = valorOperacao;
		this.baseCalculo = baseCalculo;
		this.aliquota = aliquota;
		this.valor = valor;
		this.substituicaoTributaria = substituicaoTributaria;
		this.percentualDiferimento = percentualDiferimento;
	}
	
	private static Dinheiro calcularBaseCalculo(Dinheiro valorOperacao,Percentual percentualRbc){
		return valorOperacao == null || percentualRbc == null ? 
				null :
				valorOperacao.multiplicar(
						percentualRbc.valorComplementarDecimal());
	}
	public Dinheiro valorOperacao(){
		return valorOperacao;
	}
	
	private static Dinheiro calcularValor(Dinheiro baseCalculo,Aliquota aliquota,Percentual percentualDiferimento){
		if (baseCalculo == null || aliquota == null)
			return null;
		
		if (percentualDiferimento != null)
			return calcularImpostoBase(baseCalculo,aliquota)
					.multiplicar(percentualDiferimento.valorComplementarDecimalComoBigDecimal());
		else
			return calcularImpostoBase(baseCalculo,aliquota);
	}
	
	public static Dinheiro calcularImpostoBase(Dinheiro baseCalculo,Aliquota aliquota){
		return 
			baseCalculo == null || aliquota == null ?
				null:
				baseCalculo
					.multiplicar(aliquota.valorDecimal());
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
	public Dinheiro descontoReducaoBaseCalculo(){
		if (descontoReducaoBaseCalculo == null)
			return Dinheiro.ZERO;
		return descontoReducaoBaseCalculo;
	}
	
	public Percentual percentualDiferimento(){
		return percentualDiferimento;
	}
	
	public Dinheiro valorDiferido(){
		return calcularImpostoBase(baseCalculo(),aliquota()).subtrair(valor());
	}
	
	public Dinheiro valorSemDiferimento() {
		return calcularImpostoBase(baseCalculo(),aliquota());
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
			Dinheiro.ZERO,
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
			Dinheiro descontoReducaoBaseCalculo,
			Percentual percentualDiferimento,
			DeterminacaoBaseCalculo determinacaoBaseCalculo) {
		return new Icms(
				origem, 
				Cst.CST_51, 
				determinacaoBaseCalculo, 
				percentualReducaoBaseCalculo,
				descontoReducaoBaseCalculo,
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
				.append(determinacaoBaseCalculo(), objetoTipado.determinacaoBaseCalculo())
				.append(percentualReducaoBaseCalculo(), objetoTipado.percentualReducaoBaseCalculo())
				.append(descontoReducaoBaseCalculo(), objetoTipado.descontoReducaoBaseCalculo())
				.append(valorOperacao(), objetoTipado.valorOperacao())
				.append(baseCalculo(), objetoTipado.baseCalculo())
				.append(aliquota(), objetoTipado.aliquota())
				.append(valor(), objetoTipado.valor())
				.append(st(), objetoTipado.st())
				.append(percentualDiferimento(), objetoTipado.percentualDiferimento())
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
		.append(descontoReducaoBaseCalculo())
		.append(valorOperacao())
		.append(baseCalculo())
		.append(aliquota())
		.append(valor())
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
			+ ",descontoReducaoBaseCalculo=" + descontoReducaoBaseCalculo() 
			+ ",valorOperacao=" + valorOperacao()
			+ ",baseCalculo=" + baseCalculo()
			+ ",aliquota=" + aliquota()
			+ ",valor=" + valor()
			+ ",substituicaoTributaria=" + st()
			+ ",percentualDiferimento=" + percentualDiferimento()
			+ "]";
	}

	public Dinheiro baseCalculo() {
		if (this.baseCalculo == null)
			return Dinheiro.ZERO;
		return this.baseCalculo;
	}

	public Dinheiro valor() {
		if (this.valor == null)
			return Dinheiro.ZERO;
		return this.valor;
	}

	public static Dinheiro calcularValorOperacao(Dinheiro baseCalculo,
			Percentual percentualReducaoBaseCalculo) {
		return baseCalculo.dividir(percentualReducaoBaseCalculo.valorComplementarDecimalComoBigDecimal());
	}

	/*
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Icms(){}
}
