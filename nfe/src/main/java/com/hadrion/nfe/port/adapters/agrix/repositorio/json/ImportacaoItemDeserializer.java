package com.hadrion.nfe.port.adapters.agrix.repositorio.json;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.Adicao;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.ImportacaoItem;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.Intermediacao;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.ViaTransporte;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.util.DataUtil;

public class ImportacaoItemDeserializer implements JsonDeserializer<ImportacaoItem>{

	@Override
	public ImportacaoItem deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();
		
		ImportacaoItem importacao = new ImportacaoItem(
				i(j,"nDI"), 
				DataUtil.data(s(j,"dDI")),
				s(j,"xLocDesemb"),
				Uf.valueOf(s(j,"UFDesemb")),
				DataUtil.data(s(j,"dDesemb")),
				ViaTransporte.valueOf(s(j,"tpViaTransp")),
				s(j,"cExportador"),
				valorArfmm(j),
				intermediacao(j),
				cnpjTerceiro(j),
				ufTerceiro(j),
				pedido(j),
				itemPedido(j),
				adicoes(j));
		
		return importacao;
	}

	private List<Adicao> adicoes(JsonObject j){
		if (tem(j,"adicoes")){
			return new ImportacaoTradutorJson(j.get("adicoes").toString()).converterAdicoes();
		}
		return null;
	}
	private Dinheiro valorArfmm(JsonObject j){
		if (!tem(j,"vAFRMM")) 
			return null;
		return new Dinheiro(d(j,"vAFRMM"));
	}
	
	private Intermediacao intermediacao(JsonObject j){
		return Intermediacao.valueOf(s(j,"tpIntermedio"));
	}
	
	private Cnpj cnpjTerceiro(JsonObject j){
		if (!tem(j,"CNPJ")) 
			return null;
		if (s(j,"CNPJ")=="") 
			return null;
		return new Cnpj(l(j,"CNPJ"));
	}
	
	private Uf ufTerceiro(JsonObject j){
		if (!tem(j,"UFTerceiro")) 
			return null;
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
	private int pedido(JsonObject j){
		return i(j,"xPed"); 
	}
	
	private int itemPedido(JsonObject j){
		return i(j,"nItemPed"); 
	}
	
}