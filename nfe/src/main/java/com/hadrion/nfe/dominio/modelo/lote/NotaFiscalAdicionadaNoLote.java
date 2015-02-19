package com.hadrion.nfe.dominio.modelo.lote;

import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class NotaFiscalAdicionadaNoLote implements EventoDominio {
	
	private String notaFiscalId;
	private String loteId;
	private int versaoEvento;
	private Date ocorridoEm;
	
	public NotaFiscalAdicionadaNoLote(NotaFiscalId notaFiscalId, LoteId loteId) {
		super();
		this.notaFiscalId = notaFiscalId.id();
		this.loteId = loteId.id();
		this.versaoEvento = 1;
		this.ocorridoEm = new Date();
	}
	
	public String notaFiscalId(){
		return notaFiscalId;
	}
	
	public String loteId(){
		return loteId;
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
