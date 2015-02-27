package com.hadrion.nfe.tipos;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Percentual {
	
	public final static Percentual ZERO = new Percentual(0.0);
	public final static Percentual CEM = new Percentual(100);
	
	@Column(name="PERCENTUAL")
	private Double valor;

	public Percentual(Double valor) {
		super();
		this.valor = valor;
	}
	
	public Percentual(int valor) {
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
			Percentual objetoTipado = (Percentual) objeto;
			objetosIguais = this.valor().equals(objetoTipado.valor());
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (31 * 319) 
				+ this.valor().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return String.valueOf(valor);
	}

	public Double valorDecimal() {
		return valorDecimalComoBigDecimal().doubleValue();
	}
	/**
	 * 1 - valorDecimal()
	 * @return
	 */
	public Double valorComplementarDecimal() {
		return BigDecimal.ONE.subtract(valorDecimalComoBigDecimal()).doubleValue();
	}
	
	public BigDecimal valorComplementarDecimalComoBigDecimal() {
		return BigDecimal.ONE.subtract(valorDecimalComoBigDecimal());
	}
	public BigDecimal valorDecimalComoBigDecimal() {
		return new BigDecimal(valor).divide(new BigDecimal(100));
	}
	
	@SuppressWarnings("unused")
	private Percentual(){}

	public boolean maiorQueZero() {
		return valor > 0;
	}
}
