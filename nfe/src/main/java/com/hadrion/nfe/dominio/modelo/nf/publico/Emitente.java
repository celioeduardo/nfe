package com.hadrion.nfe.dominio.modelo.nf.publico;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Telefone;

@Embeddable
@Access(AccessType.FIELD)
public class Emitente {
	
	@Embedded
	@AttributeOverride(name="numero", column=@Column(name="EMIT_CNPJ"))
	private Cnpj cnpj;
	
	@Embedded
	@AttributeOverride(name="numero", column=@Column(name="EMIT_CPF"))
	private Cpf cpf;
	
	@Column(name="EMIT_RAZAO_SOCIAL")
	private String razaoSocial;
	
	@Column(name="EMIT_NOME_FANTASIA")
	private String nomeFantasia;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="logradouro", column=@Column(name="EMIT_END_LOGRADOURO")),
		@AttributeOverride(name="numero", column=@Column(name="EMIT_END_NUMERO")),
		@AttributeOverride(name="complemento", column=@Column(name="EMIT_END_COMPLEMENTO")),
		@AttributeOverride(name="bairro", column=@Column(name="EMIT_END_BAIRRO")),
		@AttributeOverride(name="municipio.codigo", column=@Column(name="EMIT_END_MUN_CODIGO")),
		@AttributeOverride(name="municipio.nome", column=@Column(name="EMIT_END_MUN_NOME")),
		@AttributeOverride(name="municipio.uf", column=@Column(name="EMIT_END_MUN_UF")),
		@AttributeOverride(name="pais.codigo", column=@Column(name="EMIT_END_PAIS_CODIGO")),
		@AttributeOverride(name="pais.nome", column=@Column(name="EMIT_END_PAIS_NOME")),
		@AttributeOverride(name="cep.numero", column=@Column(name="EMIT_END_CEP")),
		@AttributeOverride(name="telefone.numero", column=@Column(name="EMIT_END_TELEFONE")),		
	})
	private Endereco endereco;
	
	@Embedded
	@AttributeOverride(name="numero", column=@Column(name="EMIT_TELEFONE"))
	private Telefone telefone;
	
	@Embedded
	@AttributeOverride(name="numero", column=@Column(name="EMIT_IE"))
	private InscricaoEstadual ie;
	
	@Embedded
	@AttributeOverride(name="numero", column=@Column(name="EMIT_IE_SUBS"))
	private InscricaoEstadual ieSubstituto;
	
	@Enumerated(EnumType.STRING)
	@Column(name="EMIT_CRT")
	private Crt crt;
	
	public Emitente(Cnpj cnpj, Cpf cpf, String razaoSocial,
			String nomeFantasia, Endereco endereco, Telefone telefone,
			InscricaoEstadual ie, InscricaoEstadual ieSubstituto,Crt crt) {
		super();
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.endereco = endereco;
		this.telefone = telefone;
		this.ie = ie;
		this.ieSubstituto = ieSubstituto;
		this.crt = crt;
	}

	public Cnpj cnpj() {
		return cnpj;
	}

	public Cpf cpf() {
		return cpf;
	}

	public String razaoSocial() {
		return razaoSocial;
	}

	public String nomeFantasia() {
		return nomeFantasia;
	}

	public Endereco endereco() {
		return endereco;
	}

	public Telefone telefone() {
		return telefone;
	}

	public InscricaoEstadual ie() {
		return ie;
	}

	public InscricaoEstadual ieSubstituto() {
		return ieSubstituto;
	}
	
	public Crt crt(){
		return crt;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Emitente objetoTipado = (Emitente) objeto;
			objetosIguais = new EqualsBuilder()
				.append(cnpj,objetoTipado.cnpj)
				.append(cpf,objetoTipado.cpf)
				.append(razaoSocial,objetoTipado.razaoSocial)
				.append(nomeFantasia,objetoTipado.nomeFantasia)
				.append(endereco,objetoTipado.endereco)
				.append(telefone,objetoTipado.telefone)
				.append(ie,objetoTipado.ie)
				.append(ieSubstituto,objetoTipado.ieSubstituto)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13157,19317)
			.append(cnpj)
			.append(cpf)
			.append(razaoSocial)
			.append(nomeFantasia)
			.append(endereco)
			.append(telefone)
			.append(ie)
			.append(ieSubstituto)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Emitente [cnpj=" + cnpj
				+ ",cpf="+ cpf
				+ ",razaoSocial="+ razaoSocial
				+ ",nomeFantasia="+ nomeFantasia
				+ ",endereco="+ endereco
				+ ",telefone="+ telefone
				+ ",ie="+ ie
				+ ",ieSubstituto="+ ieSubstituto
				+ "]";
	} 
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Emitente(){}
}
