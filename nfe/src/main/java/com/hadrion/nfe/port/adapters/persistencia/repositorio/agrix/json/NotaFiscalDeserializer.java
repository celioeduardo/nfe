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
import com.hadrion.nfe.dominio.modelo.nf.TipoEmissao;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Cobranca;
import com.hadrion.nfe.dominio.modelo.nf.informacao.Informacao;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalEntrega;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalRetirada;
import com.hadrion.nfe.dominio.modelo.nf.publico.Destinatario;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;

public class NotaFiscalDeserializer implements JsonDeserializer<NotaFiscal>{

	@Override
	public NotaFiscal deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();
		
		final NotaFiscal nf = new NotaFiscal(
				new NotaFiscalId(s(j,"NotaFiscalId")),
				s(j,"naturezaOperacao"),
				null,
				new Modelo(s(j,"modelo")),
				new Serie(l(j,"serie")),
				l(j,"numero"), 
				data(s(j,"emissao")), 
				data(s(j,"dataHora")),
				null,
				null,//TODO Formato DANFE
				TipoEmissao.NORMAL,//TODO TipoEmissao.valueOf(j.get("tipoEmissao").getAsString()),
				TipoOperacao.valueOf(s(j,"tipoOperacao")),
				LocalDestino.valueOf(s(j,"localDestino")), 
				null,
				b(j,"consumidorFinal"),
				Finalidade.valueOf(s(j,"finalidade")),
				null,//TODO presenca
				null,//TODO processo
				referencias(j),
				emitente(j),
				destinatario(j),
				localRetirada(j),
				localEntrega(j),
				itens(j),
				transporte(j),
				cobranca(j),
				informacaoFisco(j),
				informacaoContribuinte(j),
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
	private Transporte transporte(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("transporte").toString()).converterTransporte();
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
		if (tem(j,"observacaoFisco")){
			return new NotaFiscalTradutorJson(j.get("observacaoFisco").toString()).converterObservacao();
		}
		return null;
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
	
	private Long l(JsonObject j, String propriedade){
		return j.get(propriedade).getAsLong();
	}

	private Boolean b(JsonObject j, String propriedade){
		return j.get(propriedade).getAsBoolean();
	}

	private String s(JsonObject j, String propriedade){
		return j.has(propriedade) ? j.get(propriedade).getAsString() : null;
	}

}
