package com.hadrion.nfe.port.adapters.agrix.evento;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

class EventoAgrixTradutor {
	
	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	public EventoAgrixTradutor() {
		gsonBuilder = new GsonBuilder();
		Type collectionType = new TypeToken<Collection<EventoAgrix>>(){}.getType();
		gsonBuilder.registerTypeAdapter(collectionType, new EventosAgrixJsonDeserializer());
		gson = gsonBuilder.create();
	}
	
	public List<EventoAgrix> deserializarEventos(String json){
		Type collectionType = new TypeToken<Collection<EventoAgrix>>(){}.getType();
		List<EventoAgrix> eventos = gson.fromJson(json, collectionType);
		return eventos;
	}

}
