package com.hadrion.nfe.aplicacao.filial.data;


public class FilialData {
	private String filialId;
	private String nome;
	private String cnpj;
	private String empresaId;

	public FilialData(String filialId, String nome, String cnpj,
			String empresaId) {
		super();
		this.filialId = filialId;
		this.nome = nome;
		this.cnpj = cnpj;
		this.empresaId = empresaId;
	}

	public String getFilialId() {
		return filialId;
	}

	public void setFilialId(String filialId) {
		this.filialId = filialId;
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

	public String getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(String empresaId) {
		this.empresaId = empresaId;
	}
	

}
