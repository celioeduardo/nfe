package com.hadrion.nfe.aplicacao.nf;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.filial.ModoOperacao;

public class AtualizarModoOperacaoComando {
	private String filialId;
	private ModoOperacao modoOperacao;
	private Date dataHoraContingencia;
	private String justificativaContingencia;
	
	public AtualizarModoOperacaoComando(String filialId,
			ModoOperacao modoOperacao, Date dataHoraContingencia,
			String justificativaContingencia) {
		super();
		this.filialId = filialId;
		this.modoOperacao = modoOperacao;
		this.dataHoraContingencia = dataHoraContingencia;
		this.justificativaContingencia = justificativaContingencia;
	}

	public AtualizarModoOperacaoComando() {
	}

	public String getFilialId() {
		return filialId;
	}
	public void setFilialId(String filialId) {
		this.filialId = filialId;
	}
	public ModoOperacao getModoOperacao() {
		return modoOperacao;
	}
	public void setModoOperacao(ModoOperacao modoOperacao) {
		this.modoOperacao = modoOperacao;
	}
	public Date getDataHoraContingencia() {
		return dataHoraContingencia;
	}
	public void setDataHoraContingencia(Date dataHoraContingencia) {
		this.dataHoraContingencia = dataHoraContingencia;
	}
	public String getJustificativaContingencia() {
		return justificativaContingencia;
	}
	public void setJustificativaContingencia(String justificativaContingencia) {
		this.justificativaContingencia = justificativaContingencia;
	}

	
	
}
