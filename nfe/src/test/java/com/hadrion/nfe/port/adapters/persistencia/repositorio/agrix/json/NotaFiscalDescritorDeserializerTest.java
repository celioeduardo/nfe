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
import com.hadrion.nfe.tipos.Dinheiro;

public class NotaFiscalDescritorDeserializerTest {
	private static String JSON = 
			"{\r\n" + 
			"		\"id\" : \"0215C27A36D5B0E0E050007F01004D45\",\r\n" + 
			"		\"tipo\" : \"E\",\r\n" + 
			"		\"empresa\" : 86675642000106,\r\n" + 
			"		\"filial\" : 86675642000700,\r\n" + 
			"		\"numero\" : 2121,\r\n" + 
			"		\"serie\" : \"AE\",\r\n" + 
			"		\"emissao\" : \"02/09/14\",\r\n" + 
			"		\"movimentacao\" : \"02/09/14\",\r\n" + 
			"		\"publicoTipo\" : \"C\",\r\n" + 
			"		\"publicoCodigo\" : 56,\r\n" + 
			"		\"publicoNome\" : \"CEREALISTA SIQUEIRANDRADE LTDA.         \"\r\n" + 
			"	}";

	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new CpfDeserializer());
		gsonBuilder.registerTypeAdapter(Dinheiro.class, new DinheiroDeserializer());
		gson = gsonBuilder.create();
	}
	
//	@Test
//	public void converter(){
//		DescritorNotaFiscal descritorNotaFiscal = gson.fromJson(JSON, Cobranca.class);
//		assertEquals(new Fatura("DM-183898", new Dinheiro(3641.4), Dinheiro.ZERO),cobranca.fatura());
//		assertEquals(2,cobranca.quantidadeDuplicatas());
//		
//		assertEquals(new Duplicata("DM-183898/01", data("05/08/04"), new Dinheiro(2856)),
//				cobranca.obterDuplicata(1));
//		assertEquals(new Duplicata("DM-183898/02", data("29/09/04"), new Dinheiro(785.4)),
//				cobranca.obterDuplicata(2));
//	}
	
	private Date data(String data){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			return formatter.parse(data);
		} catch (ParseException e) {
			return null;
		}	
		
	}
	
}
