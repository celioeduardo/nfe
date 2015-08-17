package com.hadrion.comum.paginacao;

public class Paginacao {
	
	public final static Paginacao DEFAULT = new Paginacao(1, 25, 0);
	
	private int pagina;
	private int inicio;
	private int limite;
	private Ordenacao ordenacao;
	
	public Paginacao(int inicio, int limite, int pagina) {
		super();
		this.pagina = pagina;
		this.inicio = inicio;
		this.limite = limite;
	}
	
	public Paginacao(int inicio, int limite, int pagina, Ordenacao ordenacao) {
		this(inicio,limite,pagina);
		this.ordenacao = ordenacao;
	}
	
	

	public int inicio(){
		return inicio;
	}
	
	public int pagina(){
		return pagina;
	}
	
	public int limite(){
		return limite;
	}
	
	public Ordenacao ordenacao(){
		return ordenacao;
	}
}
