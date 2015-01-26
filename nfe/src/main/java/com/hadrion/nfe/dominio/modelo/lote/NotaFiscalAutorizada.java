package com.hadrion.nfe.dominio.modelo.lote;

import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominio;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;

public class NotaFiscalAutorizada implements EventoDominio {
	
	private int versaoEvento;
	private Date ocorridoEm;
	private NotaFiscalId notaFiscalId;
	private ChaveAcesso chaveAcesso;
	private Ambiente ambiente;

	public NotaFiscalAutorizada(
			NotaFiscalId notaFiscalId,
			ChaveAcesso chaveAcesso,
			Ambiente ambiente) {
		this.ocorridoEm = new Date();
		this.versaoEvento = 1;
		this.notaFiscalId = notaFiscalId;
		this.chaveAcesso = chaveAcesso;
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
	
	public ChaveAcesso chaveAcesso(){
		return chaveAcesso;
	}
	
	public Ambiente ambiente(){
		return ambiente;
	}


}
