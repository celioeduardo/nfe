package com.hadrion.nfe.dominio.modelo.nf.item.importacao;

import static com.hadrion.comum.Afirmacao.assertArgumentoNaoNulo;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;


public class ImportacaoItem {
	
	private int numero;
	private Date emissao;
	private String localDesembarque;
	private Uf ufDesembarque;
	private Date dataDesembarque;
	private ViaTransporte viaTransporte;
	private String codigoExportador;
	private Intermediacao intermediacao;
	private Optional<Dinheiro> valorArfmm;
	private Optional<Cnpj> cnpjTerceiro;
	private Optional<Uf> ufTerceiro;
	private Optional<Integer> pedido;
	private Optional<Integer> itemPedido;
	private Set<Adicao> adicoes;
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
		return ufTerceiro;
	}
	public String codigoExportador() {
		return codigoExportador;
	}
	public Optional<Integer> pedido() {
		return pedido;
	}

	public Optional<Integer> itemPedido() {
		return itemPedido;
	}
	private void setPedido(Integer pedido) {
		this.pedido = Optional.ofNullable(pedido);
	}
	private void setItemPedido(Integer itemPedido) {
		this.itemPedido = Optional.ofNullable(itemPedido);
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
		this.ufTerceiro = Optional.ofNullable(ufTerceiro);
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
