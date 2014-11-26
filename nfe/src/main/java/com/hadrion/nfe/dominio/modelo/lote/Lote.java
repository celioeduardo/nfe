package com.hadrion.nfe.dominio.modelo.lote;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioRegistro;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.MensagemSefaz;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.consulta.ProtocoloNotaProcessada;
import com.hadrion.nfe.port.adapters.portal.ws.Local;

public class Lote {
	
	private LoteId loteId;
	private Set<LoteNotaFiscal> notas;
	private SituacaoLote situacao;
	private NumeroReciboLote numeroRecibo;
	private Mensagem mensagemErro;
	private Mensagem mensagemProcessamento;
	private MensagemSefaz mensagemSefaz;
	private Ambiente ambiente;
	private Local local;
	
	public int quantidadeNotas() {
		return notas.size();
	}

	protected Lote(){
		
	}
	
	static Lote gerarEmHomologacao(NotaFiscal nota) {
		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		notas.add(nota);
		return gerarEmHomologacao(notas);
	}
	
	static Lote gerarEmHomologacao(Set<NotaFiscal> lista) {
		
		return new Lote(
				DominioRegistro.loteRepositorio().proximaIdentidade(),
				lista,
				Ambiente.HOMOLOGACAO);
	}
	
	static Lote gerarEmProducao(NotaFiscal nota) {
		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		notas.add(nota);
		return gerarEmProducao(notas);
	}
	
	static Lote gerarEmProducao(Set<NotaFiscal> lista) {
		return new Lote(
				DominioRegistro.loteRepositorio().proximaIdentidade(),
				lista,
				Ambiente.PRODUCAO);
	}
	
	private Lote(
			LoteId loteId,
			Set<NotaFiscal> notas,
			Ambiente ambiente){
		
		this.loteId = loteId;
		this.situacao = SituacaoLote.NAO_ENVIADO;
		this.notas = new HashSet<LoteNotaFiscal>();
		this.ambiente = ambiente;
		this.local = definirLocal(notas);
		for (NotaFiscal notaFiscal : notas)
			this.notas.add(
					new LoteNotaFiscal(notaFiscal,ambiente));
	}
	
	private Local definirLocal(Set<NotaFiscal> notas){
		Local local = null;
		for (NotaFiscal nf : notas) {
			if (local == null)
				local = Local.obter(nf.tipoEmissao(), nf.ufEmitente());
			else if (local != Local.obter(nf.tipoEmissao(), nf.ufEmitente()))
				throw new IllegalArgumentException(
						"Todas as Notas Fiscais tem que ter como destino o mesmo local.");
		}
		return local;
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

	private void processando() {
		assertLoteNaoEnviado();
		situacao = SituacaoLote.PROCESSANDO;
	}

	public boolean estaProcessando() {
		return situacao == SituacaoLote.PROCESSANDO;
	}

	public NumeroReciboLote numeroRecibo() {
		return this.numeroRecibo;
	}
	
	public void transmitido(NumeroReciboLote numeroRecibo){
		this.numeroRecibo = numeroRecibo;
		processando();
	}
	
	public void inconsistente(Mensagem erro){
		this.mensagemErro = erro;
		this.falhaConsistencia();
	}
	
	private void falhaConsistencia(){
		if (situacao != SituacaoLote.NAO_ENVIADO && 
			situacao != SituacaoLote.PROCESSANDO)
			throw new UnsupportedOperationException(
					"Lote não pode ser definida para Falha Consistência."
					+ "Situação é diferente de Não Enviado e Processando"); 
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

	public void processado(
			Mensagem mensagem, 
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
		
		LoteNotaFiscal loteNotaFiscal = loteNotaFiscal(protocolo.notaFiscalId());
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

	public Ambiente ambiente() {
		return ambiente;
	}

	public boolean comErroTransmissao() {
		return situacao == SituacaoLote.ERRO_TRANSMISSAO;
	}

	public void erroTransmissao(Mensagem erro) {
		this.mensagemErro = erro;
		if (situacao != SituacaoLote.NAO_ENVIADO)
			throw new UnsupportedOperationException(
					"Lote não pode ser definido para Erro de Transmissão."
					+ "Situação é diferente de Não Enviado."); 
		this.situacao = SituacaoLote.ERRO_TRANSMISSAO;
	}

	public Local local() {
		return local;
	}
}
