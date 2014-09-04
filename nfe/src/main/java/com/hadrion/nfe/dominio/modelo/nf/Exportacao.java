package com.hadrion.nfe.dominio.modelo.nf;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;

public class Exportacao {
	private Uf uf;
	private String localEmbarque;
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
	
}
