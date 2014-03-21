package com.hadrion.nfe.dominio.modelo.recepcao.consulta;

import com.hadrion.nfe.dominio.modelo.lote.Lote;

public interface ConsultaProcessamentoLoteService {
	
	RetornoConsultaProcessamentoLote consultar(Lote lote);
	
}
