package com.hadrion.nfe.dominio.modelo.cancelamento;

import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominio;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class CancelamentoHomologado implements EventoDominio {

	private NotaFiscalId notaFiscalId;
	private Ambiente ambiente;
	private int versaoEvento;
	private Date ocorridoEm;
	
	public CancelamentoHomologado(NotaFiscalId notaFiscalId, Ambiente ambiente) {
		super();
		this.notaFiscalId = notaFiscalId;
		this.ambiente = ambiente;
		this.versaoEvento = 1;
		this.ocorridoEm = new Date();
	}
	
	public NotaFiscalId notaFiscalId(){
		return notaFiscalId;
	}
	
	public Ambiente ambiente(){
		return ambiente;
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
