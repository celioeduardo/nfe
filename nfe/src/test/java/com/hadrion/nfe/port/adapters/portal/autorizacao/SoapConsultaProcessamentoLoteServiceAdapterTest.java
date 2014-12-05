package com.hadrion.nfe.port.adapters.portal.autorizacao;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalFixture;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.ConsultaProcessamentoLoteService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.RetornoConsultaProcessamentoLote;

public class SoapConsultaProcessamentoLoteServiceAdapterTest extends DominioTest{
	@Autowired
	private ConsultaProcessamentoLoteService consultaProcessamentoService;
	
	private Certificado certificado;
	
	private Lote lote;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		NotaFiscal nf = NotaFiscalFixture.nfEmHomologacao();
		nf.emitida();
		notaFiscalRepositorio.salvar(nf);
		lote = loteGeradoEmHomologacaoPersistidoParaTest(nf);
	}
	
	@Test
	public void consultar() throws Throwable{
		certificado = new Certificado(
				FileUtils.getFile("src","test","resources","assinatura","certificado.pfx"), 
				"12345678"); 
		RetornoConsultaProcessamentoLote retorno = consultaProcessamentoService.consultar(lote, certificado);
		
		assertNotNull(retorno);
		
		System.out.println(retorno);
		
//		assertNull(retorno.erro());
//		
//		System.out.println("\nNúmero do Recibo retornado: "+retorno.recibo().numero());
	}
	
}
