package com.hadrion.nfe.port.adapters.agrix.repositorio.json;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.nf.transporte.RetencaoIcms;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transportador;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Veiculo;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Volume;


public class TransporteTradutorJson {
	
	private String json;
	final GsonBuilder gsonBuilder;
	final Gson gson;
	
	public TransporteTradutorJson(String json){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Transportador.class, new TransportadorDeserializer());
		gsonBuilder.registerTypeAdapter(Veiculo.class, new VeiculoDeserializer());
		gsonBuilder.registerTypeAdapter(Transporte.class, new TransporteDeserializer());
		gsonBuilder.registerTypeAdapter(Volume.class, new VolumeDeserializer());
		gson = gsonBuilder.create();
		this.json = json;
	}
	public Transporte converterTransporte(){		
		return fromJson(Transporte.class);
	}
	public Transportador converterTransportadora() {
		return fromJson(Transportador.class);
	}
	public Veiculo converterVeiculo() {
		return fromJson(Veiculo.class);
	}
	public List<Volume> converterVolumes() {
		return Arrays.asList(gson.fromJson(json, Volume.class)); 
	}
	public RetencaoIcms converterRetencao() {
		return fromJson(RetencaoIcms.class);
	}
	private <T> T fromJson(Class<T> classe){
		return gson.fromJson(json, classe);
	}
}
