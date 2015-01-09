package com.hadrion.nfe.dominio.modelo.lote;

import java.util.List;
import java.util.Set;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;

public interface LoteRepositorio {
	
	public LoteId proximaIdentidade();
	public void salvar(Lote lote);
	public Set<Lote> lotesDaNota(NotaFiscalId notaFiscalId);
	public List<Lote> lotesPendentesDaNota(NotaFiscalId notaFiscalId);
	
	public void limpar();
	public List<Lote> lotesEmProcessamento(Ambiente ambiente, FilialId filialId);
	public List<Lote> lotesEmProcessamento(Ambiente ambiente, FilialId filialId,NotistaId notistaId);
	public Lote obterLote(LoteId loteId);
	
}
