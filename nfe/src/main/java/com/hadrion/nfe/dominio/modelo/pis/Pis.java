package com.hadrion.nfe.dominio.modelo.pis;

import com.hadrion.nfe.tipos.Dinheiro;

public class Pis {
	public static final Pis NULO = new Pis(null, Dinheiro.ZERO, 0.0, Dinheiro.ZERO, 0.0, 0.0);
	private Cst cst;
	private Dinheiro baseCalculo;
	private Double aliquota;
	private Dinheiro valor;
	private Double quantidade;
	private Double aliquotaEmReais;
	
	public Pis(Cst cst, Dinheiro baseCalculo, Double aliquota, Dinheiro valor,
			Double quantidade, Double aliquotaEmReais) {
		super();
		this.cst = cst;
		this.baseCalculo = baseCalculo;
		this.aliquota = aliquota;
		this.valor = valor;
		this.quantidade = quantidade;
		this.aliquotaEmReais = aliquotaEmReais;
	}

	public Cst cst() {
		return cst;
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

	public Double quantidade() {
		return quantidade;
	}

	public Double aliquotaEmReais() {
		return aliquotaEmReais;
	}
	
	
	
}