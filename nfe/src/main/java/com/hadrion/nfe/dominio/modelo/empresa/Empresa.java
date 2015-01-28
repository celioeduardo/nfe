package com.hadrion.nfe.dominio.modelo.empresa;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.tipos.Cnpj;

@Entity
@SequenceGenerator(name="SEQ", sequenceName="SQ_EMPRESA")
@Table(name="EMPRESA")
public class Empresa {
	
	@Embedded
	private EmpresaId empresaId;
	
	@Column(name="NOME")
	private String nome;
	
	@Embedded
	private Cnpj cnpj;
	
	@Embedded
	private Certificado certificado;

	@Lob
	private byte[] logo;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
	@Column(name="APELIDO")
	private String apelido;
	
	public Empresa(EmpresaId empresaId,
			String nome, Cnpj cnpj, String apelido, 
			Certificado certificado,
			InputStream logo) {
		this.empresaId = empresaId;
		this.nome = nome;
		this.cnpj = cnpj;
		this.apelido = apelido;
		this.certificado = certificado;
		this.logo = streamToByteArray(logo);
	}	
	public Empresa(EmpresaId empresaId,
			String nome, Cnpj cnpj, String apelido, 
			Certificado certificado,
			File logo) {
		this.empresaId = empresaId;
		this.nome = nome;
		this.cnpj = cnpj;
		this.apelido = apelido;
		this.certificado = certificado;
		try {
			this.logo = streamToByteArray(FileUtils.openInputStream(logo));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}	
	
	private byte[] streamToByteArray(InputStream is){
		if (is == null)
			return null;
		try {
			return IOUtils.toByteArray(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public EmpresaId empresaId(){
		return empresaId;
	}
	
	public String nome() {
		return nome;
	}
	
	public Cnpj cnpj(){
		return cnpj;
	}
	
	public Certificado certificado(){
		return certificado;
	}

	public void renomear(String novoNome) {
		this.nome = novoNome;
	}
	
	public String apelido(){
		return apelido;
	}
	
	public byte[] logo(){
		return this.logo;
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Empresa(){}
	
}
