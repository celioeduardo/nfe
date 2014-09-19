package com.hadrion.nfe.dominio.modelo.portal.autorizacao;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.lote.Lote;

public interface AutorizacaoService {
	
	RetornoAutorizacao autorizar(Lote lote, Certificado certificado)
			throws Throwable;
}
