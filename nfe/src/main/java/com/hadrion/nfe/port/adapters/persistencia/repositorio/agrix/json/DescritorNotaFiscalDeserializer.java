package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoEmissao;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.util.DataUtil;

public class DescritorNotaFiscalDeserializer extends AbstractDeserializer 
	implements JsonDeserializer<DescritorNotaFiscal>{

	private Ambiente ambiente;
	
	public DescritorNotaFiscalDeserializer(Ambiente ambiente){
		this.ambiente = ambiente;
	}
	
	@Override
	public DescritorNotaFiscal deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();
		
		DescritorNotaFiscal descritor = null;
		try {
			descritor = new DescritorNotaFiscal(
					criarNotaFiscalId(s(j,"id"), ambiente),
					s(j,"tipo"),
					new Cnpj(l(j,"empresa")),
					new Cnpj(l(j,"filial")),
					l(j,"numero"), 
					new Serie(i(j,"serie")),
					null,
					TipoEmissao.NORMAL,
					DataUtil.data(j.get("emissao").getAsString()), 
					DataUtil.data(j.get("movimentacao").getAsString()),
					s(j,"publicoTipo"),
					l(j,"publicoCodigo"),
					s(j,"publicoNome"),
					new Dinheiro(d(j,"valor")));		
		} catch (Exception e) {
			throw new RuntimeException(j.toString(),e);
		}
		
		return descritor;
	}

	private Long l(JsonObject j, String propriedade){
		return j.get(propriedade).getAsLong();
	}

	private String s(JsonObject j, String propriedade){
		return j.get(propriedade).getAsString();
	}
	private Integer i(JsonObject j, String propriedade){
		return j.get(propriedade).getAsInt();
	}
	private Double d(JsonObject j, String propriedade){
		return j.get(propriedade).getAsDouble();
	}
	
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}	
	
}
