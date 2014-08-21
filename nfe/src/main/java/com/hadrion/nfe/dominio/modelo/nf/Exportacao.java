package com.hadrion.nfe.dominio.modelo.nf;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;

public class Exportacao {
	private Uf uf;
	private String localEmbarque;
	private String localDespacho;
	
	public Exportacao(Uf uf, String localEmbarque, String localDespacho) {
		super();
		this.uf = uf;
		this.localEmbarque = localEmbarque;
		this.localDespacho = localDespacho;
	}
	public Uf uf() {
		return uf;
	}
	public String localEmbarque() {
		return localEmbarque;
	}
	public String localDespacho() {
		return localDespacho;
	}
	
	
	
}
