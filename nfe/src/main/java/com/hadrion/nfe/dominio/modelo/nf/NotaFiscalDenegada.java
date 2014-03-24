package com.hadrion.nfe.dominio.modelo.nf;

import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominio;

public class NotaFiscalDenegada implements EventoDominio {
	
	private int versaoEvento;
	private Date ocorridoEm;
	private NotaFiscalId notaFiscalId;

	public NotaFiscalDenegada(NotaFiscalId notaFiscalId) {
		this.ocorridoEm = new Date();
		this.versaoEvento = 1;
		this.notaFiscalId = notaFiscalId;
	}

	@Override
	public int versaoEvento() {
		return versaoEvento;
	}

	@Override
	public Date ocorridoEm() {
		return ocorridoEm;
	}
	
	public NotaFiscalId notaFiscalId(){
		return notaFiscalId;
	}
}