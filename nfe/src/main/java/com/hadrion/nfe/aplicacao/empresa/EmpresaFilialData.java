package com.hadrion.nfe.aplicacao.empresa;

import java.util.List;

public class EmpresaFilialData {

	private String id;
	private String nome;
	private List<EmpresaFilialData> children;
	private boolean expanded;
	private boolean leaf;
	
	public EmpresaFilialData(){
		
	}
	public EmpresaFilialData(String id, String nome,
			List<EmpresaFilialData> children) {
		super();
		this.id = id;
		this.nome = nome;
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String text) {
		this.nome = text;
	}

	public List<EmpresaFilialData> getChildren() {
		return children;
	}

	public void setChildren(List<EmpresaFilialData> children) {
		this.children = children;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}	
	
}
