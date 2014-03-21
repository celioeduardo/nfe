package com.hadrion.nfe.dominio.modelo.lote;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class LoteNotaFiscal {
	private NotaFiscalId notaFiscalId;
	private SituacaoLoteNotaFiscal situacao;
	
	public LoteNotaFiscal(NotaFiscalId notaFiscalId) {
		super();
		this.notaFiscalId = notaFiscalId;
		this.situacao = SituacaoLoteNotaFiscal.NAO_PROCESSADA;
	}
	
	public NotaFiscalId notaFiscalId(){
		return notaFiscalId;
	}
	
	public boolean naoProcessada(){
		return this.situacao == SituacaoLoteNotaFiscal.NAO_PROCESSADA;
	}
}
