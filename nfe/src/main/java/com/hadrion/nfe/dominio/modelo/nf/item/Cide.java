package com.hadrion.nfe.dominio.modelo.nf.item;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;

@Embeddable
@Access(AccessType.FIELD)
public class Cide {
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="CIDE_BASE_CALCULO"))
	private Dinheiro baseCalculo;
	
	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="CIDE_ALIQUOTA"))
	private Aliquota aliquota;
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="CIDE_VALOR"))
	private Dinheiro valor;
	
	public Cide(Dinheiro baseCalculo, Aliquota aliquota, Dinheiro valor) {
		super();
		if (baseCalculo.igualAZero())
			throw new IllegalArgumentException("Base de cálculo não pode ser zero.");
		if (baseCalculo.menorQueZero())
			throw new IllegalArgumentException("Base de cálculo não pode ser negativa.");
		this.baseCalculo = baseCalculo;
		this.aliquota = aliquota;
		this.valor = valor;
	}
	
	public Dinheiro baseCalculo(){
		return baseCalculo;
	}
	
	public Aliquota aliquota(){
		return aliquota;
	}
	
	public Dinheiro valor(){
		return valor;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Cide objetoTipado = (Cide) objeto;
			objetosIguais = new EqualsBuilder()
				.append(baseCalculo(),objetoTipado.baseCalculo())
				.append(aliquota(),objetoTipado.aliquota())
				.append(valor(),objetoTipado.valor())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(1475,271)
			.append(baseCalculo())
			.append(aliquota())
			.append(valor())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Cide [baseCalculo=" + baseCalculo()
				+",aliquota = " + aliquota()
				+",valor = " + valor()
				+ "]";
	} 
	
	@SuppressWarnings("unused")
	private Cide(){}

}
