package com.hadrion.nfe.dominio.modelo.cancelamento;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class SolicitacaoCancelamento {
	
	private NotaFiscalId notaFiscalId;
	private String justificativa;
	
	public SolicitacaoCancelamento(
			NotaFiscalId notaFiscalId,
			String justificativa) {
		super();
		this.notaFiscalId = notaFiscalId;
		this.justificativa = justificativa;
	}

	public NotaFiscalId notaFiscalId(){
		return notaFiscalId;
	}
	
	public String justificativa(){
		return justificativa;
	}
	
}
