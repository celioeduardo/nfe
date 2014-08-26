package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.nf.Finalidade;
import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;

public class NotaFiscalDeserializer implements JsonDeserializer<NotaFiscal>{

	@Override
	public NotaFiscal deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();
		
		final NotaFiscal nf = new NotaFiscal(
				new NotaFiscalId(j.get("NotaFiscalId").getAsString()),
				null,
				null,
				new Modelo(j.get("modelo").getAsString()),
				new Serie(j.get("serie").getAsLong()),
				j.get("numero").getAsLong(), 
				converterData(j.get("emissao").getAsString()), 
				converterData(j.get("dataHora").getAsString()), 
				TipoOperacao.valueOf(j.get("tipoOperacao").getAsString()),
				LocalDestino.valueOf(j.get("localDestino").getAsString()), 
				null,
				j.get("consumidorFinal").getAsBoolean(),
				Finalidade.valueOf(j.get("finalidade").getAsString()),
				null,
				null,
				null, //Set<NotaFiscalId> referencias,
				null, //Emitente emitente,
				null, //Destinatario destinatario,
				null, //LocalRetirada localRetirada,
				null, //LocalEntrega localEntrega,
				itens(j), //j.get("itens").getAsJsonArray(), //List<Item> itens,
				null, //Cobranca cobranca,
				null, //Informacao informacaoFisco,
				null, //Informacao informacaoContribuinte,
				null); //Exportacao exportacao
		
		return nf;
	}
	
	private List<Item> itens(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("itens").toString()).converterItens();
	}

	private Date converterData(String data){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			return formatter.parse(data);
		} catch (ParseException e) {
			return null;
		}	
		
	}
}
