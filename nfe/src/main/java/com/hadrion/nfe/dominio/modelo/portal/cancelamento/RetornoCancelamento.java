package com.hadrion.nfe.dominio.modelo.portal.cancelamento;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;

public class RetornoCancelamento {
	
	private NumeroProtocolo numeroProtocolo;
	private Mensagem mensagem;
	private Date dataHoraProcessamento;
	
	public RetornoCancelamento(
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

	public boolean cancelamentoHomologado() {
		return mensagem.cancelamentoHomologado();
	}
	
}
