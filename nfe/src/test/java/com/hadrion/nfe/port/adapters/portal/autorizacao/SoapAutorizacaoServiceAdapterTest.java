package com.hadrion.nfe.port.adapters.portal.autorizacao;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.AutorizacaoService;

public class SoapAutorizacaoServiceAdapterTest extends DominioTest{
	private Certificado certificado;
	
	@Autowired
	private AutorizacaoService autorizacaoService;
	
	@Test
	public void autorizar() throws Throwable{
		certificado = new Certificado(
				FileUtils.getFile("src","test","resources","assinatura","certificado.pfx"), 
				"12345678"); 
		autorizacaoService.autorizar(null, certificado);
	}
	
}
