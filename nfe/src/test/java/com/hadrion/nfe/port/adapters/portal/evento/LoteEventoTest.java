package com.hadrion.nfe.port.adapters.portal.evento;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.util.DataUtil;

public class LoteEventoTest {

	
	@Test
	public void novoLote(){
		Evento evento1 = Evento.novoCancelamento(
				Uf.MG,
				Ambiente.HOMOLOGACAO,
				new Cnpj(86675642000106L),
				new ChaveAcesso("31150186675642000106550020002638781002993906"),
				DataUtil.dataHora("26/01/2014 17:27:22", "GMT-02:00"), 
				1, //Sequencia do evento
				new NumeroProtocolo("131151658576199"),
				"Nota Fiscal emitida indevidamente");
		Evento evento2 = Evento.novoCancelamento(
				Uf.MG,
				Ambiente.HOMOLOGACAO,
				new Cnpj(86675642000106L),
				new ChaveAcesso("31131016832651000420550010000199361002699180"),
				DataUtil.dataHora("27/01/2014 08:06:38", "GMT-02:00"), 
				1, //Sequencia do evento
				new NumeroProtocolo("199131151658576"),
				"Valor Total da Nota Fiscal incorreto");
		
		LoteEvento lote = new LoteEvento(1L,evento1,evento2);
		assertEquals(new Long(1),lote.id());
		assertEquals(eventos(evento1,evento2),lote.eventos());
	}
	
	private List<Evento> eventos(Evento ... eventos){
		return Arrays.asList(eventos);
	}
	
}
