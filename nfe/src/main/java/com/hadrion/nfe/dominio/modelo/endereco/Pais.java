package com.hadrion.nfe.dominio.modelo.endereco;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class Pais {
	private Long codigo;
	private String nome;
	
	public Pais(Long codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public Long codigo(){
		return codigo;
	}
	
	public String nome(){
		return nome;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Pais objetoTipado = (Pais) objeto;
			objetosIguais = new EqualsBuilder()
				.append(codigo, objetoTipado.codigo)
				.append(nome, objetoTipado.nome)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(6731,31579)
			.append(codigo)
			.append(nome)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Telefone [codigo="+ codigo
				+"nome="+nome
				+ "]";
	} 
}
