package com.hadrion.nfe.dominio.modelo.nf.informacao;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Informacao {
	@Column(name="INFORMACAO")
	private String texto;
	
	@ElementCollection
	@CollectionTable(name="OBSERVACOES")
	@Column(name="OBSERVACAO")
	private List<Observacao> observacoes=null;
	
	public Informacao(){
		
	}
	public Informacao(String texto, List<Observacao> observacoes) {
		super();
		this.texto = texto;
		this.observacoes = observacoes;
	}

	public String texto() {
		return texto;
	}

	public List<Observacao> observacoes() {
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
