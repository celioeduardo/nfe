package com.hadrion.nfe.dominio.modelo.lote;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioRegistro;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRejeitada;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.ProtocoloNotaProcessada;

@Entity
@SequenceGenerator(name="SEQ", sequenceName="SQ_LOTE_NF")
@Table(name="LOTE_NF")
class LoteNotaFiscal {
	
	@Embedded
	private NotaFiscalId notaFiscalId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="AMBIENTE")
	private Ambiente ambiente;
	
	@Enumerated(EnumType.STRING)
	@Column(name="SITUACAO")
	private SituacaoLoteNotaFiscal situacao;
	
	@Column(name="DATA_HORA_PROC")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraProcessamento;
	
	@Embedded
	private NumeroProtocolo numeroProtocolo;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="codigo", column=@Column(name="MSG_COD")),
		@AttributeOverride(name="descricao", column=@Column(name="MSG_DSC"))
	})
	private Mensagem mensagem;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
	LoteNotaFiscal(NotaFiscal notaFiscal, 
			Ambiente ambiente) {
		super();
		
		if (notaFiscal == null)
			throw new IllegalArgumentException(
					"Nota Fiscal não pode ser nula.");
		
		if (!notaFiscal.pendenteDeTransmissao())
			throw new IllegalArgumentException(
					"Nota Fiscal "+notaFiscal.notaFiscalId()+
					" não está Pendente de Transmissão.");
		
		this.notaFiscalId = notaFiscal.notaFiscalId();
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
			this.autorizada(
					protocolo.dataHoraProcessamento(), 
					protocolo.numero(), 
					protocolo.mensagem(),
					protocolo.xml());
		else if(protocolo.notaDenegada())
			this.denegada(
					protocolo.dataHoraProcessamento(), 
					protocolo.mensagem());
		else
			this.rejeitada(
					protocolo.dataHoraProcessamento(), 
					protocolo.mensagem());
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
	
	void autorizada(
			Date dataHoraProcessamento,
			NumeroProtocolo numeroProtocolo,
			Mensagem mensagem,
			String xml){
		
		this.setDataHoraProcessamento(dataHoraProcessamento);
		this.setNumeroProtocolo(numeroProtocolo);
		this.setMensagem(mensagem);
		this.mudarParaAutorizada();
		
		DominioRegistro.eventoDominioPublicador().
			publicar(new AutorizacaoNotaFiscalRecebida(
					ambiente,
					notaFiscalId(),
					numeroProtocolo,
					mensagem,
					dataHoraProcessamento,
					xml));
	}
	
	void rejeitada(
			Date dataHoraProcessamento,
			Mensagem mensagem){
		
		this.setDataHoraProcessamento(dataHoraProcessamento);
		this.setMensagem(mensagem);
		this.mudarParaRejeitada();
		
		DominioRegistro.eventoDominioPublicador().
			publicar(new NotaFiscalRejeitada(notaFiscalId(),mensagem));
	}
	
	void denegada(
			Date dataHoraProcessamento,
			Mensagem mensagem){
		
		this.setDataHoraProcessamento(dataHoraProcessamento);
		this.setMensagem(mensagem);
		this.mudarParaDenegada();
		
		DominioRegistro.eventoDominioPublicador().
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
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private LoteNotaFiscal(){}
	
}
