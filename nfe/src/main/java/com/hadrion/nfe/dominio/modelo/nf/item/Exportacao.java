package com.hadrion.nfe.dominio.modelo.nf.item;

public class Exportacao {
	
	private Long numeroDrawBack;
	private ExportacaoIndireta exportacaoIndireta;
	
	public Exportacao(Long numeroDrawBack, ExportacaoIndireta exportacaoIndireta) {
		super();
		this.numeroDrawBack = numeroDrawBack;
		this.exportacaoIndireta = exportacaoIndireta;
	}

	public Long numeroDrawBack() {
		return numeroDrawBack;
	}

	public ExportacaoIndireta exportacaoIndireta() {
		return exportacaoIndireta;
	}

	
	
}

