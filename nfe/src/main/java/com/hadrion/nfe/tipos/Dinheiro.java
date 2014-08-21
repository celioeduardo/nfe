package com.hadrion.nfe.tipos;


public class Dinheiro {
	public final static Dinheiro ZERO = new Dinheiro(0.0);
	
	private Double valor;

	public Dinheiro(Double valor) {
		super();
		this.valor = valor;
	}
	public Dinheiro(int valor) {
		super();
		this.valor = (double)valor;
	}
	
	public Double valor(){
		return valor;
	}

	public Dinheiro multiplicar(Double aliquota) {
		return new Dinheiro(valor * aliquota);
	}
	
	public Dinheiro subtrair(Dinheiro valor) {
		return new Dinheiro(valor() - valor.valor);
	}
	public Dinheiro soma(Dinheiro valor) {
		return new Dinheiro(valor() + valor.valor());
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
		return "Dinheiro [valor=" + valor() + "]";
	}
}
