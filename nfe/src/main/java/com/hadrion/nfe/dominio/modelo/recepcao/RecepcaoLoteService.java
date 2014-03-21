package com.hadrion.nfe.dominio.modelo.recepcao;

import com.hadrion.nfe.dominio.modelo.lote.Lote;

public interface RecepcaoLoteService {
	
	RetornoRecepcaoLote recepcionar(Lote lote);
}
