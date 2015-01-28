package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.CpfDeserializer;
import com.hadrion.nfe.tipos.Cpf;

public class CpfDeserializerTest {
	private static String JSON = "68722405399"; 

	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Cpf.class, new CpfDeserializer());
		gson = gsonBuilder.create();
	}
	
	@Test
	public void converter(){
		Cpf cpf = gson.fromJson(JSON, Cpf.class);
		
		assertEquals(new Cpf(68722405399L),cpf);
		
	}
	
}
