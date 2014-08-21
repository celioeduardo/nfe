package com.hadrion.nfe.dominio.modelo.nf.transporte;

import java.util.List;

public class Volume {
	
	private int quantidade;
	private String especie;
	private String marca;
	private String numeracao;
	private Double pesoLiquido;
	private Double pesoBruto;
	private List<String> lacres;
	
	public Volume(int quantidade, String especie, String marca,
			String numeracao, Double pesoLiquido, Double pesoBruto, 
			List<String> lacres) {
		super();
		this.quantidade = quantidade;
		this.especie = especie;
		this.marca = marca;
		this.numeracao = numeracao;
		this.pesoLiquido = pesoLiquido;
		this.pesoBruto = pesoBruto;
		this.lacres = lacres;
	}

	public int quantidade() {
		return quantidade;
	}

	public String especie() {
		return especie;
	}

	public String marca() {
		return marca;
	}

	public String numeracao() {
		return numeracao;
	}

	public Double pPesoLiquido() {
		return pesoLiquido;
	}

	public Double pesoBruto() {
		return pesoBruto;
	}

	public List<String> lacres(){
		return lacres;
	}
}
