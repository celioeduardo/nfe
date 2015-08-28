package com.hadrion.nfe.port.adapters.agrix.repositorio.json;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.nf.importacao.Adicao;

public class ImportacaoTradutorJson {
	
	private String json;
	final GsonBuilder gsonBuilder;
	final Gson gson;
	
	public ImportacaoTradutorJson(String json){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Adicao.class, new AdicaoDeserializer());
		gson = gsonBuilder.create();
		this.json = json;
	}
	public Set<Adicao> converterAdicoes() {
		return new HashSet<Adicao>(
				Arrays.asList(
						gson.fromJson(json, Adicao[].class))); 
	}
}
