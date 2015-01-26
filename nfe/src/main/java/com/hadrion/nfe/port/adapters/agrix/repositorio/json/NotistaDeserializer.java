package com.hadrion.nfe.port.adapters.agrix.repositorio.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.notista.Notista;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;

public class NotistaDeserializer implements JsonDeserializer<Notista>{
	
	public NotistaDeserializer(){
	}
	
	@Override
	public Notista deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		 
		final JsonObject j = jsonSource.getAsJsonObject();
		
		Notista notista = new Notista( new NotistaId(s(j,"notistaId")), 
				s(j,"nome"),
				s(j,"descricao"));
		
		return notista;
	}
	
	private String s(JsonObject j, String propriedade){
		return j.has(propriedade) ? j.get(propriedade).getAsString() : null;
	}
	
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}

}
