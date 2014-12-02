package com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.lote.Lote;

public interface ConsultaProcessamentoLoteService {
	
	RetornoConsultaProcessamentoLote consultar(Lote lote, Certificado certificado);
	
}
