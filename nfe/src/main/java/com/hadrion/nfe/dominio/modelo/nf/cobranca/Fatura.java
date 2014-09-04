package com.hadrion.nfe.dominio.modelo.nf.cobranca;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.tipos.Dinheiro;

public class Fatura {
	private String numero;
	private Dinheiro valor;
	private Dinheiro desconto;
	
	public Fatura(String numero, Dinheiro valor, Dinheiro desconto) {
		super();
		if (valor.igualAZero())
			throw new IllegalArgumentException("Valor da Fatura não pode ser zero.");
		
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
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Fatura objetoTipado = (Fatura) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero,objetoTipado.numero)
				.append(valor,objetoTipado.valor)
				.append(desconto,objetoTipado.desconto)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13697,3627)
			.append(numero)
			.append(valor)
			.append(desconto)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Fatura [numero=" + numero
				+ ",valor="+ valor
				+ ",deconto="+ desconto
				+ "]";
	} 
}
