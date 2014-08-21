package com.hadrion.nfe.dominio.modelo.nf.transporte;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEtadual;

public class Transportador {
	private Cnpj cnpj;
	private Cpf cpf;
	private String razaoSocial;
	private InscricaoEtadual ie;
	private Endereco endereco;
	
	public Transportador(Cnpj cnpj, Cpf cpf, String razaoSocial,
			InscricaoEtadual ie, Endereco endereco) {
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

	public InscricaoEtadual ie() {
		return ie;
	}

	public Endereco endereco() {
		return endereco;
	}
	
	
	
}
