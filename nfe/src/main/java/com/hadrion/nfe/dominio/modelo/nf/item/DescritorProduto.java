package com.hadrion.nfe.dominio.modelo.nf.item;

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
}
