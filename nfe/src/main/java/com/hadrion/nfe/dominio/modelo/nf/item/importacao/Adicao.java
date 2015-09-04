package com.hadrion.nfe.dominio.modelo.nf.item.importacao;

import static com.hadrion.comum.Afirmacao.assertArgumentoNaoNulo;

import java.util.Optional;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.tipos.Dinheiro;
@Entity
@SequenceGenerator(name="SEQ",sequenceName="SQ_ADICAO")
@Table(name="ADICAO")
public class Adicao {

	@Column(name="NUMERO")
	private int numero;

	@Column(name="SEQUENCIA")
	private int sequencia;
	
	@Column(name="CODIGO_FABRICANTE")
	private String codigoFabricante;

	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="DESCONTO"))
	private Optional<Dinheiro> desconto;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")
	@Column(name = "ID")
	private Long id;

	public Adicao(int numero, int sequencia, String codigoFabricante, Dinheiro desconto) {
		setNumero(numero);
		setSequencia(sequencia);
		setCodigoFabricante(codigoFabricante);
		setDesconto(desconto);
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
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(36579,139)
		.append(numero)
		.append(sequencia)
		.append(codigoFabricante)
		.append(desconto)
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Adicao [numero=" + numero
				+ ",sequencia=" + sequencia
				+ ",codigoFabricante=" + codigoFabricante 
				+ ",desconto=" + desconto 
				+ "]";	
	}

	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Adicao(){}

}
