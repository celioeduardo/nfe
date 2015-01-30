package com.hadrion.nfe.dominio.modelo.cancelamento;


import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class CancelamentoHomologado implements EventoDominio {

	private NotaFiscalId notaFiscalId;
	
	private int versaoEvento;
	private Date ocorridoEm;
	
	public CancelamentoHomologado(NotaFiscalId notaFiscalId) {
		super();
		this.notaFiscalId = notaFiscalId;
		this.versaoEvento = 1;
		this.ocorridoEm = new Date();
	}
	
	public NotaFiscalId notaFiscalId(){
		return notaFiscalId;
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
