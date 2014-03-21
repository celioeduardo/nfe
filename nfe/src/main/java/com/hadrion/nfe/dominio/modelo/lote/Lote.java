package com.hadrion.nfe.dominio.modelo.lote;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hadrion.nfe.dominio.modelo.Mensagem;
import com.hadrion.nfe.dominio.modelo.MensagemSefaz;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.recepcao.consulta.ProtocoloNotaProcessada;

public class Lote {
	
	private LoteId loteId;
	private Set<LoteNotaFiscal> notas;
	private SituacaoLote situacao;
	private NumeroReciboLote numeroRecibo;
	private Mensagem mensagemErro;
	private Mensagem mensagemProcessamento;
	private MensagemSefaz mensagemSefaz;
	
	public int quantidadeNotas() {
		return notas.size();
	}

	protected Lote(){
		
	}

	public static Lote gerar(Set<NotaFiscalId> lista,
			LoteRepositorio loteRepositorio) {
		
		Lote lote = new Lote();
		lote.loteId = loteRepositorio.proximaIdentidade();
		lote.situacao = SituacaoLote.NAO_ENVIADO;
		lote.notas = new HashSet<LoteNotaFiscal>();
		
		for (NotaFiscalId notaFiscalId : lista){
			lote.notas.add(new LoteNotaFiscal(notaFiscalId));
		}
		
		return lote;
	}

	public LoteId loteId(){
		return loteId;
	}
	
	public Mensagem mensagemProcessamento(){
		return mensagemProcessamento;
	}
	
	public MensagemSefaz mensagemSefaz(){
		return mensagemSefaz;
	}
	
	public void cancelar() {
		if (situacao != SituacaoLote.NAO_ENVIADO)
			throw new UnsupportedOperationException(
					"Não é permitido cancelar um lote com situação: "+situacao);
		situacao = SituacaoLote.CANCELADO;
	}
	public void transmitido() {
		if (situacao != SituacaoLote.NAO_ENVIADO)
			throw new UnsupportedOperationException(
					"Não é permitido cancelar um lote com situação: "+situacao);
		situacao = SituacaoLote.CANCELADO;
	}
	
	public boolean temNota(NotaFiscalId notaFiscalId){
		for (LoteNotaFiscal loteNotaFiscal: notas) {
			if (loteNotaFiscal.notaFiscalId().equals(notaFiscalId))
				return true;
		}
		return false;
	}

	public boolean estaNaoEnviado() {
		return situacao == SituacaoLote.NAO_ENVIADO;
	}

	public String numero() {
		// TODO criar numero do lote
		return loteId.id();
	}

	private void emProcessamento() {
		assertLoteNaoEnviado();
		situacao = SituacaoLote.EM_PROCESSAMENTO;
	}

	public boolean estaEmProcessamento() {
		return situacao == SituacaoLote.EM_PROCESSAMENTO;
	}

	public NumeroReciboLote numeroRecibo() {
		return this.numeroRecibo;
	}
	
	public void recebido(NumeroReciboLote numeroRecibo){
		this.numeroRecibo = numeroRecibo;
		emProcessamento();
	}
	
	public void inconsistente(Mensagem erro){
		this.mensagemErro = erro;
		this.falhaConsistencia();
	}
	
	private void falhaConsistencia(){
		if (situacao != SituacaoLote.NAO_ENVIADO && 
			situacao != SituacaoLote.EM_PROCESSAMENTO)
			throw new UnsupportedOperationException(
					"Lote não pode ser definida para Falha Consistência."
					+ "Situação é diferente de Não Enviado e Em Processamento"); 
		this.situacao = SituacaoLote.FALHA_CONSISTENCIA;
	}

	public boolean estaInconsistente() {
		return situacao == SituacaoLote.FALHA_CONSISTENCIA;
	}
	
	private void assertLoteNaoEnviado(){
		if (situacao != SituacaoLote.NAO_ENVIADO)
			throw new UnsupportedOperationException(
					"Situação do Lote é diferente de Não Enviado"); 
	}
	
	public Mensagem mensagemErro(){
		return mensagemErro;
	}

	public boolean estaProcessado() {
		return situacao == SituacaoLote.PROCESSADO;
	}

	public void processado(Mensagem mensagem, 
			MensagemSefaz mensagemSefaz, 
			List<ProtocoloNotaProcessada> protocolos) {
		this.setMensagemProcessamento(mensagem);
		this.setMensagemSefaz(mensagemSefaz);
		for (ProtocoloNotaProcessada protocolo : protocolos) 
			this.processarNotaPeloProtocolo(protocolo);
		this.mudarParaProcessado();
	}
	
	private void mudarParaProcessado(){
		this.situacao = SituacaoLote.PROCESSADO;
	}
	
	private void processarNotaPeloProtocolo(
			ProtocoloNotaProcessada protocolo){
		
		LoteNotaFiscal loteNotaFiscal = 
				loteNotaFiscal(protocolo.notaFiscalId());
		if (loteNotaFiscal != null)
			loteNotaFiscal.processar(protocolo);
	}
	
	private LoteNotaFiscal loteNotaFiscal(NotaFiscalId notaFiscalId){
		for (LoteNotaFiscal loteNotaFiscal : notas()) {
			if (loteNotaFiscal.notaFiscalId().equals(notaFiscalId))
				return loteNotaFiscal;
		}
		return null;
	}
	
	private Set<LoteNotaFiscal> notas(){
		return notas;
	}
	
	private void setMensagemProcessamento(Mensagem mensagem){
		this.mensagemProcessamento = mensagem;
	}
	
	private void setMensagemSefaz(MensagemSefaz mensagem){
		this.mensagemSefaz = mensagem;
	}

	public boolean estaAutorizada(NotaFiscalId notaFiscalId) {
		LoteNotaFiscal result = loteNotaFiscal(notaFiscalId);
		return result != null && result.estaAutorizada();
	}
	public boolean estaRejeitada(NotaFiscalId notaFiscalId) {
		LoteNotaFiscal result = loteNotaFiscal(notaFiscalId);
		return result != null && result.estaRejeitada();
	}
	public boolean estaDenegada(NotaFiscalId notaFiscalId) {
		LoteNotaFiscal result = loteNotaFiscal(notaFiscalId);
		return result != null && result.estaDenegada();
	}
}
