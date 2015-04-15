package com.hadrion.nfe.dominio.modelo.portal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Representa a tabela 5.1.1 - Tabela de Códigos de Erros e Descrições de Mensagens de Erros
 * @author celioeduardo
 *
 */
@Embeddable
@Access(AccessType.FIELD)
public class Mensagem {
	@Column(name="CODIGO")
	private int codigo;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	public Mensagem(int codigo, String descricao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public String descricao(){
		return descricao;
	}

	public boolean notaAutorizada(){
		return codigo() == 100 || codigo() == 150;
	}
	
	public boolean notaDenegada(){
		return codigo() == 110 || codigo() == 301 || codigo() == 302;
	}
	
	public boolean loteEventoProcessado(){
		return codigo() == 128;
	}
	public static Mensagem mensagemCancelamentoHomologado(){
		return  new Mensagem(101,"Cancelamento de NF-e homologado");
	}
	
	public boolean inutilizacaoHomologada(){
		return codigo() == 102;
	}
	
	public static Mensagem autorizadoUsoDaNFe(){
		return new Mensagem(100,"Autorizado o uso da NF-e");
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Mensagem objetoTipado = (Mensagem) objeto;
			objetosIguais = this.codigo() == objetoTipado.codigo() 
					&& this.descricao().equals(objetoTipado.descricao());
		} 

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (6844 * 3) 
				+ Integer.valueOf(codigo()).hashCode()
				+ descricao().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return "Mensagem [codigo=" + codigo() 
				+", descricao="+descricao()
				+"]";
	}
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Mensagem(){}
}
