package com.hadrion.nfe.dominio.modelo.nf.transporte;

import com.hadrion.nfe.dominio.modelo.nf.item.Cfop;
import com.hadrion.nfe.tipos.Dinheiro;

public class RetencaoIcms {
	private Dinheiro valorServico;
	private Dinheiro baseCalculo;
	private Double aliquota;
	private Dinheiro valor;
	private Cfop cfop;
	
	public RetencaoIcms(Dinheiro valorServico, Dinheiro baseCalculo,
			Double aliquota, Dinheiro valor, Cfop cfop) {
		super();
		this.valorServico = valorServico;
		this.baseCalculo = baseCalculo;
		this.aliquota = aliquota;
		this.valor = valor;
		this.cfop = cfop;
	}

	public Dinheiro valorServico() {
		return valorServico;
	}

	public Dinheiro baseCalculo() {
		return baseCalculo;
	}

	public Double aliquota() {
		return aliquota;
	}

	public Dinheiro valor() {
		return valor;
	}

	public Cfop cfop() {
		return cfop;
	}
	
	
	
}
