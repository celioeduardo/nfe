package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.hadrion.nfe.dominio.modelo.cancelamento.SolicitacaoCancelamento;
import com.hadrion.nfe.dominio.modelo.cancelamento.SolicitacaoCancelamentoId;
import com.hadrion.nfe.dominio.modelo.cancelamento.SolicitacaoCancelamentoRepositorio;

public class MockSolicitacaoCancelamentoRepositorio  implements SolicitacaoCancelamentoRepositorio {

	private Map<String,SolicitacaoCancelamento> store= new HashMap<String, SolicitacaoCancelamento>();
	
	@Override
	public SolicitacaoCancelamentoId proximaIdentidade() {
		return new SolicitacaoCancelamentoId(
				UUID.randomUUID().toString().toUpperCase());
	}

	@Override
	public void salvar(SolicitacaoCancelamento solicitacaoCancelamento) {
		store.put(solicitacaoCancelamento.solicitacaoCancelamentoId().toString(), 
				solicitacaoCancelamento);		
	}

}
