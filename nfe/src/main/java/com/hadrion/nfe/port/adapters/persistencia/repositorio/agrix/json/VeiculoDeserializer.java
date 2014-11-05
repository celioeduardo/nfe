package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Placa;
import com.hadrion.nfe.dominio.modelo.nf.transporte.TipoVeiculo;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Veiculo;

public class VeiculoDeserializer implements JsonDeserializer<Veiculo>{
	
	public VeiculoDeserializer(){
	}
	
	@Override
	public Veiculo deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		 
		final JsonObject j = jsonSource.getAsJsonObject();
		
		Veiculo veiculo = new Veiculo(
				TipoVeiculo.valueOf(s(j,"tipo")), 
				new Placa(Uf.valueOf(s(j,"uf")), s(j,"placa")), 
				s(j,"rntc"), 
				s(j,"rntc"));
					
		return veiculo;
	}
	
	private String s(JsonObject j, String propriedade){
		return j.has(propriedade) ? j.get(propriedade).getAsString() : null;
	}
	
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}

}
