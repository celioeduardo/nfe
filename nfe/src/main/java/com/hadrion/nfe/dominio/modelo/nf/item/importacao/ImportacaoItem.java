package com.hadrion.nfe.dominio.modelo.nf.item.importacao;

import static com.hadrion.comum.Afirmacao.assertArgumentoNaoNulo;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;

@Entity
@SequenceGenerator(name="SEQ", sequenceName="SQ_IMPORTACAO")
@Table(name="IMPORTACAO")
public class ImportacaoItem {

	@Column(name="NUMERO")
	private int numero;
	
	@Column(name = "EMISSAO")
	@Temporal(TemporalType.DATE)
	private Date emissao;
	
	@Column(name="DESEMBARQUE_LOCAL")
	private String localDesembarque;
	
	@Enumerated(EnumType.STRING)
	@Column(name="DESEMBARQUE_UF")
	private Uf ufDesembarque;
	
	@Column(name = "DESEMBARQUE")
	@Temporal(TemporalType.DATE)
	private Date dataDesembarque;
	
	@Enumerated(EnumType.STRING)
	@Column(name="VIA_TRANSPORTE")
	private ViaTransporte viaTransporte;
	
	@Column(name="CODIGO_EXPORTADOR")
	private String codigoExportador;

	@Enumerated(EnumType.STRING)
	@Column(name="INTERMEDIACAO")
	private Intermediacao intermediacao;

	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="ARFMM"))
	private Optional<Dinheiro> valorArfmm;

	@Embedded
	@AttributeOverride(name="numero", column=@Column(name="TERCEIRO_CNPJ"))
	private Optional<Cnpj> cnpjTerceiro;

	@Column(name="TERCEIRO_UF")	
	private Uf ufTerceiro;
	
	@Column(name="PEDIDO",nullable = true)
	private Integer pedido;
	
	@Column(name="PEDIDO_ITEM",nullable = true)
	private Integer itemPedido;
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_DI")
	private Set<Adicao> adicoes;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")
	@Column(name = "ID")
	private Long id;
	
	public ImportacaoItem(int numero,Date data,String localDesembarque,Uf ufDesembarque, 
			Date dataDesembarque,ViaTransporte viaTransporte,String codigoExportador,
			Dinheiro valorArfmm,Intermediacao intermediacao,Cnpj cnpjTerceiro,Uf ufTerceiro,
			Integer pedido,Integer itemPedido,Set<Adicao> adicoes) {
		setNumero(numero);
		setEmissao(data);
		setLocalDesembarque(localDesembarque);
		setUfDesembarque(ufDesembarque);
		setDataDesembarque(dataDesembarque);
		setViaTransporte(viaTransporte);
		setCodigoExportador(codigoExportador);
		setIntermediacao(intermediacao);
		setValorArfmm(valorArfmm);
		setCnpjTerceiro(cnpjTerceiro);
		setUfTerceiro(ufTerceiro);
		setPedido(pedido);
		setItemPedido(itemPedido);
		setAdicoes(adicoes);
	}
	
	public int numero() {
		return this.numero;
	}	
	public Date emissao() {
		return emissao;
	}	
	public String localDesembarque() {
		return localDesembarque;
	}
	public Uf ufDesembarque() {
		return ufDesembarque;
	}
	public Date dataDesembarque() {
		return dataDesembarque;
	}
	public ViaTransporte viaTransporte() {
		return viaTransporte;
	}
	public Intermediacao intermediacao() {
		return intermediacao;
	}
	public Optional<Dinheiro> valorArfmm() {
		return this.valorArfmm;
	}
	public Optional<Cnpj> cnpjTerceiro() {
		return cnpjTerceiro;
	}
	public Optional<Uf> ufTerceiro() {
		return Optional.ofNullable(ufTerceiro);
	}
	public String codigoExportador() {
		return codigoExportador;
	}
	public Optional<Integer> pedido() {
		return Optional.ofNullable(pedido);
	}
	public Optional<Integer> itemPedido() {
		return Optional.ofNullable(itemPedido);
	}
	private void setPedido(Integer pedido) {
		this.pedido = pedido;
	}
	private void setItemPedido(Integer itemPedido) {
		this.itemPedido = itemPedido;		
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			ImportacaoItem objetoTipado = (ImportacaoItem) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero,objetoTipado.numero)
				.append(emissao,objetoTipado.emissao)
				.append(localDesembarque,objetoTipado.localDesembarque)
				.append(ufDesembarque,objetoTipado.ufDesembarque)
				.append(dataDesembarque,objetoTipado.dataDesembarque)
				.append(viaTransporte,objetoTipado.viaTransporte)
				.append(codigoExportador,objetoTipado.codigoExportador)
				.append(intermediacao,objetoTipado.intermediacao)
				.append(valorArfmm,objetoTipado.valorArfmm)
				.append(cnpjTerceiro,objetoTipado.cnpjTerceiro)
				.append(ufTerceiro,objetoTipado.ufTerceiro)
				.append(pedido, objetoTipado.pedido)
				.append(itemPedido, objetoTipado.itemPedido)
				.append(adicoes,objetoTipado.adicoes)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(1327,171)
			.append(numero)
			.append(emissao)
			.append(localDesembarque)
			.append(ufDesembarque)
			.append(dataDesembarque)
			.append(viaTransporte)
			.append(codigoExportador)
			.append(intermediacao)
			.append(valorArfmm)
			.append(cnpjTerceiro)
			.append(ufTerceiro)
			.append(pedido)
			.append(itemPedido)
			.append(adicoes)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Importacao [numero=" + numero
				+ ",emissao="+ emissao
				+ ",localDesembarque="+ localDesembarque
				+ ",ufDesembarque="+ ufDesembarque
				+ ",dataDesembarque="+ dataDesembarque
				+ ",viaTransporte="+ viaTransporte
				+ ",codigoExportador="+ codigoExportador
				+ ",intermediacao="+ intermediacao
				+ ",valorArfmm="+ valorArfmm
				+ ",cnpjTerceiro="+ cnpjTerceiro
				+ ",ufTerceiro="+ ufTerceiro
				+ ",pedido=" + pedido 
				+ ",itemPedido=" + itemPedido 
				+ ",adicoes="+ adicoes
				+ "]";
	}	
	
	private void setEmissao(Date emissao) {
		assertArgumentoNaoNulo(emissao, "Data de emissão é obrigatória.");
		this.emissao = emissao;
	}

	private void setLocalDesembarque(String localDesembarque) {
		assertArgumentoNaoNulo(localDesembarque, "Local de Desembarque é obrigatório.");
		this.localDesembarque = localDesembarque;
	}

	private void setUfDesembarque(Uf ufDesembarque) {
		assertArgumentoNaoNulo(ufDesembarque, "UF Data de Desembarque é obrigatória.");
		this.ufDesembarque = ufDesembarque;
	}

	private void setDataDesembarque(Date dataDesembarque) {
		assertArgumentoNaoNulo(dataDesembarque, "Data de Desembarque é obrigatória.");
		this.dataDesembarque = dataDesembarque;
	}

	private void setViaTransporte(ViaTransporte viaTransporte) {
		assertArgumentoNaoNulo(viaTransporte, "Via de Ttransporte Internacional é obrigatória.");
		this.viaTransporte = viaTransporte;
	}

	private void setCodigoExportador(String codigoExportador) {
		assertArgumentoNaoNulo(codigoExportador, "Código do Exportador é obrigatório.");
		this.codigoExportador = codigoExportador;
	}

	private void setAdicoes(Set<Adicao> adicoes) {
		assertArgumentoNaoNulo(adicoes, "Adições são obrigatórias.");
		this.adicoes=new HashSet<Adicao>();
		if (adicoes!=null)
			this.adicoes.addAll(adicoes);
	}
	
	private void setIntermediacao(Intermediacao intermediacao) {
		assertArgumentoNaoNulo(intermediacao, "Tipo de Intermediação é obrigatório.");
		this.intermediacao = intermediacao;
	}
	
	private void setNumero(int numero){
		if (numero==0) 
			throw new IllegalArgumentException("Número da D.I. é obrigatório");
		this.numero=numero;
	}

	private void setValorArfmm(Dinheiro valorArfmm) {
		this.valorArfmm = Optional.ofNullable(valorArfmm);
	}
	
	private void setCnpjTerceiro(Cnpj cnpjTerceiro) {
		this.cnpjTerceiro = Optional.ofNullable(cnpjTerceiro);
	}
	
	private void setUfTerceiro(Uf ufTerceiro) {
		this.ufTerceiro = ufTerceiro;
	}

	private Set<Adicao> getAdicoes() {
		if (adicoes == null)
			adicoes = new HashSet<Adicao>();
		return adicoes;
	}

	public Iterable<Adicao> obterAdicoes() {
		return getAdicoes();
	}

	public Object quantidadeAdicoes() {
		return getAdicoes().size();
	} 
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private ImportacaoItem(){}
	
		
}
