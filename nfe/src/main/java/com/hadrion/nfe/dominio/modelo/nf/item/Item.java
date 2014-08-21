package com.hadrion.nfe.dominio.modelo.nf.item;

import com.hadrion.nfe.dominio.modelo.nf.item.imposto.Imposto;


public class Item {
	private DescritorProduto produto;
	private Imposto imposto; 
	private String informacaoAdicional;
	
	public Item(DescritorProduto produto, Imposto imposto,
			String informacaoAdicional) {
		super();
		this.produto = produto;
		this.imposto = imposto;
		this.informacaoAdicional = informacaoAdicional;
	}

	public DescritorProduto produto(){
		return produto;
	}
	
	public Imposto imposto(){
		if (imposto == null)
			return Imposto.NULO;
		return imposto;
	}
	
	public String informacaoAdicional(){
		return informacaoAdicional;
	}
}
