package com.hadrion.nfe.dominio.modelo.nf.transporte;

import javax.persistence.Column;
import javax.persistence.Embedded;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEstadual;

public class Transportador {
	@Embedded
	private Cnpj cnpj;
	
	@Embedded
	private Cpf cpf;
	
	@Column(name="RAZAO_SOCIAL")
	private String razaoSocial;
	
	@Embedded
	private InscricaoEstadual ie;
	
	@Embedded
	private Endereco endereco;
	
	public Transportador(Cnpj cnpj, Cpf cpf, String razaoSocial,
			InscricaoEstadual ie, Endereco endereco) {
		super();
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.razaoSocial = razaoSocial;
		this.ie = ie;
		this.endereco = endereco;
	}

	public Cnpj cnpj() {
		return cnpj;
	}

	public Cpf cpf() {
		return cpf;
	}

	public String razaoSocial() {
		return razaoSocial;
	}

	public InscricaoEstadual ie() {
		return ie;
	}

	public Endereco endereco() {
		return endereco;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Transportador objetoTipado = (Transportador) objeto;
			objetosIguais = new EqualsBuilder()
				.append(cnpj(),objetoTipado.cnpj())
				.append(cpf(),objetoTipado.cpf())
				.append(razaoSocial(),objetoTipado.razaoSocial())
				.append(ie(),objetoTipado.ie())
				.append(endereco(),objetoTipado.endereco())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(2899,221)
			.append(cnpj())
			.append(cpf())
			.append(razaoSocial())
			.append(ie())
			.append(endereco())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Transportador [cnpj=" + cnpj()
				+ ",cpf="+ cpf()
				+ ",razaoSocial="+ razaoSocial()
				+ ",ie="+ ie()
				+ ",endereco="+ endereco()
				+ "]";
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Transportador(){}
	
}
