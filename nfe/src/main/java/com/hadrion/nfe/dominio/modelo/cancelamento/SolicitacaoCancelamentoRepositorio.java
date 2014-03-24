package com.hadrion.nfe.dominio.modelo.cancelamento;

public interface SolicitacaoCancelamentoRepositorio {
	
	SolicitacaoCancelamentoId proximaIdentidade();
	void salvar(SolicitacaoCancelamento solicitacaoCancelamento);
	
	
}
