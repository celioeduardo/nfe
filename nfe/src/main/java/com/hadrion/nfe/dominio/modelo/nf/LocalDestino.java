package com.hadrion.nfe.dominio.modelo.nf;

public enum LocalDestino {
	INTERNA(1),
	INTERESTADUAL(2),
	EXTERIOR(3);
	
	private int codigo;
	LocalDestino(int codigo){
		this.codigo = codigo;
	}
	public int codigo(){
		return codigo;
	}
	public static LocalDestino obterPeloCodigo(int codigo) {
		if (codigo == 1) return INTERNA;
		else if (codigo == 2) return INTERESTADUAL;
		else if (codigo == 3) return EXTERIOR;
		return null;
	}
	
}
