package com.hadrion.nfe.dominio.modelo.lote;

import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominio;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;

public class AutorizacaoNotaFiscalRecebida implements EventoDominio {
	
	private int versaoEvento;
	private Date ocorridoEm;
	private NotaFiscalId notaFiscalId;
	private Ambiente ambiente;
	private String numeroProtocolo;
	private String xmlProtocolo;
	private Mensagem mensagem;

	public AutorizacaoNotaFiscalRecebida(
			Ambiente ambiente,
			NotaFiscalId notaFiscalId,
			NumeroProtocolo numeroProtocolo,
			Mensagem mensagem,
			String xml) {
		this.ocorridoEm = new Date();
		this.versaoEvento = 1;
		this.ambiente = ambiente;
		this.notaFiscalId = notaFiscalId;
		this.numeroProtocolo = numeroProtocolo != null ? numeroProtocolo.numero() : null;
		this.mensagem = mensagem;
		this.xmlProtocolo = xml;
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
	
	public String numeroProtocolo(){
		return numeroProtocolo;
	}
	
	public String xmlProtocolo(){
		return xmlProtocolo;
	}
	
	public Mensagem mensagem(){
		return mensagem;
	}
	
}
