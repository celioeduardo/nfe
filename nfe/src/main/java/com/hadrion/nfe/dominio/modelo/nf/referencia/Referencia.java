package com.hadrion.nfe.dominio.modelo.nf.referencia;

public enum Referencia {
	DEVOLUCAO(0,"Devolução de Mercadorias"),
	SUBSTITUICAO_CANCELADA(1,"Substituição de NF cancelada"),
	COMPLEMENTO(2,"Complementação de NF");
	/*
	Grupo com informações de Documentos Fiscais
	referenciados. Informação utilizada nas hipóteses
	previstas na legislação. (Ex.: Devolução de Mercadorias,
	Substituição de NF cancelada, Complementação de NF,
	etc.).*/
	
	private int codigo;
	private String descricao;
	Referencia(int codigo,String descricao){
		this.codigo = codigo;
		this.descricao=descricao;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public String descricao(){
		return descricao;
	}
	
}
