package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;

public class ReferenciaDeserializer implements JsonDeserializer<Referencia>{

	@Override
	public Referencia deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		return deserializar(jsonSource.getAsJsonObject());
	}

	private Referencia deserializar(JsonObject j){
		if ("55".equals(j.get("modelo").getAsString())){
			return Referencia.nfe(new ChaveAcesso(j.get("chave").getAsString()));
		}
		return null;
	}
}
