package com.hadrion.nfe.dominio.modelo.inutilizacao;


public interface InutilizacaoRepositorio {
	
	void salvar(Inutilizacao inutilizacao);

	Inutilizacao obterPeloId(InutilizacaoId inutilizacaoId);

}
