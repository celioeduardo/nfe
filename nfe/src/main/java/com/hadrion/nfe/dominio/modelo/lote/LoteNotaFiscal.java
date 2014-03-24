package com.hadrion.nfe.dominio.modelo.lote;

import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominioPublicador;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.Mensagem;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalAutorizada;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalDenegada;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.recepcao.consulta.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.recepcao.consulta.ProtocoloNotaProcessada;

public class LoteNotaFiscal {
	private NotaFiscalId notaFiscalId;
	private Ambiente ambiente;
	private SituacaoLoteNotaFiscal situacao;
	private Date dataHoraProcessamento;
	private NumeroProtocolo numeroProtocolo;
	private Mensagem mensagem;
	
	public LoteNotaFiscal(NotaFiscalId notaFiscalId, 
			Ambiente ambiente) {
		super();
		this.notaFiscalId = notaFiscalId;
		this.situacao = SituacaoLoteNotaFiscal.NAO_PROCESSADA;
		this.ambiente = ambiente;
	}
	
	public NotaFiscalId notaFiscalId(){
		return notaFiscalId;
	}
	
	public boolean naoProcessada(){
		return this.situacao == SituacaoLoteNotaFiscal.NAO_PROCESSADA;
	}

	public void processar(ProtocoloNotaProcessada protocolo) {
		if (protocolo.notaAutorizada())
			this.autorizada(dataHoraProcessamento, numeroProtocolo, mensagem);
		else if(protocolo.notaDenegada())
			this.denegada(dataHoraProcessamento, mensagem);
		else
			this.rejeitada(dataHoraProcessamento, mensagem);
	}
	
	public boolean estaAutorizada(){
		return this.situacao == SituacaoLoteNotaFiscal.AUTORIZADA;
	}
	
	public boolean estaRejeitada(){
		return this.situacao == SituacaoLoteNotaFiscal.REJEITADA;
	}
	
	public boolean estaDenegada(){
		return this.situacao == SituacaoLoteNotaFiscal.DENEGADA;
	}
	
	private void autorizada(
			Date dataHoraProcessamento,
			NumeroProtocolo numeroProtocolo,
			Mensagem mensagem){
		this.setDataHoraProcessamento(dataHoraProcessamento);
		this.setNumeroProtocolo(numeroProtocolo);
		this.setMensagem(mensagem);
		this.mudarParaAutorizada();
		
		EventoDominioPublicador.instancia().
			publicar(new NotaFiscalAutorizada(
					notaFiscalId(),
					ambiente));
	}
	
	private void rejeitada(
			Date dataHoraProcessamento,
			Mensagem mensagem){
		
		this.setDataHoraProcessamento(dataHoraProcessamento);
		this.setMensagem(mensagem);
		this.mudarParaRejeitada();
	}
	
	private void denegada(
			Date dataHoraProcessamento,
			Mensagem mensagem){
		
		this.setDataHoraProcessamento(dataHoraProcessamento);
		this.setMensagem(mensagem);
		this.mudarParaDenegada();
		
		EventoDominioPublicador.instancia().
			publicar(new NotaFiscalDenegada(notaFiscalId(),ambiente));

	}
	
	private void mudarParaAutorizada(){
		if (situacao == SituacaoLoteNotaFiscal.DENEGADA)
			throw new UnsupportedOperationException(
					"Nota Fiscal DENEGADA não pode ser AUTORIZADA.");
		this.situacao = SituacaoLoteNotaFiscal.AUTORIZADA;
	}
	
	private void mudarParaRejeitada(){
		if (situacao == SituacaoLoteNotaFiscal.DENEGADA)
			throw new UnsupportedOperationException(
					"Nota Fiscal DENEGADA não pode ser REJEITADA.");
		
		if (situacao == SituacaoLoteNotaFiscal.AUTORIZADA)
			throw new UnsupportedOperationException(
					"Nota Fiscal AUTORIZADA não pode ser REJEITADA.");
		
		this.situacao = SituacaoLoteNotaFiscal.REJEITADA;
	}
	
	private void mudarParaDenegada(){
		if (situacao == SituacaoLoteNotaFiscal.AUTORIZADA)
			throw new UnsupportedOperationException(
					"Nota Fiscal AUTORIZADA não pode ser AUTORIZADA.");
		this.situacao = SituacaoLoteNotaFiscal.DENEGADA;
	}
	
	private void setDataHoraProcessamento(Date data){
		this.dataHoraProcessamento = data;
	}
	
	private void setNumeroProtocolo(NumeroProtocolo numeroProtocolo){
		this.numeroProtocolo = numeroProtocolo;
	}
	
	private void setMensagem(Mensagem mensagem){
		this.mensagem = mensagem;
	}
	
	
}
