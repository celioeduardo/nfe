package com.hadrion.nfe.dominio.modelo.nf.transporte;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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

	public Double pesoLiquido() {
		return pesoLiquido;
	}

	public Double pesoBruto() {
		return pesoBruto;
	}

	public List<String> lacres(){
		return lacres;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Volume objetoTipado = (Volume) objeto;
			objetosIguais = new EqualsBuilder()
				.append(quantidade(),objetoTipado.quantidade())
				.append(especie(),objetoTipado.especie())
				.append(marca(),objetoTipado.marca())
				.append(numeracao(),objetoTipado.numeracao())
				.append(pesoLiquido(),objetoTipado.pesoLiquido())
				.append(pesoBruto(),objetoTipado.pesoBruto())
				.append(lacres(),objetoTipado.lacres())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(4421,257)
			.append(quantidade())
			.append(especie())
			.append(marca())
			.append(numeracao())
			.append(pesoLiquido())
			.append(pesoBruto())
			.append(lacres())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Volume [quantidade=" + quantidade()
				+ ",especie="+ especie()
				+ ",marca="+ marca()
				+ ",numeracao="+ numeracao()
				+ ",pesoLiquido="+ pesoLiquido()
				+ ",pesoBruto="+ pesoBruto()
				+ ",lacres="+ lacres()
				+ "]";
	}
}
