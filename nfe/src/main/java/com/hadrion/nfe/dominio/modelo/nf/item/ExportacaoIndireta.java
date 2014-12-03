package com.hadrion.nfe.dominio.modelo.nf.item;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.tipos.Quantidade;

@Embeddable
@Access(AccessType.FIELD)
public class ExportacaoIndireta {
	
	@Column(name="NUMERO_REGISTRO")
	private Long numeroRegistro;
	
	@Embedded
	private ChaveAcesso chaveAcesso;
	
	@Embedded
	@AttributeOverride(name="quantidade", column=@Column(name="QUANTIDADE_EXP"))
	private Quantidade quantidadeExportada;
	
	public ExportacaoIndireta(Long numeroRegistro, ChaveAcesso chaveAcesso, Quantidade quantidadeExportada) {
		super();
		this.numeroRegistro = numeroRegistro;
		this.chaveAcesso = chaveAcesso;
		this.quantidadeExportada = quantidadeExportada;
	}

	public Long numeroRegistro() {
		return numeroRegistro;
	}

	public ChaveAcesso chaveAcesso() {
		return chaveAcesso;
	}
	
	public Quantidade quantidadeExportada(){
		return quantidadeExportada;
	}
	
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			ExportacaoIndireta objetoTipado = (ExportacaoIndireta) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numeroRegistro(),objetoTipado.numeroRegistro())
				.append(chaveAcesso(),objetoTipado.chaveAcesso())
				.append(quantidadeExportada(),objetoTipado.quantidadeExportada)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(9967,17)
			.append(numeroRegistro())
			.append(chaveAcesso())
			.append(quantidadeExportada())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "ExportacaoIndireta [numeroRegistro=" + numeroRegistro()
				+ ",chaveAcesso="+ chaveAcesso()
				+ ",quantidadeExportada="+ quantidadeExportada()
				+ "]";
	} 
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private ExportacaoIndireta(){}
	
}
