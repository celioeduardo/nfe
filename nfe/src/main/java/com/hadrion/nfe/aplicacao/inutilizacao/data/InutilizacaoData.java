package com.hadrion.nfe.aplicacao.inutilizacao.data;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.Ambiente;

public class InutilizacaoData {
	
	private String inutilizacaoId;
	private Ambiente ambiente;
	private String serie;
	private Long numeroInicial;
	private Long numeroFinal;
	private String justificativa;
	private Date dataHoraHomologacao;
	private String numeroProtocolo;
	private Integer msgCodigo;
	private String msgMensagem;

	public String getInutilizacaoId() {
		return inutilizacaoId;
	}
	public Ambiente getAmbiente() {
		return ambiente;
	}
	public String getSerie() {
		return serie;
	}
	public Long getNumeroInicial() {
		return numeroInicial;
	}
	public Long getNumeroFinal() {
		return numeroFinal;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public Date getDataHoraHomologacao() {
		return dataHoraHomologacao;
	}
	public String getProtocolo() {
		return numeroProtocolo;
	}
	
	public Integer getMsgCodigo() {
		return msgCodigo;
	}
	public String getMsgMensagem() {
		return msgMensagem;
	}
	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}
	
	public InutilizacaoData(String inutilizacaoId, Ambiente ambiente,
			String serie, Long numeroInicial, Long numeroFinal,
			String justificativa, Date dataHoraHomologacao,
			String numeroProtocolo, Integer msgCodigo, String msgMensagem) {
		super();
		this.inutilizacaoId = inutilizacaoId;
		this.ambiente = ambiente;
		this.serie = serie;
		this.numeroInicial = numeroInicial;
		this.numeroFinal = numeroFinal;
		this.justificativa = justificativa;
		this.dataHoraHomologacao = dataHoraHomologacao;
		this.numeroProtocolo = numeroProtocolo;
		this.msgCodigo = msgCodigo;
		this.msgMensagem = msgMensagem;
	}
	
}
