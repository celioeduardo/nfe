package com.hadrion.nfe.dominio.modelo.nf;

import java.util.List;


public interface NotaFiscalRepositorio {

	public List<NotaFiscal> notasPendentesAutorizacao();
	public List<DescritorNotaFiscal> notasPendentesAutorizacaoResumo();
	public void salvar(NotaFiscal notaFiscal);

	public NotaFiscal notaFiscalPeloId(NotaFiscalId notaFiscalId);
	
	public void limpar();
	
}
