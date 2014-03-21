package com.hadrion.nfe.dominio.modelo.lote;

import com.hadrion.nfe.dominio.modelo.recepcao.RecepcaoLoteService;
import com.hadrion.nfe.dominio.modelo.recepcao.RetornoRecepcaoLote;


public class EnviarLoteService {
	
	RecepcaoLoteService recepcaoLoteService;
	
	public EnviarLoteService(RecepcaoLoteService recepcaoLoteService){
		this.recepcaoLoteService = recepcaoLoteService;
	}
	
	public void enviar(Lote lote){
		RetornoRecepcaoLote retorno = recepcaoLoteService.recepcionar(lote); 
		
		if (retorno != null && retorno.sucesso())
			lote.recebido(retorno.recibo().numero());
		else 
			lote.inconsistente(retorno.erro());
	}
}
