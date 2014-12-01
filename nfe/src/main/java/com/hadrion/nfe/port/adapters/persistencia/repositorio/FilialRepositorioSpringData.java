package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;

public interface FilialRepositorioSpringData extends JpaRepository<Filial, Long> {

	Filial findByFilialId(FilialId filialId);

}
