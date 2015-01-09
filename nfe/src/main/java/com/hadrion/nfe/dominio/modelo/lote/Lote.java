package com.hadrion.nfe.dominio.modelo.lote;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioRegistro;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRejeitada;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.MensagemSefaz;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.ProtocoloNotaProcessada;
import com.hadrion.nfe.port.adapters.portal.ws.Local;

@Entity
@SequenceGenerator(name="SEQ", sequenceName="SQ_LOTE")
@Table(name="LOTE")
public class Lote {
	@Embedded
	private LoteId loteId;	
	
	@Embedded
	private EmpresaId empresaId;
	
	@Embedded
	private FilialId filialId;
	
	@Embedded
	private NotistaId notistaId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="SITUACAO")
	private SituacaoLote situacao;
	
	@Embedded
	private NumeroReciboLote numeroRecibo;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="codigo", column=@Column(name="MSG_ERRO_COD")),
		@AttributeOverride(name="descricao", column=@Column(name="MSG_ERRO_DSC"))
	})
	private Mensagem mensagemErro;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="codigo", column=@Column(name="MSG_PROC_COD")),
		@AttributeOverride(name="descricao", column=@Column(name="MSG_PROC_DSC"))
	})
	private Mensagem mensagemProcessamento;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="codigo", column=@Column(name="MSG_SEFAZ_COD")),
		@AttributeOverride(name="descricao", column=@Column(name="MSG_SEFAZ_DSC"))
	})
	private MensagemSefaz mensagemSefaz;

	@Enumerated(EnumType.STRING)
	@Column(name="AMBIENTE")
	private Ambiente ambiente;

	@Enumerated(EnumType.STRING)
	@Column(name="LOCAL")
	private Local local;

	@Enumerated(EnumType.STRING)
	@Column(name="UF")
	private Uf uf;
	
	@OneToMany(orphanRemoval=true,cascade=CascadeType.ALL)
	@JoinColumn(name="ID_LOTE")
	private Set<LoteNotaFiscal> notas;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
	@Version
    @Column(name="VERSAO")
    private int versaoConcorrencia;
	
	public int quantidadeNotas() {
		return notas.size();
	}

	protected Lote(){
		
	}
	
	static Lote gerarEmHomologacao(NotaFiscal nota, EmpresaId empresaId) {
		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		notas.add(nota);
		return gerarEmHomologacao(notas, empresaId);
	}
	
	static Lote gerarEmHomologacao(Set<NotaFiscal> lista, EmpresaId empresaId) {
		
		return new Lote(
				DominioRegistro.loteRepositorio().proximaIdentidade(),
				lista,
				Ambiente.HOMOLOGACAO,
				empresaId);
	}
	
	static Lote gerarEmProducao(NotaFiscal nota, EmpresaId empresaId) {
		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		notas.add(nota);
		return gerarEmProducao(notas,empresaId);
	}
	
	static Lote gerarEmProducao(Set<NotaFiscal> lista, EmpresaId empresaId) {
		return new Lote(
				DominioRegistro.loteRepositorio().proximaIdentidade(),
				lista,
				Ambiente.PRODUCAO,
				empresaId);
	}
	
	private Lote(
			LoteId loteId,
			Set<NotaFiscal> notas,
			Ambiente ambiente,
			EmpresaId empresaId){
		
		this.loteId = loteId;
		this.situacao = SituacaoLote.NAO_ENVIADO;
		this.notas = new HashSet<LoteNotaFiscal>();
		this.ambiente = ambiente;
		this.empresaId = empresaId;
		this.filialId = definirFilial(notas);
		this.notistaId = definirNotista(notas);
		this.local = definirLocal(notas);
		this.uf = definirUf(notas);
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
	
	private FilialId definirFilial(Set<NotaFiscal> notas){
		FilialId filialId = null;
		for (NotaFiscal nf : notas) {
			if (filialId == null)
				filialId = nf.filialId();
			else if (!filialId.equals(nf.filialId()))
				return null;
		}
		return filialId;
	}
	private NotistaId definirNotista(Set<NotaFiscal> notas){
		NotistaId notistaId = null;
		for (NotaFiscal nf : notas) {
			if (notistaId == null)
				notistaId = nf.notistaId();
			else if (!notistaId.equals(nf.notistaId()))
				return null;
		}
		return notistaId;
	}
	
	private Uf definirUf(Set<NotaFiscal> notas){
		return notas.iterator().next().ufEmitente();
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
		return String.valueOf(id);
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
		dispararEventoNotasRejeitadas(erro);
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
			MensagemSefaz mensagemSefaz) {
		
		this.setMensagemProcessamento(mensagem);
		this.setMensagemSefaz(mensagemSefaz);
		
		this.mudarParaProcessado();
	}
	
	private void mudarParaProcessado(){
		this.situacao = SituacaoLote.PROCESSADO;
	}
	
	void processarNotaPeloProtocolo(
			NotaFiscalId notaFiscalId,
			ProtocoloNotaProcessada protocolo){
		
		LoteNotaFiscal loteNotaFiscal = loteNotaFiscal(notaFiscalId);
		if (loteNotaFiscal != null)
			loteNotaFiscal.processar(protocolo);
	}

	private LoteNotaFiscal loteNotaFiscal(NotaFiscalId notaFiscalId){
		for (LoteNotaFiscal loteNotaFiscal : getNotas()) {
			if (loteNotaFiscal.notaFiscalId().equals(notaFiscalId))
				return loteNotaFiscal;
		}
		return null;
	}
	
	private Set<LoteNotaFiscal> getNotas(){
		return notas;
	}
	
	public Set<NotaFiscalId> notas(){
		Set<NotaFiscalId> result = new HashSet<NotaFiscalId>();
		
		for (LoteNotaFiscal loteNf : getNotas()) 
			result.add(loteNf.notaFiscalId());
		
		return result;
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
		
		dispararEventoNotasRejeitadas(erro);
	}
	
	private void dispararEventoNotasRejeitadas(Mensagem mensagem){
		for (LoteNotaFiscal nota : getNotas()) {
			DominioRegistro.eventoDominioPublicador()
				.publicar(new NotaFiscalRejeitada(nota.notaFiscalId(), mensagem));
		}
	}

	public Local local() {
		return local;
	}

	public EmpresaId empresaId() {
		return empresaId;
	}

	public Uf uf() {
		return uf;
	}
}
