package com.hadrion.nfe.dominio.modelo.inutilizacao;

import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.RetornoInutilizacao;

public interface InutilizacaoRepositorio {
	
	RetornoInutilizacao salvar(Inutilizacao inutilizacao);

}
