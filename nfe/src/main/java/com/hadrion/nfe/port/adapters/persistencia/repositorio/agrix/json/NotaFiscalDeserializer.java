package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static com.hadrion.util.DataUtil.data;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.nf.Exportacao;
import com.hadrion.nfe.dominio.modelo.nf.Finalidade;
import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Cobranca;
import com.hadrion.nfe.dominio.modelo.nf.informacao.Informacao;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalEntrega;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalRetirada;
import com.hadrion.nfe.dominio.modelo.nf.publico.Destinatario;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;

public class NotaFiscalDeserializer implements JsonDeserializer<NotaFiscal>{

	@Override
	public NotaFiscal deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();
		
		final NotaFiscal nf = new NotaFiscal(
				new NotaFiscalId(j.get("NotaFiscalId").getAsString()),
				null,
				null,
				new Modelo(j.get("modelo").getAsString()),
				new Serie(j.get("serie").getAsLong()),
				j.get("numero").getAsLong(), 
				data(j.get("emissao").getAsString()), 
				data(j.get("dataHora").getAsString()),
				null,
				null,//Formato DANFE
				null,//TipoEmissao.valueOf(j.get("tipoEmissao").getAsString()),
				TipoOperacao.valueOf(j.get("tipoOperacao").getAsString()),
				LocalDestino.valueOf(j.get("localDestino").getAsString()), 
				null,
				j.get("consumidorFinal").getAsBoolean(),
				Finalidade.valueOf(j.get("finalidade").getAsString()),
				null,
				null,
				referencias(j), //Set<NotaFiscalId> referencias,
				emitente(j), //Emitente emitente,
				destinatario(j), //Destinatario destinatario,
				localRetirada(j), //LocalRetirada localRetirada,
				localEntrega(j), //LocalEntrega localEntrega,
				itens(j), //j.get("itens").getAsJsonArray(), //List<Item> itens,
				null,//TODO incluir transporte
				cobranca(j), //Cobranca cobranca,
				informacaoFisco(j), //Informacao informacaoFisco,
				informacaoContribuinte(j), //Informacao informacaoContribuinte,
				exportacao(j),
				null); //Contingência
		
		return nf;
	}
	
	private List<Item> itens(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("itens").toString()).converterItens();
	}
	private List<Referencia> referencias(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("referencias").toString()).converterReferencias();
	}
	private Emitente emitente(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("emitente").toString()).converterEmitente();
	}
	private Destinatario destinatario(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("destinatario").toString()).converterDestinatario();
	}
	private LocalEntrega localEntrega(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("localEntrega").toString()).converterLocalEntrega();
	}
	private LocalRetirada localRetirada(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("localRetirada").toString()).converterLocalRetirada();
	}
	private Cobranca cobranca(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("cobranca").toString()).converterCobranca();
	}
	private Informacao informacaoFisco(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("observacaoFisco").toString()).converterObservacao();
	}
	private Informacao informacaoContribuinte(JsonObject j){
		if (tem(j,"observacao")){
			return new NotaFiscalTradutorJson(j.get("observacao").toString()).converterObservacao();
		}
		return null;
	}
	private Exportacao exportacao(JsonObject j){
		if (tem(j,"exportacao")){		
			return new NotaFiscalTradutorJson(j.get("exportacao").toString()).converterExportacao();
		}
		return null;
	}
	boolean tem(JsonObject j, String propriedade){
		return j.has(propriedade);
	}
	
}
