package com.hadrion.nfe.dominio.modelo.lote;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.AutorizacaoService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.RetornoAutorizacao;


public class EnviarLoteService {
	
	AutorizacaoService autorizacaoService;
	
	public EnviarLoteService(AutorizacaoService recepcaoLoteService){
		this.autorizacaoService = recepcaoLoteService;
	}
	
	public void enviar(Lote lote) {
		//TODO obter certificado
		Certificado certificado = null;
		RetornoAutorizacao retorno=null; 
		try {
			retorno = autorizacaoService.autorizar(lote,certificado);
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
