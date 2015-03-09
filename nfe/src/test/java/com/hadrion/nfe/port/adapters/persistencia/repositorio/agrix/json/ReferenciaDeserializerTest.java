package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static com.hadrion.util.DataUtil.data;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.ReferenciaDeserializer;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEstadual;

public class ReferenciaDeserializerTest {
	private static String JSON = 
			"[{\r\n" + 
			"    \"modelo\" : \"55\",\r\n" + 
			"    \"chave\" : \"12314567890\"\r\n" + 
			"}, {\r\n" + 
			"    \"modelo\" : \"01\",\r\n" + 
			"    \"uf\" : \"SP\",\r\n" + 
			"    \"anoMes\" : \"1401\",\r\n" + 
			"    \"cnpj\" : 74230061000181,\r\n" + 
			"    \"serie\" : \"1\",\r\n" + 
			"    \"numero\" : 1\r\n" + 
			"}, {\r\n" + 
			"    \"modelo\" : \"04\",\r\n" + 
			"    \"uf\" : \"SP\",\r\n" + 
			"    \"anoMes\" : \"1401\",\r\n" + 
			"    \"cpf\" : 15997427803,\r\n" + 
			"    \"serie\" : \"4\",\r\n" + 
			"    \"ie\" : \"ISENTO\",\r\n" + 
			"    \"numero\" : 4\r\n" + 
			"}]";

	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Referencia.class, new ReferenciaDeserializer());
		gson = gsonBuilder.create();
	}
	
	@Test
	public void converter(){
		List<Referencia> refs =  Arrays.asList(gson.fromJson(JSON, Referencia[].class));

		Referencia ref = Referencia.nfe(new ChaveAcesso("12314567890")); 
		assertEquals(3,refs.size());
		assertEquals(ref,refs.get(0));
		
		ref = Referencia.modelo_1_1A(Modelo.MODELO_1, Uf.SP, data("140101","yyMMdd"), new Cnpj(74230061000181L), new Serie(1), 1L);
		assertEquals(ref,refs.get(1));

		ref = Referencia.produtorRural(Uf.SP, data("140101","yyMMdd"), new Cpf(15997427803L), new Serie(4), 4L,new InscricaoEstadual("ISENTO"));
		assertEquals(ref,refs.get(2));
		
	}
	
}
