package com.hadrion.nfe.dominio.modelo.nf;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;

@Embeddable
@Access(AccessType.FIELD)
public class Exportacao {
	@Enumerated(EnumType.STRING)
	@Column(name="EXP_UF")
	private Uf uf;
	
	@Column(name="EXP_EMBARQUE")
	private String localEmbarque;
	
	@Column(name="EXP_DESPACHO")
	private String localDespacho;
	
	public Exportacao(Uf uf, String localEmbarque, String localDespacho) {
		super();
		this.uf = uf;
		this.localEmbarque = localEmbarque;
		this.localDespacho = localDespacho;
	}
	public Uf uf() {
		return uf;
	}
	public String localEmbarque() {
		return localEmbarque;
	}
	public String localDespacho() {
		return localDespacho;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Exportacao objetoTipado = (Exportacao) objeto;
			objetosIguais = new EqualsBuilder()
				.append(uf,objetoTipado.uf)
				.append(localEmbarque,objetoTipado.localEmbarque)
				.append(localDespacho,objetoTipado.localDespacho)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(3341,457)
			.append(uf)
			.append(localEmbarque)
			.append(localDespacho)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Exportacao [uf=" + uf
				+ ",localEmbarque="+ localEmbarque
				+ ",localDespacho="+ localDespacho
				+ "]";
	}	
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Exportacao(){}
}
