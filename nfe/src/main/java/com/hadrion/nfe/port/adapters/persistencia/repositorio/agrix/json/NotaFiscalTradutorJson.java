package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;

public class NotaFiscalTradutorJson {
	
	private String json;
	
	public NotaFiscalTradutorJson(String json){
		this.json = json;
	}

	public NotaFiscal converter(){
		
		final GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(NotaFiscal.class, new NotaFiscalDeserializer());
	    final Gson gson = gsonBuilder.create();
		NotaFiscal nf = gson.fromJson(json, NotaFiscal.class);
		return nf;
	}
	
}
