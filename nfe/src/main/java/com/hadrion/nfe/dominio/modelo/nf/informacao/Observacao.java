package com.hadrion.nfe.dominio.modelo.nf.informacao;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Observacao {
	
	@Column(name="CAMPO")
	private String campo;
	
	@Column(name="CONTEUDO")	
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
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Observacao objetoTipado = (Observacao) objeto;
			objetosIguais = new EqualsBuilder()
				.append(campo(), objetoTipado.campo())
				.append(conteudo(), objetoTipado.conteudo())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(45977,87)
		.append(campo())
		.append(conteudo())
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Observacao [campo=" + campo()
				+ ",conteudo=" + conteudo()
				+ "]";	
	}
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Observacao(){}

}
