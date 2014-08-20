package com.hadrion.nfe.dominio.modelo.nf.publico;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEtadual;
import com.hadrion.nfe.tipos.Telefone;

public class Emitente {
	//TODO - equals
	private Cnpj cnpj;
	private Cpf cpf;
	private String razaoSocial;
	private String nomeFantasia;
	private Endereco endereco;
	private Telefone telefone;
	private InscricaoEtadual ie;
	private InscricaoEtadual ieSubstituto;
	
	public Emitente(Cnpj cnpj, Cpf cpf, String razaoSocial,
			String nomeFantasia, Endereco endereco, Telefone telefone,
			InscricaoEtadual ie, InscricaoEtadual ieSubstituto) {
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

	public InscricaoEtadual ie() {
		return ie;
	}

	public InscricaoEtadual ieSubstituto() {
		return ieSubstituto;
	}
}
