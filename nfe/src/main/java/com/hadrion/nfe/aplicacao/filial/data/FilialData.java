package com.hadrion.nfe.aplicacao.filial.data;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.ModoOperacao;


public class FilialData {
	private String filialId;
	private String nome;
	private String cnpj;
	private String empresaId;
	private ModoOperacao modoOperacao;
	private Ambiente ambiente;
	private Date dataHoraContingencia;
	private String justificativaContingencia;
	
	public FilialData(String filialId, String nome, String cnpj,
			String empresaId, ModoOperacao modoOperacao, Ambiente ambiente,
			Date dataHoraContingencia, String justificativaContingencia) {
		super();
		this.filialId = filialId;
		this.nome = nome;
		this.cnpj = cnpj;
		this.empresaId = empresaId;
		this.modoOperacao = modoOperacao;
		this.ambiente = ambiente;
		this.dataHoraContingencia = dataHoraContingencia;
		this.justificativaContingencia = justificativaContingencia;
	}
	public String getFilialId() {
		return filialId;
	}
	public String getNome() {
		return nome;
	}
	public String getCnpj() {
		return cnpj;
	}
	public String getEmpresaId() {
		return empresaId;
	}
	public ModoOperacao getModoOperacao() {
		return modoOperacao;
	}
	public Ambiente getAmbiente() {
		return ambiente;
	}
	public Date getDataHoraContingencia() {
		return dataHoraContingencia;
	}
	public String getJustificativaContingencia() {
		return justificativaContingencia;
	}

}
