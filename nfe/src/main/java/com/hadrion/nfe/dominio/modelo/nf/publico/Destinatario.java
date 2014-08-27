package com.hadrion.nfe.dominio.modelo.nf.publico;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.Email;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Telefone;

public class Destinatario {
	//TODO - equals
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
		this.cnpj = cnpj;
		this.cpf = cpf;
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
}
