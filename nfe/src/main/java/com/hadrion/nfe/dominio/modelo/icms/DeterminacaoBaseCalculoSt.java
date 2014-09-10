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

	public static DeterminacaoBaseCalculoSt obterPeloCodigo(int codigo) {
		if (codigo == 0) return PRECO_TABELADO;
		if (codigo == 1) return LISTA_NEGATIVA;
		if (codigo == 2) return LISTA_POSITIVA;
		if (codigo == 3) return LISTA_NEUTRA;
		if (codigo == 4) return MVA;
		if (codigo == 5) return PAUTA;
		return null;
	}
}
