package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.nfe.dominio.modelo.lote.Lote;

public interface LoteRepositorioSpringData extends JpaRepository<Lote, Long>{
	
//	@Query("select l from Lote l where l.notas.notaFiscalId = ?1")
//	List<Lote> findLotesDaNotaFiscal(NotaFiscalId notaFiscalId);
	
}
