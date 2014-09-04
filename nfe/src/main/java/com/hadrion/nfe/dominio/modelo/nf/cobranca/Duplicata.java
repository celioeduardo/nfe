package com.hadrion.nfe.dominio.modelo.nf.cobranca;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.tipos.Dinheiro;

public class Duplicata {
	private String numero;
	private Date vencimento;
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

	protected Duplicata() {
		super();
	} 
	
	
}
