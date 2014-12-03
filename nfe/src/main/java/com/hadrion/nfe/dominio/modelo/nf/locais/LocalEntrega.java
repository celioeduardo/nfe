package com.hadrion.nfe.dominio.modelo.nf.locais;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;

@Embeddable
@Access(AccessType.FIELD)
public class LocalEntrega {
	@Embedded
	private Cnpj cnpj;
	
	@Embedded
	private Cpf cpf;
	
	@Embedded
	private Endereco endereco;
	
	public LocalEntrega(Cnpj cnpj, Cpf cpf, Endereco endereco) {
		super();
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.endereco = endereco;
	}
	
	public Cnpj cnpj(){
		return cnpj;
	}
	
	public Cpf cpf(){
		return cpf;
	}
	
	public Endereco endereco(){
		return endereco;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			LocalEntrega objetoTipado = (LocalEntrega) objeto;
			objetosIguais = new EqualsBuilder()
				.append(cnpj,objetoTipado.cnpj)
				.append(cpf,objetoTipado.cpf)
				.append(endereco,objetoTipado.endereco)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(1697,7975)
			.append(cnpj)
			.append(cpf)
			.append(endereco)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "LocalEntrega [cnpj=" + cnpj
				+ ",cpf="+ cpf
				+ ",endereco="+ endereco
				+ "]";
	} 
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private LocalEntrega(){}
}
