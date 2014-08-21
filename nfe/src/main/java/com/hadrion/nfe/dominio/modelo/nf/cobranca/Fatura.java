package com.hadrion.nfe.dominio.modelo.nf.cobranca;

import com.hadrion.nfe.tipos.Dinheiro;

public class Fatura {
	private String numero;
	private Dinheiro valor;
	private Dinheiro desconto;
	
	public Fatura(String numero, Dinheiro valor, Dinheiro desconto) {
		super();
		this.numero = numero;
		this.valor = valor;
		this.desconto = desconto;
	}
	
	public Dinheiro liquido(){
		return valor.subtrair(desconto);
	}
	
	public String numero(){
		return numero;
	}
	
	public Dinheiro valor(){
		return valor;
	}
	
	public Dinheiro desconto(){
		return desconto;
	}
	
}
