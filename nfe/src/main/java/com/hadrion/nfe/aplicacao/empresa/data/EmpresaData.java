package com.hadrion.nfe.aplicacao.empresa.data;


public class EmpresaData {
	private String empresaId;
	private String nome;
	private String cnpj;
	
	public EmpresaData(String empresaId, String nome, String cnpj) {
		super();
		this.empresaId = empresaId;
		this.nome = nome;
		this.cnpj = cnpj;
	}
	public String getEmpresaId() {
		return empresaId;
	}
	public void setEmpresaId(String empresaId) {
		this.empresaId = empresaId;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

}
