package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

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
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Telefone;

public class EmitenteDeserializer implements JsonDeserializer<Emitente>{

	@Override
	public Emitente deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();

		Endereco endereco = new Endereco(s(j,"logradouro"), 
				s(j,"numero"),
				s(j,"complemento"),
				s(j,"bairro"),
			    new Municipio(s(j,"municipio"),Uf.valueOf(s(j,"uf"))),
			    new Pais(1L,s(j,"pais")),
			    new Cep(l(j,"cep")));
		
		Telefone telefone = new Telefone(s(j,"telefone"));		
		InscricaoEstadual ie = new InscricaoEstadual(s(j,"ie")); 
		InscricaoEstadual ieSubstituto = new InscricaoEstadual(s(j,"ieSubstituto"));
		
		Emitente emitente = new Emitente(
				cnpj(j), 
			    cpf(j),
				s(j,"razaoSocial"), 
				s(j,"nomeFantasia"), 
				endereco, 
				telefone, 
				ie, 
				ieSubstituto);
		
		return emitente;
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
		return j.get(propriedade).getAsString();
	}
	
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}

}
