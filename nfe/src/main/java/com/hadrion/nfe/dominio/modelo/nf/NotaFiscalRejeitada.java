package com.hadrion.nfe.dominio.modelo.nf;

import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominio;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;

public class NotaFiscalRejeitada implements EventoDominio {
	private NotaFiscalId notaFiscalId;
	private Mensagem mensagem;
	
	private int versaoEvento;
	private Date ocorridoEm;
	
	public NotaFiscalRejeitada() {
		super();
	}

	public NotaFiscalRejeitada(
			NotaFiscalId notaFiscalId, Mensagem mensagem) {
		super();
		this.notaFiscalId = notaFiscalId;
		this.mensagem = mensagem;
		this.versaoEvento = 1;
		this.ocorridoEm = new Date();
	}

	public NotaFiscalId notaFiscalId(){
		return notaFiscalId;
	}
	
	public Mensagem mensagem(){
		return mensagem;
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
