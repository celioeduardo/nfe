package com.hadrion.nfe.dominio.modelo.portal.inutilizacao;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;

public class RetornoInutilizacao {
	
	private NumeroProtocolo numeroProtocolo;
	private Mensagem mensagem;
	private Date dataHoraProcessamento;
	
	public RetornoInutilizacao(
			NumeroProtocolo numeroProtocolo, 
			Mensagem mensagem,
			Date dataHoraProcessamento) {
		super();
		this.dataHoraProcessamento = dataHoraProcessamento;
		this.numeroProtocolo = numeroProtocolo;
		this.mensagem = mensagem;
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
	
}
