package com.hadrion.nfe.dominio.modelo.nf.item.importacao;

public enum ViaTransporte {
	MARITIMA(1,"Marítima"),
	FLUVIAL(2,"Fluvial"),
	LACUSTRE(3,"Lacustre"),
	AEREA(4,"Aérea"),
	POSTAL(5,"Postal"),
	FERROVIARIA(6,"Ferrociária"),
	RODOVIARIA(7,"Rodoviária"),
	CONDUTO_REDETRANSMISSAO(8,"Conduto / Rede Transmissão"),
	MEIOSPROPRIOS(9,"Meios Próprios"),
	ENTRADA_SAIDAFICTA(10,"Entrada / Saida ficta");
	
	private int codigo;
	private String descricao;
	ViaTransporte(int codigo,String descricao){
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
		if (codigo == 1) return MARITIMA;
		if (codigo == 2) return FLUVIAL;
		if (codigo == 3) return LACUSTRE;
		if (codigo == 4) return AEREA;
		if (codigo == 5) return POSTAL;
		if (codigo == 6) return FERROVIARIA;
		if (codigo == 7) return RODOVIARIA;
		if (codigo == 8) return CONDUTO_REDETRANSMISSAO;
		if (codigo == 9) return MEIOSPROPRIOS;
		if (codigo == 10) return ENTRADA_SAIDAFICTA;
		return null;
	}
	

}
