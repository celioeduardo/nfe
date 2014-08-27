package com.hadrion.nfe.dominio.modelo.nf.referencia;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public abstract class NotaFiscalReferencia {
	private TipoReferencia tipo;
	protected NotaFiscalReferencia(TipoReferencia tipo) {
		this.tipo = tipo;
	}
	
	public static NotaFiscalReferencia referenciar(TipoReferencia tipo){
		switch (tipo){
			case COMPLEMENTO:
				return new Complemento();
			case DEVOLUCAO:
				return new Devolucao();
			case SUBSTITUICAO_CANCELADA:
				return new SubstituicaoCancelamento();
		}
		throw new RuntimeException("Deveria ser inacessível");
	}	
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			NotaFiscalReferencia objetoTipado = (NotaFiscalReferencia) objeto;
			objetosIguais = new EqualsBuilder()
				.append(tipo, objetoTipado.tipo)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(3249,1367)
			.append(tipo)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "NotaFiscalReferencia [tipo=" 
				+ tipo.toString() 
				+ "]";
	}
	
	public TipoReferencia tipo(){
		return tipo;
	}
}
