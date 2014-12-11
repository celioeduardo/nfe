package com.hadrion.nfe.port.adapter.notificacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.comum.notificacao.Notificacao;
import com.hadrion.comum.notificacao.RastreadorNotificacaoPublicada;

@Repository
@Transactional
public class NfeRastreadorNotificacaoPublicadaStoreJpa extends NfeRastreadorNotificacaoPublicadaStore{

	@Autowired
	private RastreadorNotificacaoPublicadaSpringData repositorio;
	
	@Override
	public RastreadorNotificacaoPublicada rastreadorNotificacaoPublicada() {
		RastreadorNotificacaoPublicada rastreador = rastreadorNotificacaoPublicada(nomeTipo());
		
		return rastreador != null ? rastreador : new RastreadorNotificacaoPublicada(nomeTipo());
	}

	@Override
	public RastreadorNotificacaoPublicada rastreadorNotificacaoPublicada(
			String nomeTipo) {
		return repositorio.findByNomeTipo(nomeTipo);
	}

	@Override
	public void rastrearNotificacaoMaisRecentePublicada(
			RastreadorNotificacaoPublicada rastreadorNotificacaoPublicada,
			List<Notificacao> notificacoes) {
		
		int ultimoIndice = notificacoes.size() - 1;
		
		if (ultimoIndice >= 0) {
		    long maisRecenteId = notificacoes.get(ultimoIndice).notificacaoId();
		    rastreadorNotificacaoPublicada.setNotificacaoIdMaisRecentePublicada(maisRecenteId);
		    repositorio.save(rastreadorNotificacaoPublicada);
		}
	}

}
