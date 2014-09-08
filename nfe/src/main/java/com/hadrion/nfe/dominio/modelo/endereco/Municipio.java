package com.hadrion.nfe.dominio.modelo.endereco;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;

public class Municipio {
	private int codigo;
	private String nome;
	private Uf uf;
	
	public Municipio(int codigo, String nome, Uf uf) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.uf = uf;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public String nome(){
		return nome;
	}
	
	public Uf uf(){
		return uf;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Municipio objetoTipado = (Municipio) objeto;
			objetosIguais = new EqualsBuilder()
				.append(nome, objetoTipado.nome)
				.append(uf, objetoTipado.uf)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(633,3147)
			.append(nome)
			.append(uf)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Municipio [nome="+ nome
				+",uf="+uf
				+ "]";
	} 
}
