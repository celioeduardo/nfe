package com.hadrion.nfe.dominio.modelo.portal.recepcao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.NumeroReciboLote;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.RecepcaoLoteService;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.RetornoRecepcaoLote;

public class RecepcaoLoteServiceTest {

	@Before
	public void setUp(){
		
	}
	
	@Test
	public void recepcionarLoteComSucesso() throws Throwable{
		Lote lote = loteParaTest();
		RecepcaoLoteService recepcaoLoteService = new MockRecepcaoLoteService("123456");
		RetornoRecepcaoLote retorno = recepcaoLoteService.recepcionar(lote);
		assertEquals(new NumeroReciboLote("123456"), retorno.recibo().numero());
	} 
	@Test
	public void recepcionarLoteComFracasso() throws Throwable{
		Lote lote = loteParaTest();
		RecepcaoLoteService recepcaoLoteService = new MockRecepcaoLoteService(
				new Mensagem(123, "facasso"));
		RetornoRecepcaoLote retorno = recepcaoLoteService.recepcionar(lote);
		assertEquals(new Mensagem(123, "facasso"), retorno.erro());
	} 
	
	private Lote loteParaTest(){
		return null;
	}
	
}
