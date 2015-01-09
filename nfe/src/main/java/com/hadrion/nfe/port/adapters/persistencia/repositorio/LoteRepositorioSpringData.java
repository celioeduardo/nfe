package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.LoteId;
import com.hadrion.nfe.dominio.modelo.lote.SituacaoLote;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;

public interface LoteRepositorioSpringData extends JpaRepository<Lote, Long>{

	List<Lote> findBySituacaoAndFilialId(SituacaoLote situacao, FilialId filialId);
	List<Lote> findBySituacaoIn(List<SituacaoLote> situacoes);

	Lote findByLoteId(LoteId loteId);
	List<Lote> findBySituacaoAndFilialIdAndNotistaId(SituacaoLote processando,
			FilialId filialId, NotistaId notistaId);
	
//	@Query("select l from Lote l where l.notas.notaFiscalId = ?1")
//	List<Lote> findLotesDaNotaFiscal(NotaFiscalId notaFiscalId);
	
}
