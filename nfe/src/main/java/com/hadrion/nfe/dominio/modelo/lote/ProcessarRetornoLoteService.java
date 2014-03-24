package com.hadrion.nfe.dominio.modelo.lote;

import com.hadrion.nfe.dominio.modelo.portal.recepcao.consulta.ConsultaProcessamentoLoteService;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.consulta.RetornoConsultaProcessamentoLote;

public class ProcessarRetornoLoteService {
	
	ConsultaProcessamentoLoteService consultaProcessamentoLoteService;
	
	
	public ProcessarRetornoLoteService(
			ConsultaProcessamentoLoteService consultaProcessamentoLoteService){
		this.consultaProcessamentoLoteService = consultaProcessamentoLoteService;
	}
	
	public void processar(Lote lote) {
		RetornoConsultaProcessamentoLote retorno = 
				consultaProcessamentoLoteService.consultar(lote);
		
		if (retorno.loteFoiProcessado())
			lote.processado(
					retorno.mensagem(),
					retorno.mensagemSefaz(),
					retorno.protocolos());
		else if(retorno.loteEmProcessamento())
			;//Não faz nada
		else
			lote.inconsistente(retorno.mensagem());
	}

}
