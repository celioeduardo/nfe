package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.nfe.dominio.modelo.empresa.Empresa;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;

public interface EmpresaRepositorioSpringData extends JpaRepository<Empresa, Long>{

	Empresa findByEmpresaId(EmpresaId empresaId);

}
