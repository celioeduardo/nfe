package com.hadrion.nfe.dominio.modelo.nf;

import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominio;
import com.hadrion.nfe.dominio.modelo.Ambiente;

public class NotaFiscalAutorizada implements EventoDominio {
	
	private int versaoEvento;
	private Date ocorridoEm;
	private NotaFiscalId notaFiscalId;
	private Ambiente ambiente;

	public NotaFiscalAutorizada(
			NotaFiscalId notaFiscalId,
			Ambiente ambiente) {
		this.ocorridoEm = new Date();
		this.versaoEvento = 1;
		this.notaFiscalId = notaFiscalId;
		this.ambiente = ambiente;
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
	
	public Ambiente ambiente(){
		return ambiente;
	}
}
