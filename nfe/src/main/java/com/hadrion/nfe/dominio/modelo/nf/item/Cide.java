package com.hadrion.nfe.dominio.modelo.nf.item;

import com.hadrion.nfe.tipos.Dinheiro;

public class Cide {
	private Double baseCalculo;
	private Double aliquota;
	private Dinheiro valor;
	
	public Cide(Double baseCalculo, Double aliquota, Dinheiro valor) {
		super();
		this.baseCalculo = baseCalculo;
		this.aliquota = aliquota;
		this.valor = valor;
	}
	
	public Double baseCalculo(){
		return baseCalculo;
	}
	
	public Double aliquota(){
		return aliquota;
	}
	
	public Dinheiro valor(){
		return valor;
	}
}
