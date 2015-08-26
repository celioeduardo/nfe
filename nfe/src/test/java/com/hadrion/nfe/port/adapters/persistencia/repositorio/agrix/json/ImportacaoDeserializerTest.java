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
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.importacao.Importacao;
import com.hadrion.nfe.dominio.modelo.nf.importacao.Intermediacao;
import com.hadrion.nfe.dominio.modelo.nf.importacao.ViaTransporte;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.DateDeserializer;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.DinheiroDeserializer;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.ImportacaoDeserializer;
import com.hadrion.nfe.tipos.Cnpj;
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
			"	\"vAFRMM\" : 1.23,\r\n" +//valor da AFRMM (Adicional ao Frete para Renovação da Marinha Mercante)  
			"	\"tpIntermedio\" : \"CONTA_PROPRIA\",\r\n" + 
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
		assertEquals(data("25/08/15"),importacao.emissao());
		assertEquals("SANTOS",importacao.localDesembarque());
		assertEquals(Uf.SP,importacao.ufDesembarque());
		assertEquals(data("25/08/15"),importacao.dataDesembarque());
		assertEquals(ViaTransporte.MARITIMA,importacao.viaTransporte());
		assertEquals(new Dinheiro(1.23),importacao.valorARFMM());
		assertEquals(Intermediacao.CONTA_PROPRIA,importacao.intermediacao());
		assertEquals(new Cnpj(74230061000181L),importacao.cnpjTerceiro());
		assertEquals(Uf.RJ,importacao.ufTerceiro());
		assertEquals("123",importacao.codigoExportador());
		
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
