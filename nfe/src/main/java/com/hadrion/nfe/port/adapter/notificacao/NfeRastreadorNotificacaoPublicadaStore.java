package com.hadrion.nfe.port.adapter.notificacao;

import com.hadrion.comum.notificacao.RastreadorNotificacaoPublicadaStore;

public abstract class NfeRastreadorNotificacaoPublicadaStore implements
		RastreadorNotificacaoPublicadaStore {

	@Override
	public String nomeTipo() {
		return "hadrion.nfe";
	}

}
