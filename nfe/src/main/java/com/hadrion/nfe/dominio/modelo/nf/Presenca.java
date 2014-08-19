package com.hadrion.nfe.dominio.modelo.nf;

public enum Presenca {
	NAO_PRESENCIAL(0,"Não se aplica"),
	PRESENCIAL(1,"Operação presencial"),
	INTERNET(2,"Operação não presencial, pela Internet"),
	TELEATENDIMENTO(3,"Operação não presencial, Teleatendimento"),
	DOMICILIO(4,"NFC-e em operação com entrega a domicílio"),
	OUTROS(9,"Operação não presencial, outros");
	/*0=Não se aplica (por exemplo, Nota Fiscal complementar
		ou de ajuste);
		1=Operação presencial;
		2=Operação não presencial, pela Internet;
		3=Operação não presencial, Teleatendimento;
		4=NFC-e em operação com entrega a domicílio;
		9=Operação não presencial, outros*/
	private int codigo;
	private String descricao;
	Presenca(int codigo,String descricao){
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
