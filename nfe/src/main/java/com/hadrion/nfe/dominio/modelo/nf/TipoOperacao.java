package com.hadrion.nfe.dominio.modelo.nf;

public enum TipoOperacao {
	ENTRADA(0),
	SAIDA(1);
	
	private int codigo;
	TipoOperacao(int codigo){
		this.codigo = codigo;
	}
	
	public int codigo(){
		return codigo;
	}
	public static Object obterPeloCodigo(int codigo) {
		if (codigo == 0)
			return ENTRADA;
		else if (codigo == 1)
			return SAIDA;
		return null;
	}
	
}
