package com.hadrion.nfe.dominio.modelo.lote;

import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.RecepcaoLoteService;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.RetornoRecepcaoLote;


public class EnviarLoteService {
	
	RecepcaoLoteService recepcaoLoteService;
	
	public EnviarLoteService(RecepcaoLoteService recepcaoLoteService){
		this.recepcaoLoteService = recepcaoLoteService;
	}
	
	public void enviar(Lote lote) {
		RetornoRecepcaoLote retorno=null; 
		try {
			retorno = recepcaoLoteService.recepcionar(lote);
		} catch (Throwable t) {
			lote.erroTransmissao(new Mensagem(-1, t.getMessage()));
			return;
		}
		
		if (retorno != null && retorno.sucesso())
			lote.transmitido(retorno.recibo().numero());
		else 
			lote.inconsistente(retorno.erro());
	}
}
