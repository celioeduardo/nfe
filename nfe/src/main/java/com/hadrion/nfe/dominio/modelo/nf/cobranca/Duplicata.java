package com.hadrion.nfe.dominio.modelo.nf.cobranca;

import java.util.Date;

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
	
	
	
}
