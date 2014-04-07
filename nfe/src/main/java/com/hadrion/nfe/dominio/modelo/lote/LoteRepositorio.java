package com.hadrion.nfe.dominio.modelo.lote;

import java.util.Set;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public interface LoteRepositorio {
	
	public LoteId proximaIdentidade();
	public void salvar(Lote lote);
	public Set<Lote> lotesDaNota(NotaFiscalId notaFiscalId);
	
	public void limpar();
	
}
