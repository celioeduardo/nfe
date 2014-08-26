package com.hadrion.nfe.dominio.modelo.nf.item;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Quantidade;

public class DescritorProduto {
	
	private String codigo;
	private Gtin gtin;
	private String descricao;
	private Ncm ncm;
	private String nve;
	private String extipi;
	private Cfop cfop;
	private String unidadeComercial;
	private Quantidade quantidadeComercial;
	private Double valorUnitarioComercializacao;
	private Dinheiro valorTotalBruto;
	private Gtin gtinTributavel;
	private String unidadeTributavel;
	private Quantidade quantidadeTributavel;
	private Double valorUnitarioTributacao;
	private Dinheiro totalFrete;
	private Dinheiro totalSeguro;
	private Dinheiro valorDesconto;
	private Dinheiro outrasDespesasAcessorias;
	private Exportacao exportacao;
	private Combustivel combustivel;
	
	public DescritorProduto(String codigo, Gtin gtin, String descricao,
			Ncm ncm, String nve, String extipi, Cfop cfop,
			String unidadeComercial, Quantidade quantidadeComercial,
			Double valorUnitarioComercializacao, Dinheiro valorTotalBruto,
			Gtin gtinTributavel, String unidadeTributavel,
			Quantidade quantidadeTributavel, Double valorUnitarioTributacao,
			Dinheiro totalFrete, Dinheiro totalSeguro, Dinheiro valorDesconto,
			Dinheiro outrasDespesasAcessorias,
			Exportacao exportacao,
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
	public DescritorProduto(String codigo, String descricao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
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
	public Exportacao exportacao(){
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
		int hashCodeValue = 
				+ (1234579 * 3473) 
				+ this.codigo.hashCode()
				+ this.gtin.hashCode()
				+ this.descricao.hashCode()
				+ this.ncm.hashCode()
				+ this.nve.hashCode()
				+ this.extipi.hashCode()
				+ this.cfop.hashCode()
				+ this.unidadeComercial.hashCode()
				+ this.quantidadeComercial.hashCode()
				+ this.valorUnitarioComercializacao.hashCode()
				+ this.valorTotalBruto.hashCode()
				+ this.gtinTributavel.hashCode()
				+ this.unidadeTributavel.hashCode()
				+ this.quantidadeTributavel.hashCode()
				+ this.valorUnitarioTributacao.hashCode()
				+ this.totalFrete.hashCode()
				+ this.totalSeguro.hashCode()
				+ this.valorDesconto.hashCode()
				+ this.outrasDespesasAcessorias.hashCode()
				+ this.exportacao.hashCode()
				+ this.combustivel.hashCode();
		
		return hashCodeValue;
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
