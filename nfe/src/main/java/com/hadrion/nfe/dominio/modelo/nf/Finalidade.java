package com.hadrion.nfe.dominio.modelo.nf;

/*
 	1=NF-e normal;
	2=NF-e complementar;
	3=NF-e de ajuste;
	4=Devolução/Retorno.
*/

public enum Finalidade {
	NORMAL(1,"NF-e normal"),
	COMPLEMENTAR(2,"NF-e complementar"),
	AJUSTE(3,"NF-e de ajuste"),
	DEVOLUCAO_RETORNO(4,"Devolução/Retorno");
	private int codigo;
	private String descricao;
	Finalidade(int codigo,String descricao){
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
