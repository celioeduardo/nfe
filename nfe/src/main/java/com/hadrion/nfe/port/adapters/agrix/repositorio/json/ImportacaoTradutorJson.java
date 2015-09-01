package com.hadrion.nfe.port.adapters.agrix.repositorio.json;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.Adicao;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.ImportacaoItem;
import com.hadrion.nfe.tipos.Dinheiro;

public class ImportacaoTradutorJson {
	
	private String json;
	final GsonBuilder gsonBuilder;
	final Gson gson;
	
	public ImportacaoTradutorJson(String json){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.registerTypeAdapter(Dinheiro.class, new DinheiroDeserializer());
		gsonBuilder.registerTypeAdapter(ImportacaoItem.class, new ImportacaoItemDeserializer());
		gsonBuilder.registerTypeAdapter(Adicao.class, new AdicaoDeserializer());
		gson = gsonBuilder.create();
		this.json = json;
	}
	public Set<ImportacaoItem> converterImportacao() {
		return new HashSet<ImportacaoItem>(Arrays.asList(gson.fromJson(json, ImportacaoItem[].class)));
	}
	public Set<Adicao> converterAdicoes() {
		return new HashSet<Adicao>(
				Arrays.asList(
						gson.fromJson(json, Adicao[].class))); 
	}
}