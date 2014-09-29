package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.nf.item.Cfop;
import com.hadrion.nfe.dominio.modelo.nf.item.DescritorProduto;
import com.hadrion.nfe.dominio.modelo.nf.item.Gtin;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.item.Ncm;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json.ItemDeserializer;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Quantidade;

public class ItemTradutorJsonTest {
	private static String JSON = 
			"{\r\n" + 
			"    \"produtoId\" : \"013924F59BB64CC4E050007F010060FB\",\r\n" + 
			"    \"codigo\" : \"74\",\r\n" + 
			"    \"descricao\" : \"TOTRIL 1 LT\",\r\n" + 
			"    \"gtin\" : \"1\",\r\n" + 
			"    \"ncm\" : \"38089329\",\r\n" + 
			"    \"nve\" : \"\",\r\n" + 
			"    \"cfop\" : 5102,\r\n" + 
			"    \"extIpi\" : \"\",\r\n" + 
			"    \"unidade\" : \"LT\",\r\n" + 
			"    \"quantidade\" : 24,\r\n" + 
			"    \"unitario\" : 119,\r\n" + 
			"    \"bruto\" : 2856,\r\n" + 
			"    \"gtinTributavel\" : \"1\",\r\n" + 
			"    \"unidadeTributavel\" : \"LT\",\r\n" + 
			"    \"quantidadeTributavel\" : 24,\r\n" + 
			"    \"unitarioTributacao\" : 119,\r\n" + 
			"    \"frete\" : 0,\r\n" + 
			"    \"seguro\" : 0,\r\n" + 
			"    \"desconto\" : 0,\r\n" + 
			"    \"acessorias\" : 0,\r\n" + 
			"    \"drawback\" : null,\r\n" + 
			"    \"registroExportacao\" : null,\r\n" + 
			"    \"chaveNfRecebida\" : \"\",\r\n" + 
			"    \"quantidadeExportada\" : null,\r\n" + 
			"    \"combustivel\" : \"\"\r\n" + 
			"  }";

	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Item.class, new ItemDeserializer());
		gson = gsonBuilder.create();
	}

	@Test
	public void converter() throws IOException{
		
		Item item = gson.fromJson(JSON, Item.class);

		assertEquals(new DescritorProduto(
				"74", 
				new Gtin("1"), 
				"TOTRIL 1 LT",
				new Ncm(38089329L),
				"", 
				"", 
				new Cfop(5102L),				
				"LT",
				new Quantidade(24.0),				
				new Double(119), 
				new Dinheiro(2856),
				new Gtin("1"), 
				"LT",				
				new Quantidade(24.0),
				new Double(119), 
				new Dinheiro(0), 
				Dinheiro.ZERO, 
				Dinheiro.ZERO, 
				Dinheiro.ZERO,
				null, 
				null),item.produto());
		
	}

}
