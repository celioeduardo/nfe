package com.hadrion.nfe.dominio.modelo.filial;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;
import com.hadrion.nfe.tipos.Cnpj;

@Entity
@SequenceGenerator(name="SEQ", sequenceName="SQ_FILIAL")
@Table(name="FILIAL")
public class Filial {
	
	@Embedded
	private FilialId filialId;
	
	@Column(name="NOME")
	private String nome;
	
	@Embedded
	private Cnpj cnpj;
	
	@Embedded
	private EmpresaId empresaId;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;

	public Filial(FilialId filialId, String nome, Cnpj cnpj,
			EmpresaId empresaId) {
		this.filialId = filialId;
		this.nome = nome;
		this.cnpj = cnpj;
		this.empresaId = empresaId;
	}
	
	public FilialId filialId(){
		return filialId;
	}
	
	public String nome(){
		return nome;
	}
	
	public Cnpj cnpj(){
		return cnpj;
	}
	
	public EmpresaId empresaId(){
		return empresaId;
	}

	public void renomear(String novoNome) {
		this.nome = novoNome;
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Filial(){}
	
}
