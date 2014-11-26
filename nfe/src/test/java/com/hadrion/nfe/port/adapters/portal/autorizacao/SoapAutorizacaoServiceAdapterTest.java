package com.hadrion.nfe.port.adapters.portal.autorizacao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalFixture;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.AutorizacaoService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.RetornoAutorizacao;

public class SoapAutorizacaoServiceAdapterTest extends DominioTest{
	@Autowired
	private AutorizacaoService autorizacaoService;
	
	private Certificado certificado;
	
	private Lote lote;
	
	@Before
	public void setUp(){
		NotaFiscal nf = NotaFiscalFixture.nfEmHomologacao();
		nf.emitida();
		lote = loteGeradoEmHomologacaoPersistidoParaTest(nf);
	}
	
	@Ignore @Test
	public void autorizar() throws Throwable{
		certificado = new Certificado(
				FileUtils.getFile("src","test","resources","assinatura","certificado.pfx"), 
				"12345678"); 
		RetornoAutorizacao retorno = autorizacaoService.autorizar(lote, certificado);
		assertNotNull(retorno.recibo());
		assertNull(retorno.erro());
		
		System.out.println("\nNúmero do Recibo retornado: "+retorno.recibo().numero());
	}
	
}
