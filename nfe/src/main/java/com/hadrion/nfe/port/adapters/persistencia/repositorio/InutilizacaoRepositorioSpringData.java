package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.inutilizacao.Inutilizacao;
import com.hadrion.nfe.dominio.modelo.inutilizacao.InutilizacaoId;

public interface InutilizacaoRepositorioSpringData extends JpaRepository<Inutilizacao,Long>{

	Inutilizacao findByInutilizacaoId(InutilizacaoId inutilizacaoId);

	List<Inutilizacao> findByFilialIdAndAmbienteAndDataHoraHomologacaoIsNull(
			FilialId filialId,Ambiente ambiente, Sort sort);
	
	List<Inutilizacao> findByFilialIdAndAmbienteAndDataHoraHomologacaoIsNotNull(
			FilialId filialId,Ambiente ambiente, Sort sort);
	
}
