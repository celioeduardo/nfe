package com.hadrion.nfe.dominio.modelo.nf.publico;


public enum Crt {
	SIMPLES_NACIONAL(1,"Simples Nacional"),
	SIMPLES_NACIONAL_EXCESSO(2,"Simples Nacional excesso sublimite receita bruta"),
	REGIME_NORMAL(3,"Regime Normal (V2.0)");	
	
	private int codigo;
	private String descricao;
	
	Crt(int codigo,String descricao){
		this.codigo = codigo;
		this.descricao=descricao;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public String descricao(){
		return descricao;
	}

	public static Crt obterPeloCodigo(int codigo) {
		if (codigo == 1) return SIMPLES_NACIONAL;
		else if (codigo == 2) return SIMPLES_NACIONAL_EXCESSO;
		else if (codigo == 3) return REGIME_NORMAL;
		return null;
	}
}
