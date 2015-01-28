package com.hadrion.nfe.dominio.modelo.portal.cancelamento;

import com.hadrion.nfe.dominio.modelo.cancelamento.SolicitacaoCancelamento;

public interface CancelamentoService {
	
	RetornoCancelamento cancelar(SolicitacaoCancelamento solicitacao);
	
}
