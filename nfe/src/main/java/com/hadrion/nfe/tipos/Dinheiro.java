package com.hadrion.nfe.tipos;

import java.math.BigDecimal;
import java.math.MathContext;


public class Dinheiro {
	public final static Dinheiro ZERO = new Dinheiro(0.0);
	public final static Dinheiro UM = new Dinheiro(1);
	private final static int digitosFracionarios = 2;
	private final static int fatorCentavos = (int) Math.pow(10,digitosFracionarios);
	private Long quantia;

	public Dinheiro(Double valor) {
		super();
		this.quantia = Math.round(valor * fatorCentavos);
	}
	public Dinheiro(BigDecimal valor) {
		super();
		this.quantia = valor.multiply(new BigDecimal(fatorCentavos)).longValue();
	}
	public Dinheiro(int valor) {
		super();
		this.quantia = (long) Math.round(valor * fatorCentavos);
	}
	
	private Dinheiro(){}	
	
	private Dinheiro novoDinheiro(Long quantia){
		Dinheiro dinheiro = new Dinheiro();
		dinheiro.quantia = quantia;
		return dinheiro;
	}
	
	private BigDecimal quantia(){
		return BigDecimal.valueOf(quantia,digitosFracionarios);
	}
	
	public Double valor(){
		return (double) (quantia / fatorCentavos);
	}
	public Dinheiro soma(Dinheiro valor) {
		return novoDinheiro(quantia + valor.quantia);
	}
	public Dinheiro multiplicar(Double fator) {
		return multiplicar(new BigDecimal(fator));
	}
	public Dinheiro multiplicar(BigDecimal quantia){
		return new Dinheiro(quantia().multiply(quantia,MathContext.DECIMAL64));
	}
	
	public Dinheiro multiplicar(BigDecimal quantia, MathContext mathContext){
		return new Dinheiro(quantia().multiply(quantia,mathContext));
	}
	
	public Dinheiro subtrair(Dinheiro valor) {
		return novoDinheiro(quantia - valor.quantia);
	}
	
	public boolean igualAZero(){
		return quantia.equals(0.0);
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Dinheiro objetoTipado = (Dinheiro) objeto;
			objetosIguais = this.valor().equals(objetoTipado.valor());
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (45 * 39) 
				+ this.valor().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return new BigDecimal(quantia).divide(new BigDecimal(fatorCentavos)).toString();
		
	}
}
