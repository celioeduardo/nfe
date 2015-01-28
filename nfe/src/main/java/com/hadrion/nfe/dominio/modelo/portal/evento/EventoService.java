package com.hadrion.nfe.dominio.modelo.portal.evento;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.port.adapters.portal.evento.LoteEvento;
import com.hadrion.nfe.port.adapters.portal.ws.Local;

public interface EventoService {
	
	RetornoLoteEvento cancelar(LoteEvento lote, Certificado certificado,
			Ambiente ambiente, Local local);
	
}
