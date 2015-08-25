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
import com.hadrion.nfe.dominio.modelo.nf.importacao.Importacao;
import com.hadrion.nfe.dominio.modelo.nf.importacao.ViaTransporte;
import com.hadrion.nfe.tipos.Cnpj;

public class ImportacaoDeserializer implements JsonDeserializer<Importacao>{

	@Override
	public Importacao deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();

		Importacao importacao = new Importacao(
				i(j,"nDI"), 
				parseDataHora(s(j,"dDI")),
				s(j,"xLocDesemb"),
				Uf.valueOf(s(j,"UFDesemb")),
				parseDataHora(s(j,"dDesemb")),
				ViaTransporte.valueOf(s(j,"tpViaTransp")),
				s(j,"cExportador"),
				new Adicao(1,1,""));
		
		return importacao;
	}
	private Cnpj cnpj(JsonObject j){
		return tem(j,"cnpj")?new Cnpj(l(j,"cnpj")):null;
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