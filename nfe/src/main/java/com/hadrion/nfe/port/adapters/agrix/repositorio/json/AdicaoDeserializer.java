package com.hadrion.nfe.port.adapters.agrix.repositorio.json;

import static com.hadrion.util.DataUtil.data;
import static com.hadrion.util.DataUtil.dataHora;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.importacao.Adicao;

public class AdicaoDeserializer implements JsonDeserializer<Adicao>{

	@Override
	public Adicao deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();
		
		return new Adicao(1,1,"");
		
	}
	
	private Uf ufTerceiro(JsonObject j){
		if (!tem(j,"UFTerceiro")) 
			return null;
		return Uf.valueOf(s(j,"UFTerceiro"));
	}
	
	private Long l(JsonObject j, String propriedade){
		return j.get(propriedade).getAsLong();
	}
	
	private int i(JsonObject j, String propriedade){
		return j.get(propriedade).getAsInt();
	}
	
	private Double d(JsonObject j, String propriedade){
		return j.get(propriedade).getAsDouble();
	}
	
	private String s(JsonObject j, String propriedade){
		return j.has(propriedade) ? j.get(propriedade).getAsString() : null;
	}
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}
	private Date parseDataHora(String s) {
		if (s.length() > 8)
			return dataHora(s);
		else
			return data(s);
	}
}