package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.ImportacaoItem;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.Intermediacao;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.ViaTransporte;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.DateDeserializer;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.DinheiroDeserializer;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.ImportacaoItemDeserializer;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;

public class ImportacaoDeserializerTest {

	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.registerTypeAdapter(Dinheiro.class, new DinheiroDeserializer());
		gsonBuilder.registerTypeAdapter(ImportacaoItem.class, new ImportacaoItemDeserializer());
		gson = gsonBuilder.create();
	}
	
	@Test
	public void converterImportacao(){
		ImportacaoItem importacao = gson.fromJson(
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
				"	\"cExportador\" : \"123\",\r\n" +
				"	\"xPed\" : 654,\r\n" + 
				"	\"nItemPed\" : 1,\r\n" + 
				"		\"adicoes\" : [{\r\n" + 
				"			\"nAdicao\" : 321,\r\n" + 
				"			\"nSeqAdicC\" : 1,\r\n" + 
				"			\"cFabricante\" : \"123456789ABC\",\r\n" + 
				"			\"vDescDI\" : 1.03\r\n" + 
				"		},{\r\n" + 
				"			\"nAdicao\" : 322,\r\n" + 
				"			\"nSeqAdicC\" : 1,\r\n" + 
				"			\"cFabricante\" : \"123456789ABC\",\r\n" + 
				"			\"vDescDI\" : 1.03\r\n" + 
				"		}]\r\n" + 
				"}", ImportacaoItem.class);
		assertEquals(123,importacao.numero());
		assertEquals(data("25/08/15"),importacao.emissao());
		assertEquals("SANTOS",importacao.localDesembarque());
		assertEquals(Uf.SP,importacao.ufDesembarque());
		assertEquals(data("25/08/15"),importacao.dataDesembarque());
		assertEquals(ViaTransporte.MARITIMA,importacao.viaTransporte());
		assertEquals(Optional.ofNullable(new Dinheiro(1.23)),importacao.valorArfmm());
		assertEquals(Intermediacao.CONTA_PROPRIA,importacao.intermediacao());
		assertEquals(Optional.ofNullable(new Cnpj(74230061000181L)),importacao.cnpjTerceiro());
		assertEquals(Optional.ofNullable(Uf.RJ),importacao.ufTerceiro());
		assertEquals("123",importacao.codigoExportador());
		assertEquals(2,importacao.quantidadeAdicoes());
		
	}
	@Test
	public void converterImportacaoSemNo(){
		ImportacaoItem importacao = gson.fromJson(
				"{\r\n" + 
				"	\"nDI\" : \"123\",\r\n" + 
				"	\"dDI\" : \"25/08/15\",\r\n" + 
				"	\"xLocDesemb\" : \"SANTOS\",\r\n" + 
				"	\"UFDesemb\" : \"SP\",\r\n" + 
				"	\"dDesemb\" : \"25/08/15\",\r\n" + 
				"	\"tpViaTransp\" : \"MARITIMA\",\r\n" + 
				"	\"tpIntermedio\" : \"CONTA_PROPRIA\",\r\n" + 
				"	\"cExportador\" : \"123\",\r\n" +
				"	\"xPed\" : 654,\r\n" + 
				"	\"nItemPed\" : 1,\r\n" +
				"		\"adicoes\" : [{\r\n" + 
				"			\"nAdicao\" : 321,\r\n" + 
				"			\"nSeqAdicC\" : 1,\r\n" + 
				"			\"cFabricante\" : \"123456789ABC\",\r\n" + 
				"			\"vDescDI\" : 1.03\r\n" + 
				"		},{\r\n" + 
				"			\"nAdicao\" : 321,\r\n" + 
				"			\"nSeqAdicC\" : 1,\r\n" + 
				"			\"cFabricante\" : \"123456789ABC\",\r\n" + 
				"			\"vDescDI\" : 1.03\r\n" + 
				"		}]\r\n" + 
				"}", ImportacaoItem.class);
		assertEquals(123,importacao.numero());
		assertEquals(data("25/08/15"),importacao.emissao());
		assertEquals("SANTOS",importacao.localDesembarque());
		assertEquals(Uf.SP,importacao.ufDesembarque());
		assertEquals(data("25/08/15"),importacao.dataDesembarque());
		assertEquals(ViaTransporte.MARITIMA,importacao.viaTransporte());
		assertEquals(Optional.ofNullable(null),importacao.valorArfmm());
		assertEquals(Intermediacao.CONTA_PROPRIA,importacao.intermediacao());
		assertEquals(Optional.ofNullable(null),importacao.cnpjTerceiro());
		assertEquals(Optional.ofNullable(null),importacao.ufTerceiro());
		assertEquals("123",importacao.codigoExportador());
		assertEquals(1,importacao.quantidadeAdicoes());
		
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
