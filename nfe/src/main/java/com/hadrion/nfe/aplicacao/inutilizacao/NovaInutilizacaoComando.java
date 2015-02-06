package com.hadrion.nfe.aplicacao.inutilizacao;

import com.hadrion.nfe.dominio.modelo.Ambiente;

public class NovaInutilizacaoComando {
	
	private String inutilizacaoId; 
	private Ambiente ambiente;
	private int serie; 
	private long numeroInicial;
	private long numeroFinal; 
	private String justificativa; 
	
	private String filialId;

	public NovaInutilizacaoComando(String inutilizacaoId, Ambiente ambiente,
			int serie, long numeroInicial, long numeroFinal,
			String justificativa, String filialId) {
		super();
		this.inutilizacaoId = inutilizacaoId;
		this.ambiente = ambiente;
		this.serie = serie;
		this.numeroInicial = numeroInicial;
		this.numeroFinal = numeroFinal;
		this.justificativa = justificativa;
		this.filialId = filialId;
	}
	
	public NovaInutilizacaoComando() {
		super();
	}

	public String getInutilizacaoId() {
		return inutilizacaoId;
	}
	public Ambiente getAmbiente() {
		return ambiente;
	}
	public int getSerie() {
		return serie;
	}
	public long getNumeroInicial() {
		return numeroInicial;
	}
	public long getNumeroFinal() {
		return numeroFinal;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public String getFilialId() {
		return filialId;
	}

}
