package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;


public class NotaFiscalTradutorJson {
	
	private String json;
	
	public NotaFiscalTradutorJson(String json){
		this.json = json;
	}

	public NotaFiscal converterNotaFiscal(){
		
		final GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(NotaFiscal.class, new NotaFiscalDeserializer());
	    final Gson gson = gsonBuilder.create();
		NotaFiscal nf = gson.fromJson(json, NotaFiscal.class);
		return nf;
	}
	public Item converterItem(){
		
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Item.class, new ItemDeserializer());
		final Gson gson = gsonBuilder.create();
		Item item = gson.fromJson(json, Item.class);
		return item;
	}

	public List<Item> converterItens() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Item.class, new ItemDeserializer());
		final Gson gson = gsonBuilder.create();
		List<Item> result = Arrays.asList(gson.fromJson(json, Item[].class)); 
		return result; 
	}

	public Set<Referencia> converterReferencias() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Referencia.class, new ReferenciaDeserializer());
		final Gson gson = gsonBuilder.create();
		Set<Referencia> result =  new HashSet<Referencia>(Arrays.asList(gson.fromJson(json, Referencia[].class)));
		//Type listType = new TypeToken<Set<Referencia>>() {}.getType();		
		//Set<Referencia> result =  gson.fromJson(json, listType);
		return result; 
	}	
	public Emitente converterEmitente() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Emitente.class, new EmitenteDeserializer());
		final Gson gson = gsonBuilder.create();
		Emitente result = gson.fromJson(json, Emitente.class);
		
		return result; 
	}	
}
