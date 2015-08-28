package com.hadrion.nfe.dominio.modelo.nf.item.importacao;

import static com.hadrion.comum.Afirmacao.assertArgumentoNaoNulo;

import java.util.Optional;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.tipos.Dinheiro;

public class Adicao {

	private int numero;
	private int sequencia;
	private String codigoFabricante;
	private Optional<Dinheiro> desconto;
	private Optional<Integer> drawback;
	private Optional<Integer> pedido;
	private Optional<Integer> itemPedido;
	

	public Adicao(int numero, int sequencia, String codigoFabricante, Dinheiro desconto, 
			Integer drawback,Integer pedido,Integer itemPedido) {
		setNumero(numero);
		setSequencia(sequencia);
		setCodigoFabricante(codigoFabricante);
		setDesconto(desconto);
		setDrawback(drawback);
		setPedido(pedido);
		setItemPedido(itemPedido);
	}
	public int numero() {
		return numero;
	}

	public int sequencia() {
		return sequencia;
	}

	public String fabricante() {
		return codigoFabricante;
	}

	public Optional<Dinheiro> desconto() {
		return desconto;
	}

	public Optional<Integer> drawback() {
		return drawback;
	}

	public Optional<Integer> pedido() {
		return pedido;
	}

	public Optional<Integer> itemPedido() {
		return itemPedido;
	}
	
	private void setNumero(int numero) {
		assertArgumentoNaoNulo(numero, "Numero da Adição é obrigatório.");
		this.numero = numero;
	}
	private void setSequencia(int sequencia) {
		assertArgumentoNaoNulo(sequencia, "Numero da Sequencia na Adição é obrigatório.");
		this.sequencia = sequencia;
	}
	private void setCodigoFabricante(String codigoFabricante) {
		assertArgumentoNaoNulo(codigoFabricante, "Código do Fabricante é obrigatório.");
		this.codigoFabricante = codigoFabricante;
	}
	private void setDesconto(Dinheiro desconto) {
		this.desconto = Optional.ofNullable(desconto);
	}
	private void setDrawback(Integer drawback) {
		this.drawback = Optional.ofNullable(drawback);
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
			Adicao objetoTipado = (Adicao) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero, objetoTipado.numero)
				.append(sequencia, objetoTipado.sequencia)
				.append(codigoFabricante, objetoTipado.codigoFabricante)
				.append(desconto, objetoTipado.desconto)
				.append(drawback, objetoTipado.drawback)
				.append(pedido, objetoTipado.pedido)
				.append(itemPedido, objetoTipado.itemPedido)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(132079,137)
		.append(numero)
		.append(sequencia)
		.append(codigoFabricante)
		.append(desconto)
		.append(drawback)
		.append(pedido)
		.append(itemPedido)
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Adicao [numero=" + numero
				+ ",sequencia=" + sequencia
				+ ",codigoFabricante=" + codigoFabricante 
				+ ",desconto=" + desconto 
				+ ",drawback=" + drawback 
				+ ",pedido=" + pedido 
				+ ",itemPedido=" + itemPedido 
				+ "]";	
	}

	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Adicao(){}

}
