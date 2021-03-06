package com.hadrion.nfe.tipos;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
@Access(AccessType.FIELD)
public class Aliquota {
	public final static Aliquota ZERO = new Aliquota(0.0);
	
	@Column(name="ALIQUOTA")
	private Double valor;

	public Aliquota(Double valor) {
		super();
		this.valor = valor;
	}
	
	public Aliquota(int valor) {
		super();
		this.valor = (double)valor;
	}
	
	public Double valor(){
		return valor;
	}

	public boolean igualAZero(){
		return valor.equals(0.0);
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Aliquota objetoTipado = (Aliquota) objeto;
			objetosIguais = this.valor().equals(objetoTipado.valor());
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (47 * 39) 
				+ this.valor().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return String.valueOf(valor);
	}

	public Double valorDecimal() {
		return new BigDecimal(valor).divide(new BigDecimal(100)).doubleValue();
	}
	
	@SuppressWarnings("unused")
	private Aliquota(){}
}
