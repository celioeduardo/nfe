package com.hadrion.nfe.aplicacao.nf;

import java.util.Collections;
import java.util.List;


public class EnviarEmailComando {
	
	private List<String> ids;
	
	
	public EnviarEmailComando() {
	}

	public EnviarEmailComando(List<String> ids) {
		super();
		this.ids = ids;
	}
	public EnviarEmailComando(String notaFiscalId) {
		super();
		this.ids = Collections.singletonList(notaFiscalId);
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

}
