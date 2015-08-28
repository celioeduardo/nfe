package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.Adicao;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.AdicaoDeserializer;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.DinheiroDeserializer;
import com.hadrion.nfe.tipos.Dinheiro;

public class AdicaoDeserializerTest {

	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Dinheiro.class, new DinheiroDeserializer());
		gsonBuilder.registerTypeAdapter(Adicao.class, new AdicaoDeserializer());
		gson = gsonBuilder.create();
	}
	
	@Test
	public void converterImportacao(){
		Adicao adicao = gson.fromJson(
				"{\r\n" + 
				"	\"nAdicao\" : 321,\r\n" + 
				"	\"nSeqAdicC\" : 1,\r\n" + 
				"	\"cFabricante\" : \"123456789ABC\",\r\n" + 
				"	\"vDescDI\" : 1.03,\r\n" + 
				"	\"nDraw\" : 45,\r\n" + 
				"	\"xPed\" : 654,\r\n" + 
				"	\"nItemPed\" : 1\r\n" + 
				"}", Adicao.class);
		assertEquals(321,adicao.numero());
		assertEquals(1,adicao.sequencia());
		assertEquals("123456789ABC",adicao.fabricante());
		assertEquals(Optional.ofNullable(new Dinheiro(1.03)),adicao.desconto());
		assertEquals(Optional.ofNullable(45),adicao.drawback());
		assertEquals(Optional.ofNullable(654),adicao.pedido());
		assertEquals(Optional.ofNullable(1),adicao.itemPedido());		
	}
}
