package com.hadrion.nfe.aplicacao.notista.data;

public class NotistaData {
	private String notistaId;
	private String nome;
	private String descricao;
	
	public NotistaData(String notistaId, String nome, String descricao) {
		super();
		this.notistaId = notistaId;
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public String getNotistaId() {
		return notistaId;
	}
	public void setNotistaId(String notistaId) {
		this.notistaId = notistaId;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
