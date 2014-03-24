package com.hadrion.nfe.dominio.modelo.portal.cancelamento;

import com.hadrion.nfe.dominio.modelo.cancelamento.SolicitacaoCancelamentoId;

public interface CancelamentoNfeService {
	
	RetornoCancelamento cancelar(SolicitacaoCancelamentoId solicitacao);
	
}
