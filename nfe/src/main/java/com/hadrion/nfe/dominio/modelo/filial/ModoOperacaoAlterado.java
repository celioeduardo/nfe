package com.hadrion.nfe.dominio.modelo.filial;

import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominio;

public class ModoOperacaoAlterado implements EventoDominio {
	
	private String filialId;
	private ModoOperacao modoOperacao;
	private Date dataHoraContingencia;
	private String justificativaContingencia;
	
	private int versaoEvento;
	private Date ocorridoEm;
	
	public ModoOperacaoAlterado() {
		super();
	}

	public ModoOperacaoAlterado(
			String filialId, ModoOperacao modoOperacao,
			Date dataHoraContingencia, 
			String justificativaContingencia) {
		super();
		this.filialId = filialId;
		this.modoOperacao = modoOperacao;
		this.dataHoraContingencia = dataHoraContingencia;
		this.justificativaContingencia = justificativaContingencia;
		this.versaoEvento = 1;
		this.ocorridoEm = new Date();
	}
	
	public String filialId() {
		return filialId;
	}

	public ModoOperacao modoOperacao() {
		return modoOperacao;
	}

	public Date dataHoraContingencia() {
		return dataHoraContingencia;
	}

	public String justificativaContingencia() {
		return justificativaContingencia;
	}

	@Override
	public int versaoEvento() {
		return versaoEvento;
	}

	@Override
	public Date ocorridoEm() {
		return ocorridoEm;
	}
	
}
