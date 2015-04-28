package com.hadrion.nfe.port.adapters.agrix.repositorio.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.Exportacao;

public class ExportacaoNotaDeserializer implements JsonDeserializer<Exportacao>{

	@Override
	public Exportacao deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();

		Exportacao exportacao = new Exportacao(Uf.valueOf(s(j,"ufSaidaPais")),
				s(j,"localEmbarque"),
				s(j,"localDespacho"));
		return exportacao;
	}

	private String s(JsonObject j, String propriedade){
		return j.get(propriedade).getAsString();
	}
}	
