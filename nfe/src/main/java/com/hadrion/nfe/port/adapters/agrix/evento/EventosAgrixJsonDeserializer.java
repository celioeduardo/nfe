package com.hadrion.nfe.port.adapters.agrix.evento;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

class EventosAgrixJsonDeserializer implements
	JsonDeserializer<List<EventoAgrix>>{

	@Override
	public List<EventoAgrix> deserialize(JsonElement jsonSource, Type t,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		List<EventoAgrix> result = new ArrayList<EventoAgrix>();
		
		final JsonArray lista = jsonSource.getAsJsonArray();
		
		for (int i = 0; i < lista.size(); i++) {
			JsonObject item = lista.get(i).getAsJsonObject();  
			
			Long id = item.get("id").getAsLong();
			String type = item.get("type").getAsString();
			String occurredOn = item.get("occurred_on").getAsString();
			String body = item.get("body").getAsString();
			
			result.add(new EventoAgrix(id,type,new Date(),body));
		}
		
		return result;
	}


}
