package com.hadrion.nfe.dominio.modelo;

public enum Ambiente {
	PRODUCAO(1), 
	HOMOLOGACAO(2);
	
	int codigo;
	
	Ambiente(int codigo){
		this.codigo = codigo;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public static Ambiente obterPeloCodigo(int codigo){
		for (Ambiente ambiente : Ambiente.values()) {
			if (ambiente.codigo() == codigo)
				return ambiente;
		}
		return null;
	}
}
