package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.LoteId;
import com.hadrion.nfe.dominio.modelo.lote.SituacaoLote;

public interface LoteRepositorioSpringData extends JpaRepository<Lote, Long>{

	List<Lote> findBySituacao(SituacaoLote situacao);
	List<Lote> findBySituacaoIn(List<SituacaoLote> situacoes);

	Lote findByLoteId(LoteId loteId);
	
//	@Query("select l from Lote l where l.notas.notaFiscalId = ?1")
//	List<Lote> findLotesDaNotaFiscal(NotaFiscalId notaFiscalId);
	
}
