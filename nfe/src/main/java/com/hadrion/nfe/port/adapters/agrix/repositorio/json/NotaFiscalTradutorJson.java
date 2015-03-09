package com.hadrion.nfe.port.adapters.agrix.repositorio.json;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.Ambiente;
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
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transportador;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Veiculo;
import com.hadrion.nfe.dominio.modelo.notista.Notista;
import com.hadrion.nfe.tipos.Dinheiro;


public class NotaFiscalTradutorJson {
	
	private String json;
	final GsonBuilder gsonBuilder;
	final Gson gson;
	
	public NotaFiscalTradutorJson(String json, Ambiente ambiente){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(NotaFiscal.class, new NotaFiscalDeserializer(ambiente));
		gsonBuilder.registerTypeAdapter(Item.class, new ItemDeserializer());
		gsonBuilder.registerTypeAdapter(Referencia.class, new ReferenciaDeserializer());
		gsonBuilder.registerTypeAdapter(Emitente.class, new EmitenteDeserializer());
		gsonBuilder.registerTypeAdapter(Destinatario.class, new DestinatarioDeserializer());
		gsonBuilder.registerTypeAdapter(LocalEntrega.class, new LocalEntregaDeserializer());
		gsonBuilder.registerTypeAdapter(LocalRetirada.class, new LocalRetiradaDeserializer());
		gsonBuilder.registerTypeAdapter(Dinheiro.class, new DinheiroDeserializer());
		gsonBuilder.registerTypeAdapter(Cobranca.class, new CobrancaDeserializer());
		gsonBuilder.registerTypeAdapter(Informacao.class, new InformacaoDeserializer());
		gsonBuilder.registerTypeAdapter(Transportador.class, new TransportadorDeserializer());
		gsonBuilder.registerTypeAdapter(Veiculo.class, new VeiculoDeserializer());
		gsonBuilder.registerTypeAdapter(Transporte.class, new TransporteDeserializer());
		gsonBuilder.registerTypeAdapter(Notista.class, new NotistaDeserializer());
		gson = gsonBuilder.create();
		this.json = json;
	}

	public NotaFiscal[] converterNotaFiscal(){		
		return fromJson(NotaFiscal[].class);
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
	public Notista converterNotista(){
		return fromJson(Notista.class);
	}
	public Transporte converterTransporte(){
		return fromJson(Transporte.class);
	}
	private <T> T fromJson(Class<T> classe){
		return gson.fromJson(json, classe);
	}
}
