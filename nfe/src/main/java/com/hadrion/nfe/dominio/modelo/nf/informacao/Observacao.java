package com.hadrion.nfe.dominio.modelo.nf.informacao;

public class Observacao {
	private String campo;
	private String conteudo;
	
	public Observacao(String campo, String conteudo) {
		super();
		this.campo = campo;
		this.conteudo = conteudo;
	}
	
	public String campo(){
		return campo;
	}
	
	public String conteudo(){
		return conteudo;
	}

}
