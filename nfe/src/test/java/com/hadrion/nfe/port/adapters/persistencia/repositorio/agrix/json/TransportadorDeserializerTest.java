package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transportador;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.TransportadorDeserializer;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.InscricaoEstadual;

public class TransportadorDeserializerTest {
	private static String JSON = 
			"{\r\n" + 
			"	\"cnpj\" : \"74230061000181\",\r\n" + 
			"	\"nome\" : \"CAD COMEDIA\",\r\n" + 
			"	\"ie\" : \"123456789\",\r\n" + 
			"	\"endereco\" : \"CENTRO\",\r\n" + 
			"	\"municipio\" : \"REBS\",\r\n" + 
			"	\"uf\" : \"SP\"\r\n" + 
			"}";

	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Transportador.class, new TransportadorDeserializer());
		gson = gsonBuilder.create();
	}
	
	@Test
	public void converter(){
		Transportador transp = gson.fromJson(JSON, Transportador.class);
		
		Endereco endereco = new Endereco("CENTRO", 
				null,
				null,
				null,
			    new Municipio(-1,"REBS",Uf.valueOf("SP")),
			    null,
			    null,
			    null);
		
		InscricaoEstadual ie = new InscricaoEstadual("123456789"); 
		
		Transportador transportador = new Transportador(
				new Cnpj(74230061000181L), 
				null, 
				"CAD COMEDIA", 
				ie, 
				endereco);
		
		assertEquals(transportador,transp);
		
	}
	
	
}
