package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.nfe.dominio.modelo.inutilizacao.Inutilizacao;
import com.hadrion.nfe.dominio.modelo.inutilizacao.InutilizacaoId;

public interface InutilizacaoRepositorioSpringData extends JpaRepository<Inutilizacao,Long>{

	Inutilizacao findByInutilizacaoId(InutilizacaoId inutilizacaoId);

}
