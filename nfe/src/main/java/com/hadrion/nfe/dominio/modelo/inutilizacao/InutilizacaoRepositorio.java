package com.hadrion.nfe.dominio.modelo.inutilizacao;

import java.util.List;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;


public interface InutilizacaoRepositorio {
	
	void salvar(Inutilizacao inutilizacao);

	Inutilizacao obterPeloId(InutilizacaoId inutilizacaoId);

	List<Inutilizacao> obterPendentes(FilialId filialId, Ambiente valueOf);
	
	List<Inutilizacao> obterHomologadas(FilialId filialId, Ambiente ambiente);


}
