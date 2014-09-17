package com.hadrion.nfe.dominio.modelo.portal.autorizacao;

import com.hadrion.nfe.dominio.modelo.lote.Lote;

public interface AutorizacaoService {
	
	RetornoAutorizacao autorizar(Lote lote) throws Throwable;
}
