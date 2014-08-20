package com.hadrion.nfe.dominio.modelo.nf.item;

import com.hadrion.nfe.dominio.modelo.nf.item.imposto.Imposto;


public class Item {
	private DescritorProduto produto;
	private Imposto imposto; 
	
	public Item(DescritorProduto produto) {
		super();
		this.produto = produto;
	} 
	
	public DescritorProduto produto(){
		return produto;
	}
}
