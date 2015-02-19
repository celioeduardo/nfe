package com.hadrion.nfe.dominio.modelo.nf.cobranca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Cobranca {
	
	@Embedded
	private Fatura fatura;
	
	@ElementCollection
	@CollectionTable(name="DUPLICATAS")
	private List<Duplicata> duplicatas;
	
	public Cobranca(Fatura fatura, List<Duplicata> duplicatas) {
		super();
		this.fatura = fatura;
		if (duplicatas != null )
			this.getDuplicatas().addAll(duplicatas);
	}
	
	public Cobranca(Fatura fatura, Duplicata... duplicatas) {
		this(fatura, Arrays.asList(duplicatas));
	}

	public Fatura fatura() {
		return fatura;
	}

	private List<Duplicata> getDuplicatas() {
		if (duplicatas == null)
			duplicatas = new ArrayList<Duplicata>();
		return duplicatas;
	}
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Cobranca objetoTipado = (Cobranca) objeto;
			objetosIguais = new EqualsBuilder()
				.append(fatura,objetoTipado.fatura)
				.append(duplicatas,objetoTipado.duplicatas)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(789361,3091)
			.append(fatura)
			.append(duplicatas)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Cobranca [fatura=" + fatura
				+ ",duplicatas="+ duplicatas
				+ "]";
	}

	public int quantidadeDuplicatas() {
		return getDuplicatas().size();
	}

	public Duplicata obterDuplicata(int i) {
		return getDuplicatas().get(i-1);
	}

	public Iterable<Duplicata> duplicatas() {
		return getDuplicatas();
	} 
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Cobranca(){}
}
