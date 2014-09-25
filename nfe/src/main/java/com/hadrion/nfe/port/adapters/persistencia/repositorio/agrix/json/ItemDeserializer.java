package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.nf.item.Cfop;
import com.hadrion.nfe.dominio.modelo.nf.item.Combustivel;
import com.hadrion.nfe.dominio.modelo.nf.item.DescritorProduto;
import com.hadrion.nfe.dominio.modelo.nf.item.Exportacao;
import com.hadrion.nfe.dominio.modelo.nf.item.Gtin;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.item.Ncm;
import com.hadrion.nfe.dominio.modelo.nf.publico.Crt;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Quantidade;

public class ItemDeserializer implements JsonDeserializer<Item>{

	@Override
	public Item deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();
		
		String codigo = null,descricao = null,nve=null,extipi=null,unidadeComercial=null
				,unidadeTributavel=null;
		Gtin gtin = null,gtinTributavel=null;
		Ncm ncm=null; 
		Cfop cfop=null;
		Quantidade quantidadeComercial=null,quantidadeTributavel=null;
		Double valorUnitarioComercializacao=null,valorUnitarioTributacao=null;
		Dinheiro totalFrete=null, totalSeguro=null, valorDesconto=null,outrasDespesasAcessorias=null,valorTotalBruto=null;
		Exportacao exportacao=null;
		Combustivel combustivel=null;		
		
		codigo=j.get("codigo").getAsString();
		descricao = j.get("descricao").getAsString();
		nve=j.get("nve").getAsString();
		extipi=j.get("extipi").getAsString();
		unidadeComercial=j.get("unidadeComercial").getAsString();
		unidadeTributavel=j.get("unidadeTributavel").getAsString();
		gtin = j.get("gtin").getAsString();
		gtinTributavel=j.get("gtinTributavel").getAsString();
		ncm=null; 
		cfop=null;
		quantidadeComercial=null,quantidadeTributavel=null;
		valorUnitarioComercializacao=null,valorUnitarioTributacao=null;
		totalFrete=null, totalSeguro=null, valorDesconto=null,outrasDespesasAcessorias=null,valorTotalBruto=null;
		exportacao=null;
		combustivel=null;		
		
		final Item item = new Item(
				new DescritorProduto(codigo, gtin, descricao, ncm, nve, extipi, cfop, unidadeComercial, 
						quantidadeComercial, valorUnitarioComercializacao, valorTotalBruto, gtinTributavel, 
						unidadeTributavel, quantidadeTributavel, valorUnitarioTributacao, totalFrete, totalSeguro, 
						valorDesconto, outrasDespesasAcessorias, exportacao, combustivel),
				null,//IMPOSTO
				"ADICIONAL");//INFORMACAO ADICIONAL
		
		return item;
	}

	private Crt crt(JsonObject j){
		return tem(j,"crt")?Crt.valueOf(s(j,"crt")):null;
	}
	
	private Long l(JsonObject j, String propriedade){
		return j.get(propriedade).getAsLong();
	}
	private int i(JsonObject j, String propriedade){
		return j.get(propriedade).getAsInt();
	}

	private String s(JsonObject j, String propriedade){
		return j.get(propriedade).getAsString();
	}
	
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}

}
