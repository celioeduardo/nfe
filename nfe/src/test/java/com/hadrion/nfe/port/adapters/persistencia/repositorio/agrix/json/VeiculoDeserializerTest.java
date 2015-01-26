package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Placa;
import com.hadrion.nfe.dominio.modelo.nf.transporte.TipoVeiculo;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Veiculo;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.VeiculoDeserializer;

public class VeiculoDeserializerTest {
	private static String JSON = 
			"{\r\n" + 
			"	\"tipo\" : \"VEICULO\",\r\n" + 
			"	\"placa\" : \"AAA9999\",\r\n" + 
			"	\"uf\" : \"SP\",\r\n" + 
			"	\"rntc\" : \"\"\r\n" + 
			"}";

	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Veiculo.class, new VeiculoDeserializer());
		gson = gsonBuilder.create();
	}
	
	@Test
	public void converter(){
		Veiculo veiculo = gson.fromJson(JSON, Veiculo.class);
		
		assertEquals(new Veiculo(
				TipoVeiculo.VEICULO, 
				new Placa(Uf.SP,"AAA9999"), 
				"", 
				""),
				veiculo);		
	}
}
