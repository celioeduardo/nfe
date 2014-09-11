package com.hadrion.nfe.dominio.modelo.nf.item;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
	

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Item objetoTipado = (Item) objeto;
			objetosIguais = new EqualsBuilder()
				.append(informacaoAdicional(), objetoTipado.informacaoAdicional())
				.append(produto(), objetoTipado.produto())
				.append(imposto(), objetoTipado.imposto())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(7171,39)
		.append(informacaoAdicional())
		.append(produto())
		.append(imposto())
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Item [informacaoAdicional=" + informacaoAdicional() 
			+ ",produto=" + produto()
			+ ",imposto=" + imposto()
			+ "]";
	}
}
