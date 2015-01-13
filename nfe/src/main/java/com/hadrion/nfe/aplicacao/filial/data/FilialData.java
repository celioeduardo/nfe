package com.hadrion.nfe.aplicacao.filial.data;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.ModoOperacao;


public class FilialData {
	private String filialId;
	private String nome;
	private String cnpj;
	private String empresaId;
	private ModoOperacao modoOperacao;
	private Ambiente ambiente;

	public FilialData(String filialId, String nome, String cnpj,
			String empresaId, ModoOperacao modoOperacao, Ambiente ambiente) {
		super();
		this.filialId = filialId;
		this.nome = nome;
		this.cnpj = cnpj;
		this.empresaId = empresaId;
		this.modoOperacao = modoOperacao;
		this.ambiente = ambiente;
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

	public ModoOperacao getModoOperacao() {
		return modoOperacao;
	}

	public void setModoOperacao(ModoOperacao modoOperacao) {
		this.modoOperacao = modoOperacao;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public void setEmpresaId(String empresaId) {
		this.empresaId = empresaId;
	}
	

}
