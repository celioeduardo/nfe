package com.hadrion.nfe.dominio.modelo.icms;

public enum DeterminacaoBaseCalculo {
	MARGEM_VALOR_AGREGADO(0, "Margem Valor Agregado (%)"),
	PAUTA(1,"Pauta (Valor)"),
	PRECO_TABELADO(2,"Preço Tabelado Max (Valor)"),
	VALOR_OPERACAO(3,"Valor da Operação");
	
	private int codigo;
	private String nome;
	
	DeterminacaoBaseCalculo(int codigo, String nome){
		this.codigo = codigo;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public String nome(){
		return nome;
	}
}
