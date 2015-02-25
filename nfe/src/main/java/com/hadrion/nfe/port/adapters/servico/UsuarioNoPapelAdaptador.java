package com.hadrion.nfe.port.adapters.servico;

import com.hadrion.nfe.dominio.modelo.hospede.Hospede;
import com.hadrion.nfe.dominio.modelo.notista.Notista;

public interface UsuarioNoPapelAdaptador {
	
	Notista paraNotista(
			Hospede hospede, String identidade);
	
}
