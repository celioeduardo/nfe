package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public interface NotaFiscalRepositorioSpringData extends JpaRepository<NotaFiscal, Long>{

	NotaFiscal findByNotaFiscalId(NotaFiscalId notaFiscalId);

}
