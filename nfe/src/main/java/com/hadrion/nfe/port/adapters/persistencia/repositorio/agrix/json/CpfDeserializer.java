package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.tipos.Cpf;

public class CpfDeserializer implements JsonDeserializer<Cpf>{

	@Override
	public Cpf deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		return new Cpf(jsonSource.getAsLong());
	}

}	
