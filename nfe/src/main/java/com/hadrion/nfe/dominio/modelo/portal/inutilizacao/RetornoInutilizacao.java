package com.hadrion.nfe.dominio.modelo.portal.inutilizacao;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;

public class RetornoInutilizacao {
	
	private NumeroProtocolo numeroProtocolo;
	private Mensagem mensagem;
	private Date dataHoraProcessamento;
	private String xmlRetorno;
	
	public RetornoInutilizacao(
			NumeroProtocolo numeroProtocolo, 
			Mensagem mensagem,
			Date dataHoraProcessamento,
			String xmlRetorno) {
		super();
		this.dataHoraProcessamento = dataHoraProcessamento;
		this.numeroProtocolo = numeroProtocolo;
		this.mensagem = mensagem;
		this.xmlRetorno = xmlRetorno;
	}
	public RetornoInutilizacao(
			NumeroProtocolo numeroProtocolo, 
			Mensagem mensagem,
			Date dataHoraProcessamento) {
		this(numeroProtocolo,mensagem,dataHoraProcessamento,null);
	}

	public Date dataHoraProcessamento(){
		return dataHoraProcessamento;
	}
	
	public NumeroProtocolo numeroProtocolo(){
		return numeroProtocolo;
	}
	
	public Mensagem mensagem(){
		return mensagem;
	}

	public boolean inutilizacaoHomologada() {
		return mensagem.inutilizacaoHomologada();
	}
	
	public String xmlRetorno(){
		return xmlRetorno;
	}
	
}
