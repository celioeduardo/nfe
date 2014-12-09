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
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;

public class NotaFiscalDescritorDeserializerTest {
	private static String JSON = 
			"{\r\n" + 
			"		\"id\" : \"03ADF8A01D1727DDE050007F01002730\",\r\n" + 
			"		\"tipo\" : \"E\",\r\n" + 
			"		\"empresa\" : 86675642000106,\r\n" + 
			"		\"filial\" : 86675642000106,\r\n" + 
			"		\"numero\" : 204818,\r\n" + 
			"		\"serie\" : \"2\",\r\n" + 
			"		\"emissao\" : \"22/09/14\",\r\n" + 
			"		\"movimentacao\" : \"22/09/14\",\r\n" + 
			"		\"publicoTipo\" : \"A\",\r\n" + 
			"		\"publicoCodigo\" : 157,\r\n" + 
			"		\"publicoNome\" : \"AKEMI YOSHIE YAMAGUCHI\",\r\n" + 
			"		\"valor\" : 1000\r\n" + 
			"	}";

	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DescritorNotaFiscal.class, new DescritorNotaFiscalDeserializer(Ambiente.HOMOLOGACAO));
		gson = gsonBuilder.create();
	}
	
	@Test
	public void converter(){
		
		DescritorNotaFiscal descritor = gson.fromJson(JSON, DescritorNotaFiscal.class);
		
		assertEquals(new DescritorNotaFiscal(new NotaFiscalId("H-03ADF8A01D1727DDE050007F01002730"),
				"E",
				new Cnpj(86675642000106L),
				new Cnpj(86675642000106L),
				204818L,
				new Serie(2L),
				data("22/09/14"),
				data("22/09/14"),
				"A",
				157L,
				"AKEMI YOSHIE YAMAGUCHI",
				new Dinheiro(1000.))
		,descritor);
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
