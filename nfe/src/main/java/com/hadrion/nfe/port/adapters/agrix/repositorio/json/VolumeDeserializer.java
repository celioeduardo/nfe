package com.hadrion.nfe.port.adapters.agrix.repositorio.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Volume;

public class VolumeDeserializer implements JsonDeserializer<Volume>{
	
	public VolumeDeserializer(){
	}
	
	@Override
	public Volume deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		 
		final JsonObject j = jsonSource.getAsJsonObject();
		
		return new Volume(
				i(j,"quantidade"), 
				s(j,"especie"), 
				s(j,"marca"),
				s(j,"numero"),
				d(j,"liquido"),
				d(j,"bruto"),
				null);					
	}
	
	private String s(JsonObject j, String propriedade){
		return j.has(propriedade) ? j.get(propriedade).getAsString() : null;
	}
	private Integer i(JsonObject j, String propriedade){
		return j.has(propriedade) ? j.get(propriedade).getAsInt() : null;
	}
	private Double d(JsonObject j, String propriedade){
		return j.has(propriedade) ? j.get(propriedade).getAsDouble() : 0.0;
	}
	
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}
	
}
