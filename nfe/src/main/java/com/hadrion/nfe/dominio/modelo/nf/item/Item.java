package com.hadrion.nfe.dominio.modelo.nf.item;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.nf.item.imposto.Imposto;
import com.hadrion.nfe.tipos.Dinheiro;

@Entity
@SequenceGenerator(name="SEQ",sequenceName="SQ_ITEM")
@Table(name="ITEM")
public class Item {
	@Embedded
	private DescritorProduto produto;
	
	@Embedded
	private Imposto imposto;
	
	@Column(name="INF_ADIC")
	private String informacaoAdicional;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
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
	
	public Item mesclar(Item item){
		if (equals(item)) return this;
		this.produto = item.produto;
		this.imposto = item.imposto;
		this.informacaoAdicional = item.informacaoAdicional;
		return this;
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Item(){}
	
	public Long getId(){
		return this.id;
	}

	public Dinheiro valorDesconto() {
		return this.produto().valorDesconto().soma(this.imposto().valorRbc());
	}
	
}
