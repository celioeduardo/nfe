package com.hadrion.nfe.dominio.modelo.nf;

import java.util.Date;
import java.util.List;


public interface NotaFiscalRepositorio {

	public List<NotaFiscal> notasPendentesAutorizacao();
	public List<DescritorNotaFiscal> notasPendentesAutorizacaoResumo(Double empresa,Double filial,Date inicio,Date fim,String usuario,NotaFiscalId notaFiscalId);
	public void salvar(NotaFiscal notaFiscal);

	public NotaFiscal notaFiscalPeloId(NotaFiscalId notaFiscalId);
	public void limpar();
	public String queryToJson(String query);
	
}
