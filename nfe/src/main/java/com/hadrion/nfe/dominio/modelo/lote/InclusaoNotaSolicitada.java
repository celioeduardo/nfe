package com.hadrion.nfe.dominio.modelo.lote;

import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class InclusaoNotaSolicitada implements EventoDominio {

	private int versaoEvento;
	private Date ocorridoEm;
	private LoteId loteId;
	private NotaFiscalId notaFiscalId;

	public InclusaoNotaSolicitada(LoteId loteId, NotaFiscalId notaFiscalId) {
		this.ocorridoEm = new Date();
		this.versaoEvento = 1;

		this.loteId = loteId;
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
	
	public LoteId loteId(){
		return loteId;
	}
	
	public NotaFiscalId notaFiscalId(){
		return notaFiscalId;
	}

}
