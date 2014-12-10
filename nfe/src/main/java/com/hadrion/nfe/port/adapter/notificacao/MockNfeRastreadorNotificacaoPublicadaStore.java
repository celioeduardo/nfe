package com.hadrion.nfe.port.adapter.notificacao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hadrion.comum.notificacao.Notificacao;
import com.hadrion.comum.notificacao.RastreadorNotificacaoPublicada;

//TODO Implementar como JPA
@Repository
public class MockNfeRastreadorNotificacaoPublicadaStore extends NfeRastreadorNotificacaoPublicadaStore{

	private Map<String, RastreadorNotificacaoPublicada> store;

	public MockNfeRastreadorNotificacaoPublicadaStore(){}
	
	@Override
	public RastreadorNotificacaoPublicada rastreadorNotificacaoPublicada() {
		return rastreadorNotificacaoPublicada(nomeTipo());
	}
	
	@Override
	public RastreadorNotificacaoPublicada rastreadorNotificacaoPublicada(
			String nomeTipo) {

		for (RastreadorNotificacaoPublicada rastreador : getStore().values()) {
			if (rastreador.nomeTipo().equals(nomeTipo))
				return rastreador;
		}
		
		return new RastreadorNotificacaoPublicada(nomeTipo());
	}
	
	@Override
	public void rastrearNotificacaoMaisRecentePublicada(
			RastreadorNotificacaoPublicada rastreadorNotificacaoPublicada,
			List<Notificacao> notificacoes) {
		
		int ultimoIndice = notificacoes.size() - 1;
		
		if (ultimoIndice >= 0) {
		    long maisRecenteId = notificacoes.get(ultimoIndice).notificacaoId();
		    rastreadorNotificacaoPublicada.setNotificacaoIdMaisRecentePublicada(maisRecenteId);
		    salvar(rastreadorNotificacaoPublicada);
		}
		
	}
	
	public void limpar(){
		getStore().clear();
	}
	
	protected void salvar(RastreadorNotificacaoPublicada rastreadorNotificacaoPublicada){
		store.put(rastreadorNotificacaoPublicada.nomeTipo(), rastreadorNotificacaoPublicada);
	}
	
	private Map<String, RastreadorNotificacaoPublicada> getStore(){
		if (store == null)
			store = new HashMap<String, RastreadorNotificacaoPublicada>();
		return store;
	}

}
