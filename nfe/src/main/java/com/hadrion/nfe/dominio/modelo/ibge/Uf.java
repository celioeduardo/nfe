package com.hadrion.nfe.dominio.modelo.ibge;

public class Uf {
	
	private String codigo;
	
	public Uf(String codigo){
		//TODO Tratar códigos válidos
		//11-Rondônia 12-Acre 13-Amazonas 14-Roraima 15-Pará 16-Amapá 17-T ocantins
		//21-Maranhão 22-Piauí 23-Ceará
		//24-Rio Grande do Norte
		//25-Paraíba 26-Pernambuco 27-Alagoas 28-Sergipe 29-Bahia
		//31-Minas Gerais 32-Espírito Santo
		//33-Rio de Janeiro 35-São Paulo
		//41-Paraná 42-Santa Catarina 43-Rio Grande do Sul
		//50-Mato Grosso do Sul
		//51-Mato Grosso 52-Goiás 53-Distrito Federal
		this.codigo = codigo;
	}
	
	public String codigo(){
		return codigo;
	}
	
}
