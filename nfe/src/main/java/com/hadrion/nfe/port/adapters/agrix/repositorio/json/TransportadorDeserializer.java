package com.hadrion.nfe.port.adapters.agrix.repositorio.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transportador;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEstadual;

public class TransportadorDeserializer implements JsonDeserializer<Transportador>{
	
	public TransportadorDeserializer(){
	}
	
	@Override
	public Transportador deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		 
		final JsonObject j = jsonSource.getAsJsonObject();
		
		Endereco endereco = new Endereco(s(j,"endereco"), 
				null,
				null,
				null,
			    new Municipio(-1,s(j,"municipio"),Uf.valueOf(s(j,"uf"))),
			    null,
			    null,
			    null);
		
		InscricaoEstadual ie = new InscricaoEstadual(s(j,"ie")); 
		
		Transportador transportador = new Transportador(
				cnpj(j), 
				cpf(j), 
				s(j,"nome"), 
				ie, 
				endereco);
					
		return transportador;
	}
	
	private Cnpj cnpj(JsonObject j){
		return tem(j,"cnpj")?new Cnpj(l(j,"cnpj")):null;
	}
	
	private Cpf cpf(JsonObject j){
		return tem(j,"cpj")?new Cpf(l(j,"cpf")):null;
	}
	
	private Long l(JsonObject j, String propriedade){
		return j.get(propriedade).getAsLong();
	}

	private String s(JsonObject j, String propriedade){
		return j.has(propriedade) ? j.get(propriedade).getAsString() : null;
	}
	
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}

}
