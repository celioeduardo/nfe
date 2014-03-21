package com.hadrion.nfe.dominio.modelo.recepcao.consulta;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.Mensagem;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class ProtocoloNotaProcessada {
	
	private Date dataHoraProcessamento;
	private NumeroProtocolo numero;
	private Mensagem mensagem;
	private NotaFiscalId notaFiscalId;
	
	public ProtocoloNotaProcessada(Date dataHoraProcessamento,
			NumeroProtocolo numero, Mensagem mensagem,
			NotaFiscalId notaFiscalId) {
		super();
		this.dataHoraProcessamento = dataHoraProcessamento;
		this.numero = numero;
		this.mensagem = mensagem;
		this.notaFiscalId = notaFiscalId;
	}
	
	public boolean notaAutorizada(){
		return mensagem.codigo() == 100;
	}
	
	public boolean notaDenegada(){
		return mensagem.codigo() == 110;
	}
	
	
	public Date dataHoraProcessamento(){
		return this.dataHoraProcessamento;
	}
	
	public NumeroProtocolo numero(){
		return this.numero;
	}
	
	public Mensagem mensagem(){
		return this.mensagem;
	}
	
	public NotaFiscalId notaFiscalId(){
		return this.notaFiscalId;
	} 
	
}
