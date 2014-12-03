package com.hadrion.nfe.dominio.modelo.nf.publico;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.Email;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Telefone;

public class Destinatario {

	@Embedded
	@AttributeOverride(name="numero", column=@Column(name="DEST_CNPJ"))
	private Cnpj cnpj;
	
	@Embedded
	@AttributeOverride(name="numero", column=@Column(name="DEST_CPF"))
	private Cpf cpf;
	
	@Column(name="DEST_ID_ESTRANGEIRO")
	private String idEstrangeiro;
	
	@Column(name="DEST_RAZAO_SOCIAL")
	private String razaoSocial;
	
	@Column(name="DEST_NOME_FANTASIA")
	private String nomeFantasia;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="logradouro", column=@Column(name="DEST_END_LOGRADOURO")),
		@AttributeOverride(name="numero", column=@Column(name="DEST_END_NUMERO")),
		@AttributeOverride(name="complemento", column=@Column(name="DEST_END_COMPLEMENTO")),
		@AttributeOverride(name="bairro", column=@Column(name="DEST_END_BAIRRO")),
		@AttributeOverride(name="municipio.codigo", column=@Column(name="DEST_END_MUN_CODIGO")),
		@AttributeOverride(name="municipio.nome", column=@Column(name="DEST_END_MUN_NOME")),
		@AttributeOverride(name="municipio.uf", column=@Column(name="DEST_END_MUN_UF")),
		@AttributeOverride(name="pais.codigo", column=@Column(name="DEST_END_PAIS_CODIGO")),
		@AttributeOverride(name="pais.nome", column=@Column(name="DEST_END_PAIS_NOME")),
		@AttributeOverride(name="cep.numero", column=@Column(name="DEST_END_CEP")),
		@AttributeOverride(name="telefone.numero", column=@Column(name="DEST_END_TELEFONE")),		
	})
	private Endereco endereco;
	
	@Embedded
	@AttributeOverride(name="numero", column=@Column(name="DEST_TELEFONE"))
	private Telefone telefone;
	
	@Enumerated(EnumType.STRING)
	@Column(name="DEST_IND_IE")
	private IndicadorIe indicadorIe;
	
	@Embedded
	@AttributeOverride(name="numero", column=@Column(name="DEST_IE"))
	private InscricaoEstadual ie;
	
	@Column(name="DEST_INSCRICAO_SUFRAMA")
	private Long inscricaoSuframa;
	
	@Embedded
	@AttributeOverride(name="email", column=@Column(name="DEST_EMAIL"))
	private Email email;
	
	public Destinatario(Cnpj cnpj, Cpf cpf, String idEstrangeiro,
			String razaoSocial, String nomeFantasia, Endereco endereco,
			Telefone telefone, IndicadorIe indicadorIe, InscricaoEstadual ie,
			Long inscricaoSuframa, Email email) {
		super();
		if (cnpj!=null){
			this.cnpj = cnpj;
			this.cpf = null;
		} else {
			this.cnpj = null;
			this.cpf = cpf;			
		}
		this.idEstrangeiro = idEstrangeiro;
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.endereco = endereco;
		this.telefone = telefone;
		this.indicadorIe = indicadorIe;
		this.ie = ie;
		this.inscricaoSuframa = inscricaoSuframa;
		this.email = email;
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

	public String idEstrangeiro(){
		return idEstrangeiro;
	}
	public IndicadorIe indicadorIe(){
		return indicadorIe;
	}
	public Long inscricaoSuframa(){
		return inscricaoSuframa;
	}
	public Email email(){
		return email;
	}
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Destinatario objetoTipado = (Destinatario) objeto;
			objetosIguais = new EqualsBuilder()
				.append(cnpj,objetoTipado.cnpj)
				.append(cpf,objetoTipado.cpf)
				.append(idEstrangeiro,objetoTipado.idEstrangeiro)
				.append(razaoSocial,objetoTipado.razaoSocial)
				.append(nomeFantasia,objetoTipado.nomeFantasia)
				.append(endereco,objetoTipado.endereco)
				.append(telefone,objetoTipado.telefone)
				.append(indicadorIe,objetoTipado.indicadorIe)
				.append(ie,objetoTipado.ie)
				.append(inscricaoSuframa,objetoTipado.inscricaoSuframa)
				.append(email,objetoTipado.email)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(169,7723)
			.append(cnpj)
			.append(cpf)
			.append(idEstrangeiro)
			.append(razaoSocial)
			.append(nomeFantasia)
			.append(endereco)
			.append(telefone)
			.append(indicadorIe)
			.append(ie)
			.append(inscricaoSuframa)
			.append(email)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Destinatario [cnpj=" + cnpj
				+ ",cpf="+ cpf
				+ ",idEstrangeiro="+ idEstrangeiro
				+ ",razaoSocial="+ razaoSocial
				+ ",nomeFantasia="+ nomeFantasia
				+ ",endereco="+ endereco
				+ ",telefone="+ telefone
				+ ",IndicadorIe="+ indicadorIe
				+ ",ie="+ ie
				+ ",inscricaoSuframa="+ inscricaoSuframa
				+ ",email="+ email
				+ "]";
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Destinatario(){}
}
