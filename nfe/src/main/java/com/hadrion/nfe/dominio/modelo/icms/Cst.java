package com.hadrion.nfe.dominio.modelo.icms;

public enum Cst {
	CST_00(0, "Tributada integralmente"),
	CST_10(10,"Tributada e com cobrança do ICMS por ST"),
	CST_20(20,"Com Redução de Base de Cálculo"),
	CST_30(30,"Isenta ou não tributada e com cobrança do ICMS por ST"),
	CST_40(40,"Isenta"),
	CST_41(41,"Não Tributada"),
	CST_50(50,"Suspensão"),
	CST_51(51,"Diferimento"),
	CST_60(60,"Cobrado anteriormente por ST"),
	CST_70(70,"Com redução de base de cálculo e cobrança do ICMS por ST"),
	CST_90(90,"Outros");
	
	private int codigo;
	private String nome;
	
	Cst(int codigo, String nome){
		this.codigo = codigo;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public String nome(){
		return nome;
	}
}
