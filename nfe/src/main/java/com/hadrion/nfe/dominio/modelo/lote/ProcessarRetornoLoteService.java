package com.hadrion.nfe.dominio.modelo.lote;

import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.ConsultaProcessamentoLoteService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.RetornoConsultaProcessamentoLote;

public class ProcessarRetornoLoteService {
	
	ConsultaProcessamentoLoteService consultaProcessamentoLoteService;
	
	
	public ProcessarRetornoLoteService(
			ConsultaProcessamentoLoteService consultaProcessamentoLoteService){
		this.consultaProcessamentoLoteService = consultaProcessamentoLoteService;
	}
	
	public void processar(Lote lote) {
		//TODO obter certificado
		RetornoConsultaProcessamentoLote retorno = 
				consultaProcessamentoLoteService.consultar(lote,null);
		
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
