package com.hadrion.nfe.dominio.modelo.nf.item;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Quantidade;

@Embeddable
@Access(AccessType.FIELD)
public class DescritorProduto {
	
	@Column(name="CODIGO")
	private String codigo;
	
	@Embedded
	private Gtin gtin;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Embedded
	private Ncm ncm;
	
	@Column(name="NVE")
	private String nve;
	
	@Column(name="EXTIPI")
	private String extipi;
	
	@Embedded
	private Cfop cfop;
	
	@Column(name="CMR_UNIDADE")
	private String unidadeComercial;
	
	@Embedded
	@AttributeOverride(name="quantidade", column=@Column(name="CMR_QUANTIDADE"))
	private Quantidade quantidadeComercial;
	
	@Column(name="CMR_VAL_UN")
	private Double valorUnitarioComercializacao;
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="CMR_VAL_TOTAL_BRUTO"))
	private Dinheiro valorTotalBruto;
	
	@Embedded
	@AttributeOverride(name="gtin", column=@Column(name="TRIB_GTIN"))
	private Gtin gtinTributavel;
	
	@Column(name="TRIB_UNIDADE")
	private String unidadeTributavel;
	
	@Embedded
	@AttributeOverride(name="quantidade", column=@Column(name="TRIB_QUANTIDADE"))
	private Quantidade quantidadeTributavel;
	
	@Column(name="TRIB_VAL_UN")
	private Double valorUnitarioTributacao;
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="TOTAL_FRETE"))
	private Dinheiro totalFrete;
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="TOTAL_SEGURO"))
	private Dinheiro totalSeguro;
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="VAL_DESCONTO"))
	private Dinheiro valorDesconto;
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="VAL_OUTR_DESP_AC"))
	private Dinheiro outrasDespesasAcessorias;
	
	@Embedded
	private ExportacaoItem exportacao;
	
	@Embedded
	private Combustivel combustivel;
	
	public DescritorProduto(String codigo, Gtin gtin, String descricao,
			Ncm ncm, String nve, String extipi, Cfop cfop,
			String unidadeComercial, Quantidade quantidadeComercial,
			Double valorUnitarioComercializacao, Dinheiro valorTotalBruto,
			Gtin gtinTributavel, String unidadeTributavel,
			Quantidade quantidadeTributavel, Double valorUnitarioTributacao,
			Dinheiro totalFrete, Dinheiro totalSeguro, Dinheiro valorDesconto,
			Dinheiro outrasDespesasAcessorias,
			ExportacaoItem exportacao,
			Combustivel combustivel) {
		super();
		this.codigo = codigo;
		this.gtin = gtin;
		this.descricao = descricao;
		this.ncm = ncm;
		this.nve = nve;
		this.extipi = extipi;
		this.cfop = cfop;	
		this.unidadeComercial = unidadeComercial;
		this.quantidadeComercial = quantidadeComercial;
		this.valorUnitarioComercializacao = valorUnitarioComercializacao;
		this.valorTotalBruto = valorTotalBruto;
		this.gtinTributavel = gtinTributavel;
		this.unidadeTributavel = unidadeTributavel;
		this.quantidadeTributavel = quantidadeTributavel;
		this.valorUnitarioTributacao = valorUnitarioTributacao;
		this.totalFrete = totalFrete;
		this.totalSeguro = totalSeguro;
		this.valorDesconto = valorDesconto;
		this.outrasDespesasAcessorias = outrasDespesasAcessorias;
		this.exportacao = exportacao;
		this.combustivel = combustivel;
	}
	public String codigo() {
		return codigo;
	}
	public Gtin gtin() {
		return gtin;
	}
	public String descricao() {
		return descricao;
	}
	public Ncm ncm() {
		return ncm;
	}
	public String nve() {
		return nve;
	}
	public String extipi() {
		return extipi;
	}
	public Cfop cfop() {
		return cfop;
	}
	public String unidadeComercial() {
		return unidadeComercial;
	}
	public Quantidade quantidadeComercial() {
		return quantidadeComercial;
	}
	public Double valorUnitarioComercializacao() {
		return valorUnitarioComercializacao;
	}
	public Dinheiro totalBruto() {
		return valorTotalBruto;
	}
	public Gtin gtinTributavel() {
		return gtinTributavel;
	}
	public String unidadeTributavel() {
		return unidadeTributavel;
	}
	public Quantidade quantidadeTributavel() {
		return quantidadeTributavel;
	}
	public Double valorUnitarioTributacao() {
		return valorUnitarioTributacao;
	}
	public Dinheiro totalFrete() {
		return totalFrete;
	}
	public Dinheiro totalSeguro() {
		return totalSeguro;
	}
	public Dinheiro valorDesconto() {
		return valorDesconto;
	}
	public Dinheiro outrasDespesasAcessorias() {
		return outrasDespesasAcessorias;
	}
	public ExportacaoItem exportacao(){
		return exportacao;
	}
	public Combustivel combustivel(){
		return this.combustivel;
	}
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			DescritorProduto objetoTipado = (DescritorProduto) objeto;
			
			objetosIguais = new EqualsBuilder()
			.append(codigo, objetoTipado.codigo)
			.append(gtin, objetoTipado.gtin)
			.append(descricao, objetoTipado.descricao)
			.append(ncm,objetoTipado.ncm)
			.append(nve,objetoTipado.nve)
			.append(extipi,objetoTipado.extipi)
			.append(cfop,objetoTipado.cfop)
			.append(unidadeComercial,objetoTipado.unidadeComercial)
			.append(quantidadeComercial,objetoTipado.quantidadeComercial)
			.append(valorUnitarioComercializacao,objetoTipado.valorUnitarioComercializacao)
			.append(valorTotalBruto,objetoTipado.valorTotalBruto)
			.append(gtinTributavel,objetoTipado.gtinTributavel)
			.append(unidadeTributavel,objetoTipado.unidadeTributavel)
			.append(quantidadeTributavel,objetoTipado.quantidadeTributavel)
			.append(valorUnitarioTributacao,objetoTipado.valorUnitarioTributacao)
			.append(totalFrete,objetoTipado.totalFrete)
			.append(totalSeguro,objetoTipado.totalSeguro)
			.append(valorDesconto,objetoTipado.valorDesconto)
			.append(outrasDespesasAcessorias,objetoTipado.outrasDespesasAcessorias)
			.append(exportacao,objetoTipado.exportacao)
			.append(combustivel,objetoTipado.combustivel)
			.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(1234579,3473) 
				.append(codigo)
				.append(gtin)
				.append(descricao)
				.append(ncm)
				.append(nve)
				.append(extipi)
				.append(cfop)
				.append(unidadeComercial)
				.append(quantidadeComercial)
				.append(valorUnitarioComercializacao)
				.append(valorTotalBruto)
				.append(gtinTributavel)
				.append(unidadeTributavel)
				.append(quantidadeTributavel)
				.append(valorUnitarioTributacao)
				.append(totalFrete)
				.append(totalSeguro)
				.append(valorDesconto)
				.append(outrasDespesasAcessorias)
				.append(exportacao)
				.append(combustivel)
				.toHashCode();		
	}
	
	@Override
	public String toString() {
		return "DescritorProduto [codigo=" + codigo
				+ ",gtin=" + gtin
				+ ",descricao=" + descricao
				+ ",nvm=" + ncm
				+ ",nve=" + nve
				+ ",extipin=" + extipi
				+ ",cfop=" + cfop
				+ ",unidadeComercial=" + unidadeComercial
				+ ",quantidadeComercial=" + quantidadeComercial
				+ ",valorUnitarioComercializacao=" + valorUnitarioComercializacao
				+ ",valorTotalBruto=" + valorTotalBruto
				+ ",gtinTributavel=" + gtinTributavel
				+ ",unidadeTributavel=" + unidadeTributavel
				+ ",quantidadeTributavel=" + quantidadeTributavel
				+ ",valorUnitarioTributacao=" + valorUnitarioTributacao
				+ ",totalFrete=" + totalFrete
				+ ",totalSeguro=" + totalSeguro
				+ ",valorDesconto=" + valorDesconto
				+ ",outrasDespesasAcessorias=" + outrasDespesasAcessorias
				+ ",exportacao=" + exportacao
				+ ",combustivel =" + combustivel + "]";
	
	}

}
