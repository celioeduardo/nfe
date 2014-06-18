package com.hadrion.nfe.dominio.modelo.inutillizacao;


public class Faixa {
	private int numeracaoInicial;
	private int numeracaoFinal;
	
	public Faixa(int numeracaoInicial, int numeracaoFinal) {
		setNumeracaoInicial(numeracaoInicial);
		setNumeracaoFinal(numeracaoFinal);
		assertIntervalo();
	}
	
	private void setNumeracaoInicial(int numeracaoInicial) {
		this.numeracaoInicial = numeracaoInicial;
	}

	private void assertIntervalo() {
		if ((this.numeracaoFinal < numeracaoInicial) ||
				(this.numeracaoFinal <= 0)) 
			throw new IllegalArgumentException("Intervalo Inválido");	
	}

	private void setNumeracaoFinal(int numeracaoFinal) {
		this.numeracaoFinal = numeracaoFinal;
	}

	public int numeracaoInicial(){
		return this.numeracaoInicial;
	}
	public int numeracaoFinal(){
		return this.numeracaoFinal;
	}
}