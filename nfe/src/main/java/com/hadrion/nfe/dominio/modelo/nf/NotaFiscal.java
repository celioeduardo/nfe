package com.hadrion.nfe.dominio.modelo.nf;

import static com.hadrion.util.NumeroUtil.randInt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioRegistro;
import com.hadrion.nfe.dominio.modelo.cancelamento.CancelamentoHomologado;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.filial.ModoOperacao;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.lote.NotaFiscalAutorizada;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Cobranca;
import com.hadrion.nfe.dominio.modelo.nf.informacao.Informacao;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalEntrega;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalRetirada;
import com.hadrion.nfe.dominio.modelo.nf.publico.Destinatario;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.tipos.Dinheiro;

@Entity
@SequenceGenerator(name="SEQ",sequenceName="SQ_NF")
@Table(name="NF")
public class NotaFiscal {
	@Enumerated(EnumType.STRING)
	private Ambiente ambiente;
	
	@Embedded
	private NotaFiscalId notaFiscalId;
	
	@Embedded
	private ChaveAcesso chaveAcesso;
	
	@Enumerated(EnumType.STRING)
	@Column(name="SITUACAO")
	private Situacao situacao;
	
	@Column(name="NATUREZA_OPERACAO")
	private String naturezaOperacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name="FORMA_PAGAMENTO")
	private FormaPagamento formaPagamento;
	
	@Embedded
	private Modelo modelo;
	
	@Embedded
	private Serie serie;
	
	@Column(name="NUMERO")
	private Long numero;
	
	@Column(name="EMISSAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date emissao;
	
	@Column(name="DATA_HORA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHora;
	
	@Column(name="CODIGO_NUMERICO")
	private int codigoNumerico;
	
	@Enumerated(EnumType.STRING)
	@Column(name="FORMATO_DANFE")
	private FormatoDanfe formatoDanfe;
	
	@Enumerated(EnumType.STRING)
	@Column(name="TIPO_EMISSAO")
	private TipoEmissao tipoEmissao;
	
	@Enumerated(EnumType.STRING)
	@Column(name="TIPO_OPERACAO")
	private TipoOperacao tipoOperacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name="LOCAL_DESTINO")
	private LocalDestino localDestino;
	
	@Embedded
	private Municipio municipioFatoGerador;
	
	@Column(name="CONSUMIDOR_FINAL")
	private boolean consumidorFinal;
	
	@Enumerated(EnumType.STRING)
	@Column(name="FINALIDADE")
	private Finalidade finalidade;
	
	@Enumerated(EnumType.STRING)
	@Column(name="PRESENCA")
	private Presenca presenca;
	
	@Enumerated(EnumType.STRING)
	@Column(name="PROCESSO")
	private Processo processo;	
	
	@Embedded
	private Emitente emitente;
	
	@Embedded
	private Destinatario destinatario;
	
	@Embedded
	private LocalRetirada localRetirada;
	
	@Embedded
	private LocalEntrega localEntrega;	
	
	@Embedded
	private Transporte transporte;
	
	@Embedded
	private Cobranca cobranca;
	
	@Embedded
	@AttributeOverride(name="texto", column=@Column(name="FISCO_INFORMACAO",length=2000))
	@AssociationOverrides({
		@AssociationOverride(name="observacoes",
				joinTable=@JoinTable(name="FISCO_OBS"))
	})
	private Informacao informacaoFisco;
	
	@Embedded
	@AttributeOverride(name="texto", column=@Column(name="CONTRIB_INFORMACAO",length=4000))
	@AssociationOverrides({
		@AssociationOverride(name="observacoes",
				joinTable=@JoinTable(name="CONTRIB_OBS"))
	})
	private Informacao informacaoContribuinte;
	
	@Embedded
	private Exportacao exportacao;
	
	@Embedded
	private Contingencia contingencia;
	
	@Column(name="DANFE_IMPRESSO")
	private boolean danfeImpresso = false;
	
	@Embedded
	private FilialId filialId;
	
	@OneToMany(orphanRemoval=true,cascade=CascadeType.ALL)
	@JoinTable(name="REFERENCIAS",
	    joinColumns=@JoinColumn(name="ID_NF"),
	    inverseJoinColumns=@JoinColumn(name="ID_REFERENCIA"))
	private List<Referencia> referencias;
	
	@OneToMany(orphanRemoval=true,cascade=CascadeType.ALL)
	@JoinColumn(name="ID_NF")
	private List<Item> itens;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="codigo", column=@Column(name="MSG_COD")),
		@AttributeOverride(name="descricao", column=@Column(name="MSG_DSC"))
	})
	private Mensagem mensagem;	
	
	@Embedded
	private NumeroProtocolo numeroProtocoloAutorizacao;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="numero", column=@Column(name="CANC_NUMERO_PROTOCOLO"))
	})
	private NumeroProtocolo numeroProtocoloCancelamento;
	
	@Column(name="CANC_DATA_HORA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraCancelamento;
	
	@Embedded
	private NotistaId notistaId;
	
	@Column(name="DATA_HORA_AUTORIZACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraAutorizacao;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	private String xmlProtocolo;
	
	@OneToMany(orphanRemoval=true,cascade=CascadeType.ALL)
	@OrderColumn(name="ORDEM")
	@JoinColumn(name="ID_NF")
	private List<CartaCorrecao> cartasCorrecao;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
	@Version
    @Column(name="VERSAO")
    private int versaoConcorrencia;
	
	@SuppressWarnings("unused")
	private NotaFiscal() {
		super();
	}

	public NotaFiscal(
			Ambiente ambiente,
			NotaFiscalId notaFiscalId,
			FilialId filialId,
			String naturezaOperacao,
			FormaPagamento formaPagamento,
			Modelo modelo,
			Serie serie,
			Long numero, 
			Date emissao, 
			Date dataHora, 
			Integer codigoNumerico,
			FormatoDanfe formatoDanfe,
			TipoEmissao tipoEmissao,
			TipoOperacao tipoOperacao,
			LocalDestino localDestino, 
			Municipio municipioFatoGerador,
			boolean consumidorFinal,
			Finalidade finalidade,
			Presenca presenca,
			Processo processo,
			List<Referencia> referencias,
			Emitente emitente,
			Destinatario destinatario,
			LocalRetirada localRetirada,
			LocalEntrega localEntrega,
			List<Item> itens,
			Transporte transporte,
			Cobranca cobranca,
			Informacao informacaoFisco,
			Informacao informacaoContribuinte,
			Exportacao exportacao,
			Contingencia contingencia,
			NotistaId notistaId) {
		
		this.ambiente = ambiente;
		this.notaFiscalId = notaFiscalId;
		this.filialId = filialId;
		this.situacao=Situacao.INDEFINIDA;
		this.naturezaOperacao=naturezaOperacao;
		this.formaPagamento=formaPagamento;
		this.modelo=modelo;
		this.serie=serie;
		this.numero=numero;
		this.emissao=emissao;
		this.dataHora=dataHora;
		this.tipoOperacao=tipoOperacao;
		this.localDestino=localDestino;
		this.municipioFatoGerador=municipioFatoGerador;
		this.formatoDanfe=formatoDanfe;
		this.consumidorFinal=consumidorFinal;
		this.finalidade=finalidade;
		this.presenca=presenca;
		this.processo=processo;
		this.emitente = emitente;
		this.destinatario = destinatario;
		this.localRetirada = localRetirada;
		this.localEntrega = localEntrega;
		this.itens = itens;
		this.cobranca = cobranca;
		this.informacaoFisco = informacaoFisco;
		this.informacaoContribuinte = informacaoContribuinte;
		this.exportacao = exportacao;
		this.transporte=transporte;
		this.tipoEmissao = tipoEmissao;
		this.codigoNumerico = codigoNumerico == null ? randInt(1, 99999999) : codigoNumerico;
		this.contingencia = contingencia;
		this.notistaId = notistaId;
		this.setChaveAcesso(gerarChaveAcesso());
		for (Referencia referencia : referencias) 
			referenciar(referencia);
		consistirNotasReferencia();
	}
	
	private void setChaveAcesso(ChaveAcesso novaChaveAcesso) {
			this.chaveAcesso = novaChaveAcesso;
	}

	public NotaFiscal(Ambiente ambiente, NotaFiscalId notaFiscalId, FilialId filialId) {
		this.notaFiscalId = notaFiscalId;
		this.ambiente = ambiente;
		this.tipoEmissao = TipoEmissao.NORMAL;
		this.situacao=Situacao.INDEFINIDA;
		this.filialId = filialId;
	}
	
	public Ambiente ambiente(){
		return ambiente;
	}
	
	public FilialId filialId(){
		return filialId;
	}
	
	public NotistaId notistaId(){
		return notistaId;
	}
	
	private void consistirNotasReferencia(){
		if (referencias != null && referencias.size() > 500)
			throw new IllegalArgumentException(
					"Quantidade máxima de 500 Notas de Referência excedida. "
					+"Informadas: "+referencias.size());
	}
	public NotaFiscalId notaFiscalId(){
		return this.notaFiscalId;
	}

	
	public boolean pendenteDeTransmissao(){
		return this.situacao == Situacao.EMITIDA || this.situacao == Situacao.INDEFINIDA;
	}
	public void emitida(){
		assertSituacaoIgual("Situação inválida: "+this.situacao,Situacao.INDEFINIDA);
		this.situacao=Situacao.EMITIDA;
	}
	public void autorizada(NumeroProtocolo numeroProtocolo,Mensagem mensagem,
			Date dataHoraAutorizacao, String xmlProtocolo) {
		String msg = "Nota Fiscal está %s e não pode ser definida como Autorizada";
		
		if (situacao == Situacao.CANCELADA)
			throw new RuntimeException(String.format(msg, "Cancelada"));
		if (situacao == Situacao.DENEGADA)
			throw new RuntimeException(String.format(msg, "Denegada"));
		if (situacao == Situacao.INUTILIZADA)
			throw new RuntimeException(String.format(msg, "Inutilizada"));
		
		this.situacao=Situacao.AUTORIZADA;
		this.mensagem = mensagem;
		this.numeroProtocoloAutorizacao = numeroProtocolo;
		this.dataHoraAutorizacao = dataHoraAutorizacao;
		this.xmlProtocolo = xmlProtocolo;

		DominioRegistro.eventoDominioPublicador().
			publicar(new NotaFiscalAutorizada(notaFiscalId(),chaveAcesso(),ambiente()));
		
	}
	public void cancelar(NumeroProtocolo numeroProtocolo, Mensagem mensagem, 
			Date dataHoraCancelamento) {
		assertSituacaoIgual("Situação inválida: "+this.situacao,Situacao.AUTORIZADA);
		this.situacao=Situacao.CANCELADA;
		this.mensagem = mensagem;
		this.numeroProtocoloCancelamento = numeroProtocolo;
		this.dataHoraCancelamento = dataHoraCancelamento;
		
		DominioRegistro.eventoDominioPublicador().publicar(
				new CancelamentoHomologado(notaFiscalId()));
	}
	
	public void inutilizada() {
		assertSituacaoIgual("Situação inválida: "+this.situacao,Situacao.EMITIDA);
		this.situacao=Situacao.INUTILIZADA;
	}
	public void denegada() {
		assertSituacaoIgual("Situação inválida: "+this.situacao,Situacao.EMITIDA);
		this.situacao=Situacao.DENEGADA;
	}
	
	public void rejeitada(Mensagem mensagem){
		String msg = "Nota Fiscal está %s e não pode ser definida como Rejeitada";
		
		if (situacao == Situacao.AUTORIZADA)
			throw new RuntimeException(String.format(msg, "Autorizada"));
		if (situacao == Situacao.CANCELADA)
			throw new RuntimeException(String.format(msg, "Cancelada"));
		if (situacao == Situacao.DENEGADA)
			throw new RuntimeException(String.format(msg, "Denegada"));
		if (situacao == Situacao.INUTILIZADA)
			throw new RuntimeException(String.format(msg, "Inutilizada"));
		
		this.mensagem = mensagem;
	}
	
	public boolean estaEmitida() {
		return this.situacao==Situacao.EMITIDA;
	}
	public boolean estaAutorizada() {
		return this.situacao==Situacao.AUTORIZADA;
	}
	public boolean estaCancelada() {
		return this.situacao==Situacao.CANCELADA;
	}
	public String naturezaOperacao() {
		return naturezaOperacao;
	}
	public FormaPagamento formaPagamento() {
		return formaPagamento;
	}
	public Modelo modelo() {
		return modelo;
	}
	public Serie serie() {
		return serie;
	}
	public Long numero() {
		return numero;
	}
	public Date emissao() {
		return emissao;
	}
	public Date dataHora() {
		return dataHora;
	}
	public TipoOperacao tipoOperacao() {
		return tipoOperacao;
	}
	public LocalDestino localDestino() {
		return localDestino;
	}
	public Municipio municipioFatoGerador() {
		return municipioFatoGerador;
	}
	private void assertSituacaoIgual(String mensagem,Situacao... esperadas){
		for (Situacao esperada : esperadas) {
			if (esperada == this.situacao)
				return;
		}
		throw new UnsupportedOperationException(mensagem);
	}
	public FormatoDanfe formatoDanfe() {
		return formatoDanfe;
	}
	public boolean consumidorFinal() {
		return consumidorFinal;
	}
	public Finalidade finalidade() {
		return finalidade;
	}
	public Presenca presenca() {
		return presenca;
	}
	public Processo processo() {
		return processo;
	}
	public Dinheiro totalBaseCalculoIcms(){
		Dinheiro result = Dinheiro.ZERO;
		for (Item item : getItens())
			result = result.soma(item.imposto().icms().baseCalculo());
		return result;
	}
	public Dinheiro totalIcms(){
		Dinheiro result = Dinheiro.ZERO;
		for (Item item : getItens())
			result = result.soma(item.imposto().icms().valor());
		return result;
	}
	public Dinheiro totalBaseCalculoIcmsSt(){
		Dinheiro result = Dinheiro.ZERO;
		for (Item item : getItens())
			result = result.soma(item.imposto().icms().st().baseCalculo());
		return result;
	}
	public Dinheiro totalIcmsSt(){
		Dinheiro result = Dinheiro.ZERO;
		for (Item item : getItens())
			result = result.soma(item.imposto().icms().st().valor());
		return result;
	}
	public Dinheiro totalProdutos(){
		Dinheiro result = Dinheiro.ZERO;
		for (Item item : getItens())
			result = result.soma(item.produto().totalBruto());
		return result;
	}
	public Dinheiro totalFrete(){
		Dinheiro result = Dinheiro.ZERO;
		for (Item item : getItens())
			result = result.soma(item.produto().totalFrete());
		return result;
	}
	public Dinheiro totalSeguro(){
		Dinheiro result = Dinheiro.ZERO;
		for (Item item : getItens())
			result = result.soma(item.produto().totalSeguro());
		return result;
	}
	public Dinheiro totalDesconto(){
		Dinheiro result = Dinheiro.ZERO;
		for (Item item : getItens())
			result = result.soma(item.produto().valorDesconto());
		return result;
	}
	public Dinheiro totalPis(){
		Dinheiro result = Dinheiro.ZERO;
		for (Item item : getItens())
			result = result.soma(item.imposto().pis().valor());
		return result;
	}
	public Dinheiro totalCofins(){
		Dinheiro result = Dinheiro.ZERO;
		for (Item item : getItens())
			result = result.soma(item.imposto().cofins().valor());
		return result;
	}
	public Dinheiro valorIcmsDesonerado(){
		return Dinheiro.ZERO;
	}
	public Dinheiro outrasDespesasAcessorias(){
		Dinheiro result = Dinheiro.ZERO;
		for (Item item : getItens())
			result = result.soma(item.produto().outrasDespesasAcessorias());
		return result;
	}
	public Dinheiro total(){
		return totalProdutos()
				.subtrair(totalDesconto())
				.subtrair(valorIcmsDesonerado())
				.soma(totalIcmsSt())
				.soma(totalFrete())
				.soma(totalSeguro())
				.soma(outrasDespesasAcessorias());
	}	
	
	public Dinheiro totalValorAproximadoTributos(){
		Dinheiro result = Dinheiro.ZERO;
		for (Item item : getItens())
			result = result.soma(item.imposto().valorTotalAproximado());
		return result;
	}
	
	public Emitente emitente() {
		return emitente;
	}

	public Destinatario destinatario() {
		return destinatario;
	}

	public LocalRetirada localRetirada() {
		return localRetirada;
	}

	public LocalEntrega localEntrega() {
		return localEntrega;
	}
	
	public Exportacao exportacao(){
		return exportacao;
	}
	
	private List<Item> getItens(){
		if (itens == null)
			itens = new ArrayList<Item>();
		return itens;
	}
	public List<Item> itens() {
		return Collections.unmodifiableList(getItens());
	}
	public Item item(int i) {
		return getItens().get(i);
	}
	
	
	private List<Referencia> getReferencias(){
		if (referencias == null)
			referencias = new ArrayList<Referencia>();
		return referencias;
	}
	public boolean estaReferenciando(Referencia referencia){
		return getReferencias().contains(referencia);
	}

	public Cobranca cobranca() {
		return cobranca;
	}

	public Informacao informacaoContribuinte() {
		return informacaoContribuinte;
	}
	
	public Informacao informacaoFisco() {
		return informacaoFisco;
	}
	
	public Transporte transporte(){
		return transporte;
	}
	
	public int codigoNumerico(){
		return codigoNumerico;
	}
	
	public TipoEmissao tipoEmissao(){
		return tipoEmissao;
	}
	
	public ChaveAcesso chaveAcesso(){
		return chaveAcesso;
	}
	
	private ChaveAcesso gerarChaveAcesso(){
		return new ChaveAcesso(
				emitente().endereco().municipio().uf(), 
				emissao(), 
				emitente().cnpj(), 
				serie(), numero(), 
				tipoEmissao(), 
				codigoNumerico());
	}
	
	public Contingencia contingencia(){
		return contingencia;
	}

	public boolean notaEmContingencia() {
		return contingencia() != null;
	}

	public boolean temReferencias() {
		return getReferencias().size() > 0;
	}
	
	public Collection<Referencia> referencias(){
		return getReferencias();
	}

	public void referenciar(Referencia ref) {
		getReferencias().add(ref);
	}
	
	
	public void alterarTipoEmissaoParaContingencia(Contingencia contingencia) {
		setTipoEmissao(TipoEmissao.contingenciaPelaUf(
				ufEmitente()));
		this.contingencia = contingencia;
	}
	
	public void alterarTipoEmissaoParaFsDa(Contingencia contingencia) {
		setTipoEmissao(TipoEmissao.FS_DA);
		this.contingencia = contingencia;
	}
	public void alterarTipoEmissaoParaFsIa(Contingencia contingencia) {
		setTipoEmissao(TipoEmissao.FS_IA);
		this.contingencia = contingencia;
	}
	
	public void alterarTipoEmissaoParaNormal() {
		setTipoEmissao(TipoEmissao.NORMAL);
		this.contingencia = null;
	}
	
	private void setTipoEmissao(TipoEmissao novoTipoEmissao){
		if (tipoEmissao == novoTipoEmissao) return;
		
		String mensagem = consisteTrocaTipoEmissao(novoTipoEmissao);
		if (mensagem != null)
			throw new RuntimeException(mensagem);

		tipoEmissao = novoTipoEmissao;
		
		this.atualizarChaveAcesso();
	}
	
	private String consisteTrocaTipoEmissao(TipoEmissao novoTipoEmissao){
		if (!pendenteDeTransmissao())
			return "Nota já transmitida. Não é possível alterar o Tipo de Emissão";
		
		if (danfeImpresso && (tipoEmissao == TipoEmissao.FS_DA || tipoEmissao == TipoEmissao.FS_IA))
			return "Tipo de Emissão não pode ser alterado. Formulário de Segurança já foi impresso.";
		
		return null;
	}
	
	public boolean permiteAlterarTipoEmissao(TipoEmissao novoTipoEmissao){
		return consisteTrocaTipoEmissao(novoTipoEmissao) == null;
	}
	
	private void atualizarChaveAcesso(){
		this.setChaveAcesso(gerarChaveAcesso());
	}

	public Uf ufEmitente() {
		return emitente().endereco().municipio().uf();
	}

	public void definirDanfeComoImpresso() {
		boolean fs = this.tipoEmissao() == TipoEmissao.FS_DA || this.tipoEmissao() == TipoEmissao.FS_IA;  
		if (!fs && !this.estaAutorizada())
			throw new RuntimeException("Danfe somente pode ser impresso para nota autorizada.");
		danfeImpresso = true;
	}

	public boolean danfeImpresso() {
		return danfeImpresso;
	}
	
	public Mensagem mensagem(){
		return mensagem;
	}
	
	public String xmlProtocolo(){
		return xmlProtocolo;
	}
	
	public NumeroProtocolo numeroProtocoloAutorizacao(){
		return numeroProtocoloAutorizacao;
	}
	
	public Date dataHoraAutorizacao(){
		return dataHoraAutorizacao;
	}
	
	public NumeroProtocolo numeroProtocoloCancelamento(){
		return numeroProtocoloCancelamento;
	}
	
	public Date dataHoraCancelamento(){
		return dataHoraCancelamento;
	}
	
	//TODO Aprimorar Mesclagem
	public void mesclar(NotaFiscal nf) {
		this.naturezaOperacao = nf.naturezaOperacao;
		this.formaPagamento = nf.formaPagamento;
		this.modelo = nf.modelo;
		this.serie = nf.serie;
		this.numero = nf.numero;
		this.emissao = nf.emissao;
		this.dataHora = nf.dataHora;
		this.tipoOperacao = nf.tipoOperacao;
		this.localDestino = nf.localDestino;
		this.municipioFatoGerador = nf.municipioFatoGerador;
		this.consumidorFinal = nf.consumidorFinal;
		this.finalidade = nf.finalidade;
		this.presenca = nf.presenca;
		this.processo = nf.processo;
		this.emitente = nf.emitente;
		this.destinatario = nf.destinatario;
		this.localRetirada = nf.localRetirada;
		this.localEntrega = nf.localEntrega;
		this.transporte = this.transporte.mesclar(nf.transporte);
		this.cobranca = nf.cobranca;
		this.notistaId = nf.notistaId;
		
		mesclarInformacaoFisco(informacaoFisco);
		
		//this.informacaoContribuinte = nf.informacaoContribuinte.mesclar(nf.informacaoContribuinte);
		this.exportacao = nf.exportacao;
		
		this.getReferencias().clear();

		if (nf.referencias().size() > 0)
			this.getReferencias().addAll(nf.referencias);
		
		for (Item item : itens()) 
			mesclarItem(item,nf.itens());
		
		atualizarChaveAcesso();
		
	}
	
	private void mesclarInformacaoFisco(Informacao outra){
		if (this.informacaoFisco == null)
			this.informacaoFisco = outra == null ? null : outra.clonar(); 
		else
			this.informacaoFisco = this.informacaoFisco.mesclar(outra);
	}
	
	private void mesclarItem(Item item, List<Item> itens){
		for (Item outro : itens) {
			if (item.produto().codigo().equals(outro.produto().codigo()))
				item.mesclar(outro);
		}
	}

	public void alterarModoOperacao(ModoOperacao modoOperacao, 
			Contingencia contingencia) {
		switch (modoOperacao) {
		case NORMAL:
			if (permiteAlterarTipoEmissao(TipoEmissao.NORMAL))
				alterarTipoEmissaoParaNormal();
			break;
		case FS_DA:
			if (permiteAlterarTipoEmissao(TipoEmissao.FS_DA))
				alterarTipoEmissaoParaFsDa(contingencia);
			break;
		case SVC:
			TipoEmissao tipoEmissao = tipoEmissaoPeloModoOperacao(modoOperacao, ufEmitente());
			if (permiteAlterarTipoEmissao(tipoEmissao))
				alterarTipoEmissaoParaContingencia(contingencia);
			break;
		default:
			break;
		}
	}
	
	public static TipoEmissao tipoEmissaoPeloModoOperacao(ModoOperacao modoOperacao, Uf ufEmitente){
		switch (modoOperacao) {
		case NORMAL:
			return TipoEmissao.NORMAL;
		case FS_DA:
			return TipoEmissao.FS_DA;
		case SVC:
			return TipoEmissao.contingenciaPelaUf(ufEmitente);
		default:
			return null;
		}
	}

	public CartaCorrecao cartaCorrecaoAtual() {
		int size = getCartasCorrecao().size();
		if (size == 0)
			return null;
		else
			return getCartasCorrecao().get(size-1);
	}

	public void registrarCartaCorrecao(int sequencia, String correcao, Date dataRegistro,
			String xmlEnvio, String xmlRetorno) {
		
		if (cartaCorrecaoAtual() != null && 
				cartaCorrecaoAtual().naoEstaRegistrada())
			throw new RuntimeException(
					"Não é possível adicionar nova Carta de Correção. "
					+ "Existe Carta de Correção prévia não registrada");
		
		getCartasCorrecao().add(new CartaCorrecao(sequencia, correcao, 
				dataRegistro,xmlEnvio, xmlRetorno));
	}
	
	public void registrarCartaCorrecao(String correcao,Date dataRegistro) {
		registrarCartaCorrecao(ultimaSequenciaCartaCorrecao() + 1,correcao, dataRegistro,null,null);
	}
	
	private List<CartaCorrecao> getCartasCorrecao(){
		if (cartasCorrecao == null)
			cartasCorrecao = new ArrayList<CartaCorrecao>();
		return cartasCorrecao;
	}

	public int ultimaSequenciaCartaCorrecao() {
		int result = 0;
		for (CartaCorrecao cce : getCartasCorrecao()) 
			result = cce.sequencia();
		return result;
	}

}
