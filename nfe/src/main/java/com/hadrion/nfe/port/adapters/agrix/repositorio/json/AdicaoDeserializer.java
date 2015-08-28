package com.hadrion.nfe.port.adapters.agrix.repositorio.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.Adicao;
import com.hadrion.nfe.tipos.Dinheiro;

public class AdicaoDeserializer implements JsonDeserializer<Adicao>{

	@Override
	public Adicao deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();
		
		return new Adicao(
				numero(j),
				sequencia(j),
				codigoFabricante(j),
				desconto(j),
				drawback(j),
				pedido(j),
				itemPedido(j));		
	}
	
	private int numero(JsonObject j){
		return i(j,"nAdicao"); 
	}
	
	private int sequencia(JsonObject j){
		return i(j,"nSeqAdicC"); 
	}
	
	private String codigoFabricante(JsonObject j){
		return s(j,"cFabricante"); 
	}
	
	private Dinheiro desconto(JsonObject j){
		return new Dinheiro(d(j,"vDescDI")); 
	}
	
	private int drawback(JsonObject j){
		return i(j,"nDraw"); 
	}
	
	private int pedido(JsonObject j){
		return i(j,"xPed"); 
	}
	
	private int itemPedido(JsonObject j){
		return i(j,"nItemPed"); 
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
}