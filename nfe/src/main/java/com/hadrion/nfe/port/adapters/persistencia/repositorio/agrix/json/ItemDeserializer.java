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
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoIndireta;
import com.hadrion.nfe.dominio.modelo.nf.item.Gtin;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.item.Ncm;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
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
		Dinheiro frete=null, seguro=null, desconto=null,acessorias=null,valorTotalBruto=null;
		Exportacao exportacao=null;
		Combustivel combustivel=null;		
		
		codigo=s(j,"codigo");
		descricao = s(j,"descricao");
		nve=s(j,"nve");
		extipi=s(j,"extIpi");
		unidadeComercial=s(j,"unidade");
		unidadeTributavel=s(j,"unidadeTributavel");
		gtin = gtin(j,"gtin");
		ncm = new Ncm(s(j,"ncm")); 
		cfop = new Cfop(l(j,"cfop"));
		quantidadeComercial = new Quantidade(d(j,"quantidade"));
		quantidadeTributavel = new Quantidade(d(j,"quantidadeTributavel"));
		valorUnitarioComercializacao=d(j,"unitario");
		valorTotalBruto = new Dinheiro(d(j,"bruto"));
		gtinTributavel= gtin(j,"gtinTributavel");;
		valorUnitarioTributacao=new Double(d(j,"unitarioTributacao"));
		frete = new Dinheiro(d(j,"frete"));
		seguro = new Dinheiro(d(j,"seguro"));
		desconto = new Dinheiro(d(j,"desconto"));
		acessorias = new Dinheiro(d(j,"acessorias"));
		exportacao = exportacao(j);
		combustivel = null;	
		
		final Item item = new Item(
				new DescritorProduto(codigo, gtin, descricao, ncm, nve, extipi, cfop, unidadeComercial, 
						quantidadeComercial, valorUnitarioComercializacao, valorTotalBruto, gtinTributavel, 
						unidadeTributavel, quantidadeTributavel, valorUnitarioTributacao, frete, seguro, 
						desconto, acessorias, exportacao, combustivel),
				null,//IMPOSTO
				"ADICIONAL");//INFORMACAO ADICIONAL
		
		return item;
	}

	private Long l(JsonObject j, String propriedade){
		return j.get(propriedade).getAsLong();
	}
	private Double d(JsonObject j, String propriedade){
		return j.get(propriedade).getAsDouble();
	}

	private String s(JsonObject j, String propriedade){
		return j.get(propriedade).getAsString();
	}
	
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}

	private Gtin gtin(JsonObject j, String propriedade){
		return tem(j,propriedade) ? new Gtin(s(j,propriedade)) : null;
	}
	
	private Exportacao exportacao(JsonObject j){
		Exportacao exportacao = null;
		if (tem(j,"exportacao")){
			JsonObject g = j.get("exportacao").getAsJsonObject();
			
			return new Exportacao(g.get("drawback").getAsLong(), 
					new ExportacaoIndireta(g.get("registroExportacao").getAsLong(), 
							new ChaveAcesso(g.get("chaveNfRecebida").getAsString()),
							new Quantidade(g.get("quantidadeExportada").getAsDouble())));
		}			
		return exportacao;			
	}
}
