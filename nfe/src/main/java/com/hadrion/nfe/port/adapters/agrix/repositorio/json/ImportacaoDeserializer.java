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
import com.hadrion.nfe.dominio.modelo.nf.importacao.Intermediacao;
import com.hadrion.nfe.dominio.modelo.nf.importacao.ViaTransporte;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;

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
				valorArfmm(j),
				intermediacao(j),
				cnpjTerceiro(j),
				ufTerceiro(j),
				new Adicao(1,1,""));
		
		return importacao;
	}
	private Dinheiro valorArfmm(JsonObject j){
		return new Dinheiro(d(j,"vAFRMM"));
	}
	private Intermediacao intermediacao(JsonObject j){
		return Intermediacao.valueOf(s(j,"tpIntermedio"));
	}
	private Cnpj cnpjTerceiro(JsonObject j){
		return new Cnpj(l(j,"CNPJ"));
	}
	private Uf ufTerceiro(JsonObject j){
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