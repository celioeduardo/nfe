package com.hadrion.nfe.dominio.modelo.nf.informacao;

import java.util.List;

public class Informacao {
	private String texto;
	private List<Observacao> observacoes;
	
	public Informacao(String texto, List<Observacao> observacoes) {
		super();
		this.texto = texto;
		this.observacoes = observacoes;
	}

	public String texto() {
		return texto;
	}

	public List<Observacao> observacoes() {
		return observacoes;
	}

	
	
}
