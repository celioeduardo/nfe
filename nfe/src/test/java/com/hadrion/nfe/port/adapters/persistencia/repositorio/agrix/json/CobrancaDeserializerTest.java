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
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Cobranca;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Duplicata;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Fatura;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.CobrancaDeserializer;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.CpfDeserializer;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.DinheiroDeserializer;
import com.hadrion.nfe.tipos.Dinheiro;

public class CobrancaDeserializerTest {
	private static String JSON = 
			"[{\r\n" + 
			"    \"tipoDoc\" : \"DM\",\r\n" + 
			"    \"numero\" : 183898,\r\n" + 
			"    \"sequencia\" : \"01\",\r\n" + 
			"    \"vencimento\" : \"05/08/04\",\r\n" + 
			"    \"valor\" : 2856,\r\n" + 
			"    \"desconto\" : 0\r\n" + 
			"}, {\r\n" + 
			"    \"tipoDoc\" : \"DM\",\r\n" + 
			"    \"numero\" : 183898,\r\n" + 
			"    \"sequencia\" : \"02\",\r\n" + 
			"    \"vencimento\" : \"29/09/04\",\r\n" + 
			"    \"valor\" : 785.4,\r\n" + 
			"    \"desconto\" : 0\r\n" + 
			"}]";

	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new CpfDeserializer());
		gsonBuilder.registerTypeAdapter(Dinheiro.class, new DinheiroDeserializer());
		gsonBuilder.registerTypeAdapter(Cobranca.class, new CobrancaDeserializer());
		gson = gsonBuilder.create();
	}
	
	@Test
	public void converter(){
		Cobranca cobranca = gson.fromJson(JSON, Cobranca.class);
		assertEquals(new Fatura("DM-183898", new Dinheiro(3641.4), Dinheiro.ZERO),cobranca.fatura());
		assertEquals(2,cobranca.quantidadeDuplicatas());
		
		assertEquals(new Duplicata("DM-183898/01", data("05/08/04"), new Dinheiro(2856)),
				cobranca.obterDuplicata(1));
		assertEquals(new Duplicata("DM-183898/02", data("29/09/04"), new Dinheiro(785.4)),
				cobranca.obterDuplicata(2));
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
