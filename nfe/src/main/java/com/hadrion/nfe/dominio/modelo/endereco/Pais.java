package com.hadrion.nfe.dominio.modelo.endereco;

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
	
}
