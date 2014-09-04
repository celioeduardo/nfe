package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Cobranca;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Duplicata;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Fatura;
import com.hadrion.nfe.tipos.Dinheiro;

public class CobrancaDeserializer implements JsonDeserializer<Cobranca>{
	
	public CobrancaDeserializer(){
	}
	
	@Override
	public Cobranca deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		 
		final JsonArray j = jsonSource.getAsJsonArray();
		
		if (j.size() == 0)
			return null;
		
		double totalFatura = 0;
		List<Duplicata> duplicatas = new ArrayList<Duplicata>();
		for (JsonElement jsonElement : j) {
			JsonObject jo = jsonElement.getAsJsonObject();
			duplicatas.add(duplicata(
					jo.get("numero").getAsString(),
					jo.get("tipoDoc").getAsString(),
					jo.get("sequencia").getAsString(),
					converterData(jo.get("vencimento").getAsString()),
					jo.get("valor").getAsDouble()));
			totalFatura += jo.get("valor").getAsDouble();
		}
		
		Cobranca cobranca = new Cobranca(
				fatura(
					j.get(0).getAsJsonObject().get("numero").getAsString(),
					j.get(0).getAsJsonObject().get("tipoDoc").getAsString(),
					totalFatura), 
				duplicatas);
		
		return cobranca;
	}
	
	private Duplicata duplicata(String numero, String tipoDoc, 
			String sequencia, Date vencimento, Double valor){
		return new Duplicata(tipoDoc+"-"+numero+"/"+sequencia, 
				vencimento, new Dinheiro(valor));
	} 
	
	private Fatura fatura(String numero, String tipoDoc, Double valor){
		return new Fatura(tipoDoc+"-"+numero, new Dinheiro(valor), Dinheiro.ZERO);
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
