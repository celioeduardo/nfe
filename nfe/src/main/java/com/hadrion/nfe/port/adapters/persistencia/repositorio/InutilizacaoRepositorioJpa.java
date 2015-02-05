package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.inutilizacao.Inutilizacao;
import com.hadrion.nfe.dominio.modelo.inutilizacao.InutilizacaoId;
import com.hadrion.nfe.dominio.modelo.inutilizacao.InutilizacaoRepositorio;

@Repository
@Transactional
public class InutilizacaoRepositorioJpa implements InutilizacaoRepositorio{
	
	@Autowired 
	private InutilizacaoRepositorioSpringData repositorio;
	
	@Override
	public void salvar(Inutilizacao inutilizacao) {
		repositorio.save(inutilizacao);
	}

	@Override
	public Inutilizacao obterPeloId(InutilizacaoId inutilizacaoId) {
		return repositorio.findByInutilizacaoId(inutilizacaoId);
	}

	@Override
	public List<Inutilizacao> obterPendentes(FilialId filialId, Ambiente ambiente){
		return repositorio.findByFilialIdAndAmbienteAndDataHoraHomologacaoIsNull(
				filialId,ambiente,new Sort(Direction.DESC, "id"));
	}
	
	@Override
	public List<Inutilizacao> obterHomologadas(FilialId filialId, Ambiente ambiente){
		return repositorio.findByFilialIdAndAmbienteAndDataHoraHomologacaoIsNotNull(
				filialId,ambiente,new Sort(Direction.DESC, "dataHoraHomologacao"));
	}

}
