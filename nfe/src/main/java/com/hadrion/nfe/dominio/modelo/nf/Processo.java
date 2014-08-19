package com.hadrion.nfe.dominio.modelo.nf;

public enum Processo {
	APLICATIVO_CONTRIBUINTE(0,"Emissão de NF-e com aplicativo do contribuinte"),
	AVULSA_FISCO(1,"Emissão de NF-e avulsa pelo Fisco"),
	AVULSA_CONTRIBUINTE(2,"Emissão de NF-e avulsa, pelo contribuinte com seu certificado digital, através do site do Fisco"),
	APLICATIVO_FISCO(3,"Emissão NF-e pelo contribuinte com aplicativo fornecido pelo Fisco");
	/*0=Emissão de NF-e com aplicativo do contribuinte;
	1=Emissão de NF-e avulsa pelo Fisco;
	2=Emissão de NF-e avulsa, pelo contribuinte com seu
	certificado digital, através do site do Fisco;
	3=Emissão NF-e pelo contribuinte com aplicativo
	fornecido pelo Fisco.*/
	private int codigo;
	private String descricao;
	Processo(int codigo,String descricao){
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
