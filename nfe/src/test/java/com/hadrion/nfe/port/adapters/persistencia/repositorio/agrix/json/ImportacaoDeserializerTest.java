package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.nf.importacao.Importacao;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.DateDeserializer;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.DinheiroDeserializer;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.ImportacaoDeserializer;
import com.hadrion.nfe.tipos.Dinheiro;

public class ImportacaoDeserializerTest {
	private static String JSON = 
			"{\r\n" + 
			"	\"nDI\" : \"123\",\r\n" + 
			"	\"dDI\" : \"25/08/15\",\r\n" + 
			"	\"xLocDesemb\" : \"SANTOS\",\r\n" + 
			"	\"UFDesemb\" : \"SP\",\r\n" + 
			"	\"dDesemb\" : \"25/08/15\",\r\n" + 
			"	\"tpViaTransp\" : \"MARITIMA\",\r\n" + 
			"	\"vAFRMM\" : 1.23,\r\n" + 
			"	\"tpIntermedio\" : \"MARITIMA\",\r\n" + 
			"	\"CNPJ\" : \"74230061000181\",\r\n" + 
			"	\"UFTerceiro\" : \"RJ\",\r\n" + 
			"	\"cExportador\" : \"123\"\r\n" + 
			"}";

	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.registerTypeAdapter(Dinheiro.class, new DinheiroDeserializer());
		gsonBuilder.registerTypeAdapter(Importacao.class, new ImportacaoDeserializer());
		gson = gsonBuilder.create();
	}
	
	@Test
	public void converterImportacao(){
		Importacao importacao = gson.fromJson(JSON, Importacao.class);
		assertEquals(123,importacao.numero());
		//assertEquals(new Dinheiro(2856),importacao.valor());
		assertEquals(data("25/08/15"),importacao.emissao());
	}
	
	private Date data(String data){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			return formatter.parse(data);
		} catch (ParseException e) {
			return null;
		}	
		
	}	
}
