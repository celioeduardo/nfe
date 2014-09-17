package com.hadrion.nfe.dominio.modelo.lote;

import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.AutorizacaoService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.RetornoAutorizacao;


public class EnviarLoteService {
	
	AutorizacaoService recepcaoLoteService;
	
	public EnviarLoteService(AutorizacaoService recepcaoLoteService){
		this.recepcaoLoteService = recepcaoLoteService;
	}
	
	public void enviar(Lote lote) {
		RetornoAutorizacao retorno=null; 
		try {
			retorno = recepcaoLoteService.autorizar(lote);
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
