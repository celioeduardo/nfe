package com.hadrion.nfe.dominio.modelo.portal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Campo de uso da SEFAZ para enviar mensagem de interesse da SEFAZ para o emissor. (NT 2011/004)
 * @author celioeduardo
 *
 */
@Embeddable
@Access(AccessType.FIELD)
public class MensagemSefaz {
	
	@Column(name="CODIGO")
	private int codigo;

	@Column(name="DESCRICAO")
	private String descricao;
	
	public static MensagemSefaz vazia(){
		return new MensagemSefaz(-1, "");
	}
	
	public MensagemSefaz(int codigo, String descricao) {
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
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			MensagemSefaz objetoTipado = (MensagemSefaz) objeto;
			objetosIguais = this.codigo() == objetoTipado.codigo() 
					&& this.descricao().equals(objetoTipado.descricao());
		} 

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (8734 * 9) 
				+ Integer.valueOf(codigo()).hashCode()
				+ descricao().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return "MensagemSefaz [codigo=" + codigo() + ", descrição="+descricao()+"]";
	}
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private MensagemSefaz(){}
}
