package com.hadrion.nfe.dominio.modelo.portal.inutilizacao;

import com.hadrion.nfe.dominio.modelo.inutillizacao.SolicitacaoInutilizacao;


public interface InutilizacaoPortalService {

	RetornoInutilizacao inutilizar(SolicitacaoInutilizacao solicitacao);
}
