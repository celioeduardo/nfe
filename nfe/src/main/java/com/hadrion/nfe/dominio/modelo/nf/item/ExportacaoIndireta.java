package com.hadrion.nfe.dominio.modelo.nf.item;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;

public class ExportacaoIndireta {
	
	private Long numeroRegistro;
	private ChaveAcesso chaveAcesso;
	
	public ExportacaoIndireta(Long numeroRegistro, ChaveAcesso chaveAcesso) {
		super();
		this.numeroRegistro = numeroRegistro;
		this.chaveAcesso = chaveAcesso;
	}

	public Long numeroRegistro() {
		return numeroRegistro;
	}

	public ChaveAcesso chaveAcesso() {
		return chaveAcesso;
	}
	
	
	
}
