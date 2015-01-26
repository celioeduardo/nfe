package com.hadrion.nfe.port.adapters.agrix.repositorio.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.endereco.Cep;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.endereco.Pais;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalEntrega;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.Telefone;

public class LocalEntregaDeserializer implements JsonDeserializer<LocalEntrega>{

	@Override
	public LocalEntrega deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();

		Endereco endereco = new Endereco(s(j,"logradouro"), 
				s(j,"numero"),
				s(j,"complemento"),
				s(j,"bairro"),
			    new Municipio(i(j,"codigoMunicipio"),s(j,"municipio"),Uf.valueOf(s(j,"uf"))),
			    new Pais(l(j,"pais"),s(j,"paisNome")),
			    new Cep(l(j,"cep")),
			    telefone(j));
		
		LocalEntrega local = new LocalEntrega(
				cnpj(j), 
			    cpf(j),
				endereco);
		
		return local;
	}
	
	private Cnpj cnpj(JsonObject j){
		return tem(j,"cnpj")?new Cnpj(l(j,"cnpj")):null;
	}
	
	private Cpf cpf(JsonObject j){
		return tem(j,"cpj")?new Cpf(l(j,"cpf")):null;
	}
	
	private Telefone telefone(JsonObject j){
		return tem(j,"telefone") ? new Telefone(s(j,"telefone")) : null;
	}
	
	private Long l(JsonObject j, String propriedade){
		return j.get(propriedade).getAsLong();
	}
	private int i(JsonObject j, String propriedade){
		return j.get(propriedade).getAsInt();
	}

	private String s(JsonObject j, String propriedade){
		return j.has(propriedade) ? j.get(propriedade).getAsString() : null;
	}
	
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}

}
