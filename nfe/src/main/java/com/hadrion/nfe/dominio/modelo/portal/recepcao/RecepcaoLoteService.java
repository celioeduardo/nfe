package com.hadrion.nfe.dominio.modelo.portal.recepcao;

import com.hadrion.nfe.dominio.modelo.lote.Lote;

public interface RecepcaoLoteService {
	
	RetornoRecepcaoLote recepcionar(Lote lote) throws Throwable ;
}
