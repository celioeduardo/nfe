package com.hadrion.nfe.dominio.modelo.nf.transporte;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@SequenceGenerator(name="SEQ",sequenceName="SQ_VOLUME")
@Table(name="VOLUME")
public class Volume {
	
	@Column(name="QUANTIDADE")
	private int quantidade;
	
	@Column(name="ESPECIE")
	private String especie;
	
	@Column(name="MARCA")
	private String marca;
	
	@Column(name="NUMERACAO")
	private String numeracao;
	
	@Column(name="PESO_LIQUIDO")
	private Double pesoLiquido;
	
	@Column(name="PESO_BRUTO")
	private Double pesoBruto;
	
	@ElementCollection
	@CollectionTable(name="LACRES")
	@Column(name="LACRE")
	private List<String> lacres;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
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
		return getLacres();
	}
	
	private List<String> getLacres(){
		if (lacres == null)
			lacres = new ArrayList<String>();
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
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Volume(){}
}
