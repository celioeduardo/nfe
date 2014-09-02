package com.hadrion.nfe.dominio.modelo.nf.publico;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.Email;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Telefone;

public class Destinatario {
	private Cnpj cnpj;
	private Cpf cpf;
	private String idEstrangeiro;
	private String razaoSocial;
	private String nomeFantasia;
	private Endereco endereco;
	private Telefone telefone;
	private IndicadorIe indicadorIe;
	private InscricaoEstadual ie;
	private Long inscricaoSuframa;
	private Email email;
	
	public Destinatario(Cnpj cnpj, Cpf cpf, String idEstrangeiro,
			String razaoSocial, String nomeFantasia, Endereco endereco,
			Telefone telefone, IndicadorIe indicadorIe, InscricaoEstadual ie,
			Long inscricaoSuframa, Email email) {
		super();
		if (cnpj!=null){
			this.cnpj = cnpj;
			this.cpf = null;
		} else {
			this.cnpj = null;
			this.cpf = cpf;			
		}
		this.idEstrangeiro = idEstrangeiro;
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.endereco = endereco;
		this.telefone = telefone;
		this.indicadorIe = indicadorIe;
		this.ie = ie;
		this.inscricaoSuframa = inscricaoSuframa;
		this.email = email;
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

	public String idEstrangeiro(){
		return idEstrangeiro;
	}
	public IndicadorIe indicadorIe(){
		return indicadorIe;
	}
	public Long inscricaoSuframa(){
		return inscricaoSuframa;
	}
	public Email email(){
		return email;
	}
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Destinatario objetoTipado = (Destinatario) objeto;
			objetosIguais = new EqualsBuilder()
				.append(cnpj,objetoTipado.cnpj)
				.append(cpf,objetoTipado.cpf)
				.append(idEstrangeiro,objetoTipado.idEstrangeiro)
				.append(razaoSocial,objetoTipado.razaoSocial)
				.append(nomeFantasia,objetoTipado.nomeFantasia)
				.append(endereco,objetoTipado.endereco)
				.append(telefone,objetoTipado.telefone)
				.append(indicadorIe,objetoTipado.indicadorIe)
				.append(ie,objetoTipado.ie)
				.append(inscricaoSuframa,objetoTipado.inscricaoSuframa)
				.append(email,objetoTipado.email)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(169,7723)
			.append(cnpj)
			.append(cpf)
			.append(idEstrangeiro)
			.append(razaoSocial)
			.append(nomeFantasia)
			.append(endereco)
			.append(telefone)
			.append(indicadorIe)
			.append(ie)
			.append(inscricaoSuframa)
			.append(email)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Destinatario [cnpj=" + cnpj
				+ ",cpf="+ cpf
				+ ",idEstrangeiro="+ idEstrangeiro
				+ ",razaoSocial="+ razaoSocial
				+ ",nomeFantasia="+ nomeFantasia
				+ ",endereco="+ endereco
				+ ",telefone="+ telefone
				+ ",IndicadorIe="+ indicadorIe
				+ ",ie="+ ie
				+ ",inscricaoSuframa="+ inscricaoSuframa
				+ ",email="+ email
				+ "]";
	} 
}
