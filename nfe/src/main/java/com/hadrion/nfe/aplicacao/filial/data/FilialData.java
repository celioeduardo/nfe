package com.hadrion.nfe.aplicacao.filial.data;

import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.tipos.Cnpj;

public class FilialData {
	private FilialId filialId;
	private String nome;
	private Cnpj cnpj;
	private EmpresaId empresaId;

		
	

	public FilialData(FilialId filialId, String nome, Cnpj cnpj,
			EmpresaId empresaId) {
		super();
		this.filialId = filialId;
		this.nome = nome;
		this.cnpj = cnpj;
		this.empresaId = empresaId;
	}




	public FilialId getFilialId() {
		return filialId;
	}




	public void setFilialId(FilialId filialId) {
		this.filialId = filialId;
	}




	public String getNome() {
		return nome;
	}




	public void setNome(String nome) {
		this.nome = nome;
	}




	public Cnpj getCnpj() {
		return cnpj;
	}




	public void setCnpj(Cnpj cnpj) {
		this.cnpj = cnpj;
	}




	public EmpresaId getEmpresaId() {
		return empresaId;
	}




	public void setEmpresaId(EmpresaId empresaId) {
		this.empresaId = empresaId;
	}




	public FilialData(){}

}
