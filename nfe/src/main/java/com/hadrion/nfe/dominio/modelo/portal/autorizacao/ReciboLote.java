package com.hadrion.nfe.dominio.modelo.portal.autorizacao;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.lote.NumeroReciboLote;

public class ReciboLote {
	private NumeroReciboLote numero;
	
	public ReciboLote(NumeroReciboLote numero){
		this.numero = numero;
	}
	
	public NumeroReciboLote numero(){
		return numero;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			ReciboLote objetoTipado = (ReciboLote) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero(),objetoTipado.numero())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(125,911)
			.append(numero())
			.toHashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(numero());
	}
}
