package com.hadrion.nfe.aplicacao.nf;

import java.util.List;

public class EnviarNotasComando {
	
	private List<String> ids;
	private String ambiente;
	
	
	public EnviarNotasComando() {
	}

	public EnviarNotasComando(List<String> ids, String ambiente) {
		super();
		this.ids = ids;
		this.ambiente = ambiente;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

}
