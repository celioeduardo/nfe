package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.nf.Exportacao;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Cobranca;
import com.hadrion.nfe.dominio.modelo.nf.informacao.Informacao;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalEntrega;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalRetirada;
import com.hadrion.nfe.dominio.modelo.nf.publico.Destinatario;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.tipos.Dinheiro;


public class NotaFiscalTradutorJson {
	
	private String json;
	final GsonBuilder gsonBuilder;
	final Gson gson;
	
	public NotaFiscalTradutorJson(String json){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(NotaFiscal.class, new NotaFiscalDeserializer());
		gsonBuilder.registerTypeAdapter(Item.class, new ItemDeserializer());
		gsonBuilder.registerTypeAdapter(Referencia.class, new ReferenciaDeserializer());
		gsonBuilder.registerTypeAdapter(Emitente.class, new EmitenteDeserializer());
		gsonBuilder.registerTypeAdapter(Destinatario.class, new DestinatarioDeserializer());
		gsonBuilder.registerTypeAdapter(LocalEntrega.class, new LocalEntregaDeserializer());
		gsonBuilder.registerTypeAdapter(LocalRetirada.class, new LocalRetiradaDeserializer());
		gsonBuilder.registerTypeAdapter(Date.class, new CpfDeserializer());
		gsonBuilder.registerTypeAdapter(Dinheiro.class, new DinheiroDeserializer());
		gsonBuilder.registerTypeAdapter(Cobranca.class, new CobrancaDeserializer());
		gsonBuilder.registerTypeAdapter(Informacao.class, new InformacaoDeserializer());
		gson = gsonBuilder.create();
		this.json = json;
	}

	public NotaFiscal converterNotaFiscal(){		
		return fromJson(NotaFiscal.class);
	}
	public Item converterItem(){		
		return fromJson(Item.class);
	}
	public List<Item> converterItens() {
		List<Item> result = Arrays.asList(gson.fromJson(json, Item[].class)); 
		return result; 
	}
	public List<Referencia> converterReferencias() {
		return Arrays.asList(gson.fromJson(json, Referencia[].class));
	}	
	public Emitente converterEmitente() {
		return fromJson(Emitente.class);
	}	
	public Destinatario converterDestinatario() {
		return fromJson(Destinatario.class); 
	}

	public LocalEntrega converterLocalEntrega() {
		return fromJson(LocalEntrega.class);
	}
	public LocalRetirada converterLocalRetirada() {
		return fromJson(LocalRetirada.class);
	}
	public Cobranca converterCobranca(){
		return fromJson(Cobranca.class);
	}
	public Informacao converterObservacao() {
		return fromJson(Informacao.class);
	}
	public Exportacao converterExportacao(){
		return fromJson(Exportacao.class);
	}
	private <T> T fromJson(Class<T> classe){
		return gson.fromJson(json, classe);
	}
}
