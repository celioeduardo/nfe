package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.notista.Notista;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.NotistaDeserializer;

public class NotistaDeserializerTest {
	private static String JSON = 
			"{\r\n" + 
			"			\"notistaId\" : \"COOPADAP\",\r\n" + 
			"			\"nome\" : \"COOPADAP\",\r\n" + 
			"			\"descricao\" : \"DBA DO SISTEMA\",\r\n" + 
			"			\"ativo\" : \"S\"\r\n" + 
			"}";

	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Notista.class, new NotistaDeserializer());
		gson = gsonBuilder.create();
	}
	
	@Test
	public void converter(){

		Notista notista = gson.fromJson(JSON,Notista.class);		
		
		assertEquals("COOPADAP",notista.notistaId().id());
		assertEquals("COOPADAP",notista.nome());
		assertEquals("DBA DO SISTEMA",notista.descricao());
		
	}
}
