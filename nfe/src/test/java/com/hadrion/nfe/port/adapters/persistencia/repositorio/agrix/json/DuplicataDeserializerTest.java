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
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Duplicata;
import com.hadrion.nfe.tipos.Dinheiro;

public class DuplicataDeserializerTest {
	private static String JSON = 
			"{\r\n" + 
			"    \"tipoDoc\" : \"DM\",\r\n" + 
			"    \"numero\" : 183898,\r\n" + 
			"    \"sequencia\" : \"01\",\r\n" + 
			"    \"vencimento\" : \"05/08/04\",\r\n" + 
			"    \"valor\" : 2856,\r\n" + 
			"    \"desconto\" : 0\r\n" + 
			"}";

	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.registerTypeAdapter(Dinheiro.class, new DinheiroDeserializer());
		gson = gsonBuilder.create();
	}
	
	@Test
	public void converterDuplicata(){
		Duplicata duplicata = gson.fromJson(JSON, Duplicata.class);
		assertEquals("183898",duplicata.numero());
		assertEquals(new Dinheiro(2856),duplicata.valor());
		assertEquals(data("05/08/04"),duplicata.vencimento());
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
