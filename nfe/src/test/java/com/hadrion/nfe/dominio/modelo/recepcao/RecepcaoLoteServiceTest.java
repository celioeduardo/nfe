package com.hadrion.nfe.dominio.modelo.recepcao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Mensagem;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.NumeroReciboLote;

public class RecepcaoLoteServiceTest {

	@Before
	public void setUp(){
		
	}
	
	@Test
	public void recepcionarLoteComSucesso(){
		Lote lote = fixtureLote();
		RecepcaoLoteService recepcaoLoteService = new MockRecepcaoLoteService("123456");
		RetornoRecepcaoLote retorno = recepcaoLoteService.recepcionar(lote);
		assertEquals(new NumeroReciboLote("123456"), retorno.recibo().numero());
	} 
	@Test
	public void recepcionarLoteComFracasso(){
		Lote lote = fixtureLote();
		RecepcaoLoteService recepcaoLoteService = new MockRecepcaoLoteService(
				new Mensagem(123, "facasso"));
		RetornoRecepcaoLote retorno = recepcaoLoteService.recepcionar(lote);
		assertEquals(new Mensagem(123, "facasso"), retorno.erro());
	} 
	
	private Lote fixtureLote(){
		return null;
	}
	
}
