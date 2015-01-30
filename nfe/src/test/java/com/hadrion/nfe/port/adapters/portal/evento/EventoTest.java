package com.hadrion.nfe.port.adapters.portal.evento;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.util.DataUtil;

public class EventoTest {

	
	@Test
	public void novoEvento(){
		EventoCancelamento evento = new EventoCancelamento(
				Uf.MG,
				Ambiente.HOMOLOGACAO,
				new Cnpj(86675642000106L),
				new ChaveAcesso("31150186675642000106550020002638781002993906"),
				DataUtil.dataHora("04/12/2014 17:27:22", "GMT-02:00"), 
				1, //Sequencia do evento
				new NumeroProtocolo("131151658576199"),
				"Nota Fiscal emitida indevidamente");
		
		assertEquals("ID1101113115018667564200010655002000263878100299390601",evento.id());
		assertEquals(110111,evento.tipo());
		assertEquals(31,evento.codigoOrgaoRecepcao());
		assertEquals(Ambiente.HOMOLOGACAO,evento.ambiente());
		assertEquals(new Cnpj(86675642000106L),evento.cnpjAutor());
		assertEquals(new ChaveAcesso("31150186675642000106550020002638781002993906"),evento.chaveAcesso());
		assertEquals(DataUtil.dataHora("04/12/2014 17:27:22", "GMT-02:00"),evento.dataHora());
		assertEquals(1,evento.sequencia());
		assertEquals(new NumeroProtocolo("131151658576199"),evento.numeroProtocolo());
		assertEquals("Nota Fiscal emitida indevidamente",evento.justificativa());
		
	}
//	@Test
//	public void novoEventoCartaCorrecao(){
//		Evento evento = Evento.novaCartaCorrecao(
//				Uf.MG,
//				Ambiente.HOMOLOGACAO,
//				new Cnpj(86675642000106L),
//				new ChaveAcesso("31150186675642000106550020002638781002993906"),
//				DataUtil.dataHora("04/12/2014 17:27:22", "GMT-02:00"), 
//				1, //Sequencia do evento
//				"Teste de Carta de Correção");
//		
//		assertEquals("ID1101103115018667564200010655002000263878100299390601",evento.id());
//		assertEquals(110110,evento.tipo());
//		assertEquals(31,evento.codigoOrgaoRecepcao());
//		assertEquals(Ambiente.HOMOLOGACAO,evento.ambiente());
//		assertEquals(new Cnpj(86675642000106L),evento.cnpjAutor());
//		assertEquals(new ChaveAcesso("31150186675642000106550020002638781002993906"),evento.chaveAcesso());
//		assertEquals(DataUtil.dataHora("04/12/2014 17:27:22", "GMT-02:00"),evento.dataHora());
//		assertEquals(1,evento.sequencia());
//		assertEquals("Nota Fiscal emitida indevidamente",evento.correcao());
//		
//	}
	
}
