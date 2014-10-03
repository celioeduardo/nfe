package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.util.DataUtil;

public class DescritorNotaFiscalDeserializer implements JsonDeserializer<DescritorNotaFiscal>{

	@Override
	public DescritorNotaFiscal deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();
		
		DescritorNotaFiscal descritor = new DescritorNotaFiscal(new NotaFiscalId(s(j,"id")), 
				new Modelo(s(j,"modelo")), 
				new Serie(i(j,"modelo")),
				l(j,"numero"), 
				DataUtil.data(j.get("modelo").getAsString()), 
				DataUtil.data(j.get("modelo").getAsString()));		
		
		return descritor;
	}

	private Long l(JsonObject j, String propriedade){
		return j.get(propriedade).getAsLong();
	}
	private Double d(JsonObject j, String propriedade){
		return j.get(propriedade).getAsDouble();
	}

	private String s(JsonObject j, String propriedade){
		return j.get(propriedade).getAsString();
	}
	private Integer i(JsonObject j, String propriedade){
		return j.get(propriedade).getAsInt();
	}
	
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}	
	
}
