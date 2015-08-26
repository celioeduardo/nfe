package com.hadrion.nfe.dominio.modelo.nf.importacao;

public enum Intermediacao {
	CONTA_PROPRIA(1,"Importação por Conta Própria"),
	CONTA_ORDEM(2,"Importação por Conta e Ordem"),
	ENCOMENDA(3,"Importação por encomenda");
	
	private int codigo;
	private String descricao;
	Intermediacao(int codigo,String descricao){
		this.codigo = codigo;
		this.descricao=descricao;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public String descricao(){
		return descricao;
	}

	public static Object obterPeloCodigo(int codigo) {
		if (codigo == 1) return CONTA_PROPRIA;
		if (codigo == 2) return CONTA_ORDEM;
		if (codigo == 3) return ENCOMENDA;
		return null;
	}

}
