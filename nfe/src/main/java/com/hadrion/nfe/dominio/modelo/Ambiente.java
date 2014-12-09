package com.hadrion.nfe.dominio.modelo;

public enum Ambiente {
	PRODUCAO(1,"P"), 
	HOMOLOGACAO(2,"H");
	
	int codigo;
	String sigla;
	
	Ambiente(int codigo, String sigla){
		this.codigo = codigo;
		this.sigla = sigla;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public String sigla(){
		return sigla;
	}
	
	public static Ambiente obterPeloCodigo(int codigo){
		for (Ambiente ambiente : Ambiente.values()) {
			if (ambiente.codigo() == codigo)
				return ambiente;
		}
		return null;
	}
}
