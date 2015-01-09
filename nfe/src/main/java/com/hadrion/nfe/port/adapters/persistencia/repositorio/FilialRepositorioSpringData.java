package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;
import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;

public interface FilialRepositorioSpringData extends JpaRepository<Filial, Long> {

	Filial findByFilialId(FilialId filialId);

	List<Filial> findByEmpresaId(EmpresaId empresaId, Sort sort);

}
