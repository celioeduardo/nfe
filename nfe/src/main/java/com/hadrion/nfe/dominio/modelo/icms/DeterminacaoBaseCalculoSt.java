package com.hadrion.nfe.dominio.modelo.icms;

public enum DeterminacaoBaseCalculoSt {
	PRECO_TABELADO(0,"Preço tabelado ou máximo sugerido"),
	LISTA_NEGATIVA(1,"Lista negativa (valor)"),
	LISTA_POSITIVA(2,"Lista positiva (valor)"),
	LISTA_NEUTRA(3,"Lista neutra (valor)"),
	MVA(4,"Margem Valor Agregado (%)"),
	PAUTA(5,"Pauta (Valor)");
	
	private int codigo;
	private String nome;
	
	DeterminacaoBaseCalculoSt(int codigo, String nome){
		this.codigo = codigo;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public String nome(){
		return nome;
	}
}
