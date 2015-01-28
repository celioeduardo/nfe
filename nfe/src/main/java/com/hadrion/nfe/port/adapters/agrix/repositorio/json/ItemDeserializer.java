package com.hadrion.nfe.port.adapters.agrix.repositorio.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.cofins.Cofins;
import com.hadrion.nfe.dominio.modelo.cofins.CstCofins;
import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
import com.hadrion.nfe.dominio.modelo.nf.item.Cfop;
import com.hadrion.nfe.dominio.modelo.nf.item.Combustivel;
import com.hadrion.nfe.dominio.modelo.nf.item.DescritorProduto;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoIndireta;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoItem;
import com.hadrion.nfe.dominio.modelo.nf.item.Gtin;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.item.Ncm;
import com.hadrion.nfe.dominio.modelo.nf.item.imposto.Imposto;
import com.hadrion.nfe.dominio.modelo.pis.CstPis;
import com.hadrion.nfe.dominio.modelo.pis.Pis;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Quantidade;

public class ItemDeserializer implements JsonDeserializer<Item>{

	@Override
	public Item deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();
		
		String codigo = null,descricao = null,nve=null,extipi=null,unidadeComercial=null
				,unidadeTributavel=null,informacaoAdicional=null;
		Gtin gtin = null,gtinTributavel=null;
		Ncm ncm=null; 
		Cfop cfop=null;
		Quantidade quantidadeComercial=null,quantidadeTributavel=null;
		Double valorUnitarioComercializacao=null,valorUnitarioTributacao=null;
		Dinheiro frete=null, seguro=null, desconto=null,acessorias=null,valorTotalBruto=null;
		ExportacaoItem exportacao=null;
		Combustivel combustivel=null;		

		codigo=s(j,"codigo");
		descricao = s(j,"descricao");
		nve=tem(j,"nve") ? s(j,"nve") : null;
		extipi= tem(j,"extIpi") ? s(j,"extIpi") : null;
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
		informacaoAdicional = s(j,"informacaoAdicional");
		final Item item = new Item(
				new DescritorProduto(codigo, gtin, descricao, ncm, nve, extipi, cfop, unidadeComercial, 
						quantidadeComercial, valorUnitarioComercializacao, valorTotalBruto, gtinTributavel, 
						unidadeTributavel, quantidadeTributavel, valorUnitarioTributacao, frete, seguro, 
						desconto, acessorias, exportacao, combustivel),
				imposto(j),
				informacaoAdicional);
		
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
	private Integer i(JsonObject j, String propriedade){
		return j.get(propriedade).getAsInt();
	}
	
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}

	private Gtin gtin(JsonObject j, String propriedade){
		return tem(j,propriedade) ? new Gtin(s(j,propriedade)) : null;
	}
	
	private ExportacaoItem exportacao(JsonObject j){
		if (tem(j,"exportacao")){
			JsonObject g = j.get("exportacao").getAsJsonObject();
			
			return new ExportacaoItem(g.get("drawback").getAsLong(), 
					new ExportacaoIndireta(g.get("registroExportacao").getAsLong(), 
							new ChaveAcesso(g.get("chaveNfRecebida").getAsString()),
							new Quantidade(g.get("quantidadeExportada").getAsDouble())));
		}			
		return null;			
	}
	private Imposto imposto(JsonObject j){
		
		Icms icms = null; Pis pis = null; Cofins cofins = null;JsonObject g = null;JsonObject f = null;
		
		f = j.get("imposto").getAsJsonObject();
		g = f.get("icms").getAsJsonObject();
		icms = Icms.cst_00(Origem.obterPeloCodigo(i(g,"origem")), 
				new Dinheiro(d(g,"base")), 
				new Aliquota(d(g,"aliquota")), 
				DeterminacaoBaseCalculo.VALOR_OPERACAO);

		g = f.get("pis").getAsJsonObject();
		pis = new Pis(CstPis.obterPeloCodigo(i(g,"st")), 
				new Dinheiro(d(g,"base")), 
				new Aliquota(d(g,"aliquota")), 
				.0, 
				d(g,"valor"));

		g = f.get("cofins").getAsJsonObject();
		cofins = new Cofins(CstCofins.obterPeloCodigo(i(g,"st")), 
				new Dinheiro(d(g,"base")), 
				new Aliquota(d(g,"aliquota")), 
				.0, 
				d(g,"valor"));
			
		return new Imposto(Dinheiro.ZERO, icms, pis, cofins);
	}
}