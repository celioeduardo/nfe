package com.hadrion.nfe.dominio.modelo.cancelamento;


import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominio;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class CancelamentoHomologado implements EventoDominio {

	private NotaFiscalId notaFiscalId;
	
	private int versaoEvento;
	private Date ocorridoEm;
	private FilialId filialId;
	
	public CancelamentoHomologado(NotaFiscalId notaFiscalId,FilialId filialId) {
		super();
		this.notaFiscalId = notaFiscalId;
		this.versaoEvento = 1;
		this.ocorridoEm = new Date();
		this.filialId = filialId;
	}
	
	public NotaFiscalId notaFiscalId(){
		return notaFiscalId;
	}

	public FilialId filialId(){
		return filialId;
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
