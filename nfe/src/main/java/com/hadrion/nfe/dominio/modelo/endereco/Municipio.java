package com.hadrion.nfe.dominio.modelo.endereco;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;

@Embeddable
@Access(AccessType.FIELD)
public class Municipio {
	
	@Column(name="MUNICIPIO_CODIGO")
	private int codigo;
	
	@Column(name="MUNICIPIO_NOME")
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@Column(name="MUNICIPIO_UF")
	private Uf uf;
	
	public Municipio(int codigo, String nome, Uf uf) {
		super();
		this.codigo = codigo;
		this.nome = StringUtils.trim(nome);
		this.uf = uf;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public String nome(){
		return nome;
	}
	
	public Uf uf(){
		return uf;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Municipio objetoTipado = (Municipio) objeto;
			objetosIguais = new EqualsBuilder()
				.append(nome, objetoTipado.nome)
				.append(uf, objetoTipado.uf)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(633,3147)
			.append(nome)
			.append(uf)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Municipio [codigo="+ codigo
				+",nome="+nome
				+",uf="+uf
				+ "]";
	}
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Municipio(){}
	
}
