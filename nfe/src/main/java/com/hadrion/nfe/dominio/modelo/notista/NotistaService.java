package com.hadrion.nfe.dominio.modelo.notista;

import com.hadrion.nfe.dominio.modelo.hospede.Hospede;


public interface NotistaService {
	
	Notista obterNotista(Hospede hospede, String identificacao);
	
}
