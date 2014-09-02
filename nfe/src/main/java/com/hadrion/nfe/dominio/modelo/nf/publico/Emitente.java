package com.hadrion.nfe.dominio.modelo.nf.publico;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Telefone;

public class Emitente {
	//TODO - equals
	private Cnpj cnpj;
	private Cpf cpf;
	private String razaoSocial;
	private String nomeFantasia;
	private Endereco endereco;
	private Telefone telefone;
	private InscricaoEstadual ie;
	private InscricaoEstadual ieSubstituto;
	
	public Emitente(Cnpj cnpj, Cpf cpf, String razaoSocial,
			String nomeFantasia, Endereco endereco, Telefone telefone,
			InscricaoEstadual ie, InscricaoEstadual ieSubstituto) {
		super();
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.endereco = endereco;
		this.telefone = telefone;
		this.ie = ie;
		this.ieSubstituto = ieSubstituto;
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

	public String nomeFantasia() {
		return nomeFantasia;
	}

	public Endereco endereco() {
		return endereco;
	}

	public Telefone telefone() {
		return telefone;
	}

	public InscricaoEstadual ie() {
		return ie;
	}

	public InscricaoEstadual ieSubstituto() {
		return ieSubstituto;
	}
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Emitente objetoTipado = (Emitente) objeto;
			objetosIguais = new EqualsBuilder()
				.append(cnpj,objetoTipado.cnpj)
				.append(cpf,objetoTipado.cpf)
				.append(razaoSocial,objetoTipado.razaoSocial)
				.append(nomeFantasia,objetoTipado.nomeFantasia)
				.append(endereco,objetoTipado.endereco)
				.append(telefone,objetoTipado.telefone)
				.append(ie,objetoTipado.ie)
				.append(ieSubstituto,objetoTipado.ieSubstituto)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13157,19317)
			.append(cnpj)
			.append(cpf)
			.append(razaoSocial)
			.append(nomeFantasia)
			.append(endereco)
			.append(telefone)
			.append(ie)
			.append(ieSubstituto)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Emitente [cnpj=" + cnpj
				+ ",cpf="+ cpf
				+ ",razaoSocial="+ razaoSocial
				+ ",nomeFantasia="+ nomeFantasia
				+ ",endereco="+ endereco
				+ ",telefone="+ telefone
				+ ",ie="+ ie
				+ ",ieSubstituto="+ ieSubstituto
				+ "]";
	} 
}
