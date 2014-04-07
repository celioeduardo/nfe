package com.hadrion.nfe.dominio.modelo.lote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.recepcao.MockRecepcaoLoteService;


public class EnviarLoteServiceTest  extends AbstractLoteServiceTest {
	
	@Before
	public void setup() throws Exception{
		super.setUp();
		notaFiscalRepositorio.salvar(fixtureNoteFiscal());
	}
	
	@Test
	public void enviar_com_sucesso(){
		Lote lote = fixtureLoteNaoEnviado();
		EnviarLoteService enviarLoteService = new EnviarLoteService(
				new MockRecepcaoLoteService("123456"));
		enviarLoteService.enviar(lote);
		assertTrue("Lote tem que estar Processando",lote.estaProcessando());
		assertEquals(new NumeroReciboLote("123456"),lote.numeroRecibo());
		assertNull(lote.mensagemErro());
		
	}
	
	@Test
	public void enviar_com_falha_consistencia(){
		Lote lote = fixtureLoteNaoEnviado();
		EnviarLoteService enviarLoteService = new EnviarLoteService(
				new MockRecepcaoLoteService(
						new Mensagem(214, "Rejeição: Tamanho da mensagem excedeu o limite estabelecido")));
		enviarLoteService.enviar(lote);
		assertTrue("Lote tem que estar Inconsistente",lote.estaInconsistente());
		assertNull("Número do Recibo tem que ser nulo",lote.numeroRecibo());
		assertEquals(new Mensagem(214, "Rejeição: Tamanho da mensagem excedeu o limite estabelecido"),lote.mensagemErro());
	}
	
	protected Lote fixtureLoteNaoEnviado() {
		return geracaoLoteService.gerarLoteEmHomologacao(listaNotaFiscalId("1111"));
	}
	
	private NotaFiscal fixtureNoteFiscal(){
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId("1111"));
		nf.emitidaHomologacao();
		return nf;
	}
	
	
}
