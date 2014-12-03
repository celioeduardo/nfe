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
public class LocalRetirada {
	@Embedded
	@AttributeOverride(name="numero", column=@Column(name="RET_CNPJ"))
	private Cnpj cnpj;
	
	@Embedded
	@AttributeOverride(name="numero", column=@Column(name="RET_CPF"))
	private Cpf cpf;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="logradouro", column=@Column(name="RET_END_LOGRADOURO")),
		@AttributeOverride(name="numero", column=@Column(name="RET_END_NUMERO")),
		@AttributeOverride(name="complemento", column=@Column(name="RET_END_COMPLEMENTO")),
		@AttributeOverride(name="bairro", column=@Column(name="RET_END_BAIRRO")),
		@AttributeOverride(name="municipio.codigo", column=@Column(name="RET_END_MUN_CODIGO")),
		@AttributeOverride(name="municipio.nome", column=@Column(name="RET_END_MUN_NOME")),
		@AttributeOverride(name="municipio.uf", column=@Column(name="RET_END_MUN_UF")),
		@AttributeOverride(name="pais.codigo", column=@Column(name="RET_END_PAIS_CODIGO")),
		@AttributeOverride(name="pais.nome", column=@Column(name="RET_END_PAIS_NOME")),
		@AttributeOverride(name="cep.numero", column=@Column(name="RET_END_CEP")),
		@AttributeOverride(name="telefone.numero", column=@Column(name="RET_END_TELEFONE")),		
	})
	private Endereco endereco;
	
	public LocalRetirada(Cnpj cnpj, Cpf cpf, Endereco endereco) {
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
			LocalRetirada objetoTipado = (LocalRetirada) objeto;
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
		return new HashCodeBuilder(1313,6969)
			.append(cnpj)
			.append(cpf)
			.append(endereco)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "LocalRetirada [cnpj=" + cnpj
				+ ",cpf="+ cpf
				+ ",endereco="+ endereco
				+ "]";
	} 
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private LocalRetirada(){}
	
}
