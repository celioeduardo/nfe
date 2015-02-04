package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

}
