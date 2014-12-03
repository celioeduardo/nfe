package com.hadrion.nfe.dominio.modelo.nf.locais;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
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
	@AttributeOverride(name="numero", column=@Column(name="ENTR_CNPJ"))
	private Cnpj cnpj;
	
	@Embedded
	@AttributeOverride(name="numero", column=@Column(name="ENTR_CPF"))
	private Cpf cpf;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="logradouro", column=@Column(name="ENTR_END_LOGRADOURO")),
		@AttributeOverride(name="numero", column=@Column(name="ENTR_END_NUMERO")),
		@AttributeOverride(name="complemento", column=@Column(name="ENTR_END_COMPLEMENTO")),
		@AttributeOverride(name="bairro", column=@Column(name="ENTR_END_BAIRRO")),
		@AttributeOverride(name="municipio.codigo", column=@Column(name="ENTR_END_MUN_CODIGO")),
		@AttributeOverride(name="municipio.nome", column=@Column(name="ENTR_END_MUN_NOME")),
		@AttributeOverride(name="municipio.uf", column=@Column(name="ENTR_END_MUN_UF")),
		@AttributeOverride(name="pais.codigo", column=@Column(name="ENTR_END_PAIS_CODIGO")),
		@AttributeOverride(name="pais.nome", column=@Column(name="ENTR_END_PAIS_NOME")),
		@AttributeOverride(name="cep.numero", column=@Column(name="ENTR_END_CEP")),
		@AttributeOverride(name="telefone.numero", column=@Column(name="ENTR_END_TELEFONE")),		
	})
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
