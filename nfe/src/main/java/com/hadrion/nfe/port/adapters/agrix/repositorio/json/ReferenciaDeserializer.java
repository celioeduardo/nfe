package com.hadrion.nfe.port.adapters.agrix.repositorio.json;

import static com.hadrion.util.DataUtil.data;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEstadual;

public class ReferenciaDeserializer implements JsonDeserializer<Referencia>{

	@Override
	public Referencia deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		return deserializar(jsonSource.getAsJsonObject());
	}

	private Referencia deserializar(JsonObject j){
		
		Modelo modelo = new Modelo(s(j,"modelo"));

		if ("55".equals(modelo.toString()))
			return Referencia.nfe(new ChaveAcesso(s(j,"chave")));
		
		Uf ufEmitente=uf(j); 
		Date emissao=anoMes(j); 
		Cnpj cnpj=cnpj(j);
		Cpf cpf = cpf(j);
		Serie serie=serie(j,"serie"); 
		Long numero=l(j,"numero"); 
		InscricaoEstadual ie=ie(j,"ie");
	    
		if ("1".equalsIgnoreCase(modelo.toString()) || "01".equalsIgnoreCase(modelo.toString()) || "1A".equalsIgnoreCase(modelo.toString())){
	    	
			return Referencia.modelo_1_1A(modelo, ufEmitente, emissao, cnpj, serie, numero);
			
		} else if ("04".equalsIgnoreCase(modelo.toString()) || "4".equalsIgnoreCase(modelo.toString())){
			
			if (cnpj!=null){ 
				return Referencia.produtorRural(ufEmitente, emissao, cnpj, serie, numero, ie);
			} else {
				return Referencia.produtorRural(ufEmitente, emissao, cpf, serie, numero, ie);
			}
		}
		return null;
	}
	private Long l(JsonObject j, String propriedade){
		return tem(j,propriedade) ? j.get(propriedade).getAsLong() : null;
	}
	private String s(JsonObject j, String propriedade){
		return tem(j,propriedade) ? j.get(propriedade).getAsString() : null;
	}
	private Integer i(JsonObject j, String propriedade){
		return tem(j,propriedade) ? j.get(propriedade).getAsInt() : null;
	}	
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}
	private InscricaoEstadual ie(JsonObject j, String propriedade){
		return tem(j,propriedade) ? new InscricaoEstadual(s(j,propriedade)) : null;
	}
	private Serie serie(JsonObject j, String propriedade){
		return tem(j,propriedade) ? new Serie(i(j,propriedade)) : null;
	}
	private Cnpj cnpj(JsonObject j){
		return tem(j,"cnpj") ? new Cnpj(l(j,"cnpj")) : null;
	}
	
	private Cpf cpf(JsonObject j){
		return tem(j,"cpf") ? new Cpf(l(j,"cpf")) : null;
	}	
	private Date anoMes(JsonObject j){
		return tem(j,"anoMes") ? data(s(j,"anoMes") + "01","yyMMdd") : null;
	}	
	private Uf uf(JsonObject j){
		return tem(j,"uf") ? Uf.valueOf(s(j,"uf")) : null;
	}	
}
