package com.hadrion.nfe.dominio.modelo.nf.informacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Informacao {
	
	@Column(name="TEXTO")
	private String texto;
	
	@OneToMany(orphanRemoval=true,cascade=CascadeType.ALL)
	@JoinTable(name="OBSERVACOES",
	    joinColumns=@JoinColumn(name="ID_NF"),
	    inverseJoinColumns=@JoinColumn(name="ID_OBSERVACAO"))
	private List<Observacao> observacoes;
	
	public Informacao(){
		
	}
	public Informacao(String texto, Observacao ... observacoes) {
		super();
		this.texto = texto;
		this.observacoes = observacoes != null ? Arrays.asList(observacoes) : null;
	}

	public String texto() {
		return texto;
	}

	public List<Observacao> observacoes() {
		return getObservacoes();
	}
	
	private List<Observacao> getObservacoes() {
		if (observacoes == null)
			observacoes = new ArrayList<Observacao>();
		return observacoes;
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Informacao objetoTipado = (Informacao) objeto;
			objetosIguais = new EqualsBuilder()
				.append(texto(), objetoTipado.texto())
				.append(observacoes(), objetoTipado.observacoes())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(477,371)
		.append(texto())
		.append(observacoes())
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Informacao [texto=" + texto()
				+ ",observacoes=" + observacoes()
				+ "]";	
	}
}
