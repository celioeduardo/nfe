package com.hadrion.nfe.dominio.modelo.nf;

import static com.hadrion.util.NumeroUtil.randInt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
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
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Cobranca;
import com.hadrion.nfe.dominio.modelo.nf.informacao.Informacao;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalEntrega;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalRetirada;
import com.hadrion.nfe.dominio.modelo.nf.publico.Destinatario;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.tipos.Dinheiro;

@Entity
@SequenceGenerator(name="SEQ",sequenceName="SQ_NF")
@Table(name="NF")
public class NotaFiscal {
	@Enumerated(EnumType.STRING)
	private Ambiente ambiente;
	
	@Embedded
	private NotaFiscalId notaFiscalId;
	
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
	@AttributeOverride(name="texto", column=@Column(name="FISCO_INFORMACAO"))
	@AssociationOverrides({
		@AssociationOverride(name="observacoes",
				joinTable=@JoinTable(name="FISCO_OBS"))
	})
	private Informacao informacaoFisco;
	
	@Embedded
	@AttributeOverride(name="texto", column=@Column(name="CONTRIB_INFORMACAO"))
	@AssociationOverrides({
		@AssociationOverride(name="observacoes",
				joinTable=@JoinTable(name="CONTRIB_OBS"))
	})
	private Informacao informacaoContribuinte;
	
	@Embedded
	private Exportacao exportacao;
	
	@Embedded
	private Contingencia contingencia;
	
	@Column(name="FS_IMPRESSO")
	private boolean formularioSegurancaImpresso = false;
	
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

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
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
			Contingencia contingencia) {
		
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

		for (Referencia referencia : referencias) 
			referenciar(referencia);
		consistirNotasReferencia();
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
	public void autorizada() {
		assertSituacaoIgual("Situação inválida: "+this.situacao,Situacao.EMITIDA);
		this.situacao=Situacao.AUTORIZADA;
	}
	public void cancelada() {
		assertSituacaoIgual("Situação inválida: "+this.situacao,Situacao.AUTORIZADA);
		this.situacao=Situacao.CANCELADA;
	}
	public void inutilizada() {
		assertSituacaoIgual("Situação inválida: "+this.situacao,Situacao.EMITIDA);
		this.situacao=Situacao.INUTILIZADA;
	}
	public void denegada() {
		assertSituacaoIgual("Situação inválida: "+this.situacao,Situacao.EMITIDA);
		this.situacao=Situacao.DENEGADA;
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
		return new ChaveAcesso(
				emitente().endereco().municipio().uf() , 
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
	
	public Iterable<Referencia> referencias(){
		return getReferencias();
	}

	public void referenciar(Referencia ref) {
		getReferencias().add(ref);
	}
	
	
	public void alterarTipoEmissaoParaContingencia() {
		setTipoEmissao(TipoEmissao.contingenciaPelaUf(
				ufEmitente()));
	}
	
	private void setTipoEmissao(TipoEmissao novoTipoEmissao){
		if (tipoEmissao == novoTipoEmissao) return;
		
		if (!pendenteDeTransmissao())
			throw new RuntimeException(
					"Nota já transmitida. Não é possível alterar o Tipo de Emissão");
		
		if (formularioSegurancaImpresso)
			throw new RuntimeException(
					"Tipo de Emissão não pode ser alterado. Formulário de Segurança já foi impresso.");
		
		tipoEmissao = novoTipoEmissao;
	}

	public Uf ufEmitente() {
		return emitente().endereco().municipio().uf();
	}

	public void alterarTipoEmissao(TipoEmissao novoTipoEmissao) {
		setTipoEmissao(novoTipoEmissao);
	}

	public void definirFormularioSegurancaoComoImpresso() {
		if (tipoEmissao != TipoEmissao.FS_DA && tipoEmissao != TipoEmissao.FS_IA)
			throw new RuntimeException(
					"Formulário de Segurança não pode ser definido como impresso quando "
					+ "o Tipo de Emissão for diferente de FS-DA ou FS-IA");
		formularioSegurancaImpresso = true;
	}

	public boolean formularioSegurancaImpresso() {
		return formularioSegurancaImpresso;
	}

}
