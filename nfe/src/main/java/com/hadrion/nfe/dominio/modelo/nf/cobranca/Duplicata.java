package com.hadrion.nfe.dominio.modelo.nf.cobranca;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.tipos.Dinheiro;

@Embeddable
@Access(AccessType.FIELD)
public class Duplicata {
	
	@Column(name="NUMERO")
	private String numero;
	
	@Column(name="VENCIMENTO")
	private Date vencimento;
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="VALOR"))
	private Dinheiro valor;
	
	public Duplicata(String numero, Date vencimento, Dinheiro valor) {
		super();
		this.numero = numero;
		this.vencimento = vencimento;
		this.valor = valor;
	}

	public String numero() {
		return numero;
	}

	public Date vencimento() {
		return vencimento;
	}

	public Dinheiro valor() {
		return valor;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Duplicata objetoTipado = (Duplicata) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero,objetoTipado.numero)
				.append(valor,objetoTipado.valor)
				.append(vencimento,objetoTipado.vencimento)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(3695,1523)
			.append(numero)
			.append(valor)
			.append(vencimento)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Duplicata [numero=" + numero
				+ ",valor="+ valor
				+ ",vencimento="+ vencimento
				+ "]";
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Duplicata() {} 
	
	
}
