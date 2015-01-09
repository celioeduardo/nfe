package com.hadrion.nfe.dominio.modelo.notista;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="SEQ", sequenceName="SQ_NOTISTA")
@Table(name="NOTISTA")
public class Notista {

	@Embedded
	private NotistaId notistaId;
	@Column(name="NOME")
	private String nome;
	@Column(name="DESCRICAO")
	private String descricao; 
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
	public Notista(NotistaId notistaId, 
			String nome, String descricao) {
		this.notistaId = notistaId;
		this.nome = nome;
		this.descricao = descricao;
	}

	public NotistaId notistaId() {
		return notistaId;
	}

	public String nome() {
		return this.nome;
	}
	public String descricao() {
		return this.descricao;
	}
	
	public void renomear(String novoNome){
		this.nome = novoNome;
	}
	
	public void alterarDescricao(String novaDescricao){
		this.descricao = novaDescricao;
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Notista(){}
	
}
