package com.hadrion.nfe.port.adapters.portal.autorizacao;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.AutorizacaoService;

public class SoapAutorizacaoServiceAdapterTest extends DominioTest{
	
	@Autowired
	private AutorizacaoService autorizacaoService;
	
	@Ignore @Test
	public void autorizar() throws Throwable{
		autorizacaoService.autorizar(null);
	}
	
}
