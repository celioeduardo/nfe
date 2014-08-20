package com.hadrion.nfe.dominio.modelo.endereco;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;

public class Municipio {
	private String nome;
	private Uf uf;
	
	public Municipio(String nome, Uf uf) {
		super();
		this.nome = nome;
		this.uf = uf;
	}
	
	public String nome(){
		return nome;
	}
	
	public Uf uf(){
		return uf;
	}
	
}
