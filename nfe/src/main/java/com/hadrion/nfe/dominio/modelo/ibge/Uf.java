package com.hadrion.nfe.dominio.modelo.ibge;

public enum Uf {
	RO("11","Rondônia"),
	AC("12","Acre"),
	AM("13","Amazonas"),
	RR("14","Roraima"),
	PA("15","Pará"),
	AP("16","Amapá"),
	TO("17","Tocantins"),
	MA("21","Maranhão"),
	PI("22","Piauí"),
	CE("23","Ceará"),
	RN("24","Rio Grande do Norte"),
	PB("25","Paraíba"),
	PE("26","Pernambuco"),
	AL("27","Alagoas"),
	SE("28","Sergipe"),
	BA("29","Bahia"),
	MG("31","Minas Gerais"),
	ES("32","Espírito Santo"),
	RJ("33","Rio de Janeiro"),
	SP("35","São Paulo"),
	PR("41","Paraná"),
	SC("42","Santa Catarina"),
	RS("43","Rio Grande do Sul"),
	MS("50","Mato Grosso do Sul"),
	MT("51","Mato Grosso"),
	GO("52","Goiás"),
	DF("53","Distrito Federal"),
	EX("EX","Exterior");
	//11-Rondônia 12-Acre 13-Amazonas 14-Roraima 15-Pará 16-Amapá 17-Tocantins
	//21-Maranhão 22-Piauí 23-Ceará
	//24-Rio Grande do Norte
	//25-Paraíba 26-Pernambuco 27-Alagoas 28-Sergipe 29-Bahia
	//31-Minas Gerais 32-Espírito Santo
	//33-Rio de Janeiro 35-São Paulo
	//41-Paraná 42-Santa Catarina 43-Rio Grande do Sul
	//50-Mato Grosso do Sul
	//51-Mato Grosso 52-Goiás 53-Distrito Federal
	
	private String codigo;
	private String descricao;
	Uf(String codigo,String descricao){
		this.codigo = codigo;
		this.descricao=descricao;
	}
	
	public String codigo(){
		return codigo;
	}
	
	public String descricao(){
		return descricao;
	}
	
	public static Uf obterPeloCodigo(String codigo){
		for (Uf uf : Uf.values()) 
			if (codigo.equals(uf.codigo))
				return uf;
		return null;
	}
}
