package com.hadrion.nfe.port.adapters.portal.autorizacao.consulta;

import static org.junit.Assert.assertEquals;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.NumeroReciboLote;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalFixture;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
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
		lote.transmitido(new NumeroReciboLote("310000035995659"));
	}
	
	@Test
	public void consultar() throws Throwable{
		certificado = new Certificado(
				FileUtils.getFile("src","test","resources","assinatura","certificado.pfx"), 
				"12345678"); 
		
		RetornoConsultaProcessamentoLote retorno = consultaProcessamentoService.consultar(lote, certificado);
		
		assertEquals(new Mensagem(104, "Lote processado"),retorno.mensagem());
		
		System.out.println(retorno);
		
//		assertNull(retorno.erro());
//		
//		System.out.println("\nNúmero do Recibo retornado: "+retorno.recibo().numero());
	}
	
}
