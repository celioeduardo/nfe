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
import com.hadrion.nfe.dominio.modelo.nf.publico.Destinatario;
import com.hadrion.nfe.dominio.modelo.nf.publico.IndicadorIe;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.Email;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Telefone;

public class DestinatarioDeserializer implements JsonDeserializer<Destinatario>{

	@Override
	public Destinatario deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();
		Telefone telefone = new Telefone(s(j,"telefone"));		

		Endereco endereco = new Endereco(s(j,"logradouro"), 
				s(j,"numero"),
				s(j,"complemento"),
				s(j,"bairro"),
			    new Municipio(i(j,"codigoMunicipio"),s(j,"municipio"),Uf.valueOf(s(j,"uf"))),
			    new Pais(l(j,"pais"),s(j,"paisNome")),
			    new Cep(l(j,"cep")),
			    telefone);
		
		InscricaoEstadual ie = new InscricaoEstadual(s(j,"ie")); 
		Email email = null; 
		if (tem(j,"email"))
			email = new Email(s(j,"email"));
		Long inscricaoSuframa = tem(j,"inscricaoSuframa") ? l(j,"inscricaoSuframa") : null;
		Destinatario destinatario = new Destinatario(
				cnpj(j), 
			    cpf(j),
			    s(j,"idEstrangeiro"), 
				s(j,"razaoSocial"), 
				s(j,"nomeFantasia"), 
				endereco, 
				telefone,
				IndicadorIe.obterPeloCodigo(i(j,"indicadorIe")),
				ie, 
				inscricaoSuframa,
				email);
		
		return destinatario;
	}
	
	private Cnpj cnpj(JsonObject j){
		return tem(j,"cnpj")?new Cnpj(l(j,"cnpj")):null;
	}
	
	private Cpf cpf(JsonObject j){
		return tem(j,"cpf")?new Cpf(l(j,"cpf")):null;
	}
	private Long l(JsonObject j, String propriedade){
		return j.get(propriedade).getAsLong();
	}

	private String s(JsonObject j, String propriedade){
		return j.get(propriedade).getAsString();
	}
	private int i(JsonObject j, String propriedade){
		return j.get(propriedade).getAsInt();
	}
	
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}

}
