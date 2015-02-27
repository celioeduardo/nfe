package com.hadrion.nfe.port.adapters.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.dominio.modelo.hospede.Hospede;
import com.hadrion.nfe.dominio.modelo.notista.Notista;
import com.hadrion.nfe.dominio.modelo.notista.NotistaService;

@Service
class TraducaoNotistaService implements NotistaService {

	@Autowired
	private UsuarioNoPapelAdaptador usuarioNoPapelAdaptador;
	
	@Override
	public Notista obterNotista(Hospede hospede, String identificacao) {
		return usuarioNoPapelAdaptador.paraNotista(hospede, identificacao);
	}

}
