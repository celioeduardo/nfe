package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.nf.transporte.ModalidadeFrete;
import com.hadrion.nfe.dominio.modelo.nf.transporte.RetencaoIcms;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transportador;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Veiculo;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Volume;

public class TransporteDeserializer implements JsonDeserializer<Transporte>{

	@Override
	public Transporte deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();
		
		ModalidadeFrete modalidadeFrete = tem(j,"tipoFrete") && 
				StringUtils.isNotEmpty(j.get("tipoFrete").getAsString()) ? 
						ModalidadeFrete.obterPeloCodigo(j.get("tipoFrete").getAsInt()) : null;
						
		final Transporte transporte = new Transporte(
				modalidadeFrete,
				transportador(j),
				retencao(j),
				veiculo(j),
				new ArrayList<Volume>());//TODO volumes
		
		return transporte;
	}
	
	private Transportador transportador(JsonObject j){
		if (tem(j,"transportadora")){
			return new TransporteTradutorJson(j.get("transportadora").toString()).converterTransportadora();
		}
		return null;
	}
	private Veiculo veiculo(JsonObject j){
		if (tem(j,"veiculo")){		
			return new TransporteTradutorJson(j.get("veiculo").toString()).converterVeiculo();
		}
		return null;
	}
	private RetencaoIcms retencao(JsonObject j){
		if (tem(j,"retencao")){
			return new TransporteTradutorJson(j.get("retencao").toString()).converterRetencao();
		}
		return null;
	}
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}
	
}
