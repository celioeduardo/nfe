package com.hadrion.nfe.dominio.modelo.nf;

import java.util.Date;
import java.util.List;


public interface NotaFiscalRepositorio {

	public List<DescritorNotaFiscal> notasPendentesAutorizacaoResumo(Double empresa,Double filial,Date inicio,Date fim,String usuario,NotaFiscalId notaFiscalId);
	public List<NotaFiscal> notasPendentesAutorizacao(List<NotaFiscalId> notas);

	
	public NotaFiscal notaFiscalPeloId(NotaFiscalId notaFiscalId);
	
	public String queryToJson(String query);

	public void limpar();
	public void salvar(NotaFiscal notaFiscal);
}
