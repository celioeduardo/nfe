package com.hadrion.nfe.dominio.modelo.nf;

import static com.hadrion.util.NumeroUtil.randInt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
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

public class NotaFiscal {
	private Ambiente ambiente;
	private NotaFiscalId notaFiscalId;
	private Situacao situacaoHomologacao;
	private Situacao situacaoProducao;	
	private String naturezaOperacao;
	private FormaPagamento formaPagamento;
	private Modelo modelo;
	private Serie serie;
	private Long numero;
	private Date emissao;
	private Date dataHora;
	private int codigoNumerico;
	private FormatoDanfe formatoDanfe;
	private TipoEmissao tipoEmissao;
	private TipoOperacao tipoOperacao;
	private LocalDestino localDestino;
	private Municipio municipioFatoGerador; 
	private boolean consumidorFinal;
	private Finalidade finalidade;
	private Presenca presenca;
	private Processo processo;
	private List<Referencia> referencias;
	
	private Emitente emitente;
	private Destinatario destinatario;
	private LocalRetirada localRetirada;
	private LocalEntrega localEntrega;
	
	
	private List<Item> itens;
	private Transporte transporte;
	private Cobranca cobranca;
	private Informacao informacaoFisco;
	private Informacao informacaoContribuinte;
	private Exportacao exportacao;
	private Contingencia contingencia;

	@SuppressWarnings("unused")
	private NotaFiscal() {
		super();
	}

	public NotaFiscal(
			Ambiente ambiente,
			NotaFiscalId notaFiscalId,
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
		this.situacaoHomologacao=Situacao.INDEFINIDA;
		this.situacaoProducao=Situacao.INDEFINIDA;
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
	
	public NotaFiscal(Ambiente ambiente, NotaFiscalId notaFiscalId) {
		this.notaFiscalId = notaFiscalId;
		this.ambiente = ambiente;
		this.situacaoHomologacao=Situacao.INDEFINIDA;
		this.situacaoProducao=Situacao.INDEFINIDA;
	}
	
	public Ambiente ambiente(){
		return ambiente;
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

	public boolean pendenteDeTransmissaoHomologacao(){
		return this.situacaoHomologacao == Situacao.EMITIDA;
	}
	public void emitidaHomologacao(){
		assertSituacaoIgualHomologacao("Situação inválida: "+this.situacaoHomologacao,Situacao.INDEFINIDA);
		this.situacaoHomologacao=Situacao.EMITIDA;
	}
	public void autorizadaHomologacao() {
		assertSituacaoIgualHomologacao("Situação inválida: "+this.situacaoHomologacao,Situacao.EMITIDA);
		this.situacaoHomologacao=Situacao.AUTORIZADA;
	}
	public void canceladaHomologacao() {
		assertSituacaoIgualHomologacao("Situação inválida: "+this.situacaoHomologacao,Situacao.AUTORIZADA);
		this.situacaoHomologacao=Situacao.CANCELADA;
	}
	public void inutilizadaHomologacao() {
		assertSituacaoIgualHomologacao("Situação inválida: "+this.situacaoHomologacao,Situacao.EMITIDA);
		this.situacaoHomologacao=Situacao.INUTILIZADA;
	}
	public void denegadaHomologacao() {
		assertSituacaoIgualHomologacao("Situação inválida: "+this.situacaoHomologacao,Situacao.EMITIDA);
		this.situacaoHomologacao=Situacao.DENEGADA;
	}
	
	public boolean pendenteDeTransmissaoProducao(){
		return this.situacaoProducao == Situacao.EMITIDA;
	}
	public void emitidaProducao(){
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,Situacao.INDEFINIDA);
		this.situacaoProducao=Situacao.EMITIDA;
	}
	public void autorizadaProducao() {
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,Situacao.EMITIDA);
		this.situacaoProducao=Situacao.AUTORIZADA;
	}
	public void canceladaProducao() {
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,Situacao.AUTORIZADA);
		this.situacaoProducao=Situacao.CANCELADA;
	}
	public void inutilizadaProducao() {
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,Situacao.EMITIDA);
		this.situacaoProducao=Situacao.INUTILIZADA;
	}
	public void denegadaProducao() {
		assertSituacaoIgualProducao("Situação inválida: "+this.situacaoProducao,Situacao.EMITIDA);
		this.situacaoProducao=Situacao.DENEGADA;
	}
	
	public boolean estaEmitidaEmHomologacao() {
		return this.situacaoHomologacao==Situacao.EMITIDA;
	}
	public boolean estaEmitidaEmProducao() {
		return this.situacaoProducao==Situacao.EMITIDA;
	}
	public boolean estaAutorizadaEmHomologacao() {
		return this.situacaoHomologacao==Situacao.AUTORIZADA;
	}
	public boolean estaAutorizadaEmProducao() {
		return this.situacaoProducao==Situacao.AUTORIZADA;
	}
	public boolean estaCanceladaEmHomologacao() {
		return this.situacaoHomologacao==Situacao.CANCELADA;
	}
	public boolean estaCanceladaEmProducao() {
		return this.situacaoProducao==Situacao.CANCELADA;
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
	private void assertSituacaoIgualHomologacao(String mensagem,Situacao... esperadas){
		for (Situacao esperada : esperadas) {
			if (esperada == this.situacaoHomologacao)
				return;
		}
		throw new UnsupportedOperationException(mensagem);
	}
	private void assertSituacaoIgualProducao(String mensagem,Situacao... esperadas){
		for (Situacao esperada : esperadas) {
			if (esperada == this.situacaoProducao)
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

}
