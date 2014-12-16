package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static com.hadrion.util.DataUtil.data;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.nf.Exportacao;
import com.hadrion.nfe.dominio.modelo.nf.Finalidade;
import com.hadrion.nfe.dominio.modelo.nf.FormaPagamento;
import com.hadrion.nfe.dominio.modelo.nf.FormatoDanfe;
import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.Presenca;
import com.hadrion.nfe.dominio.modelo.nf.Processo;
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

public class NotaFiscalDeserializer extends AbstractDeserializer implements JsonDeserializer<NotaFiscal>{
	private Ambiente ambiente;
	
	public NotaFiscalDeserializer(Ambiente ambiente){
		this.ambiente = ambiente;
	}
	
	@Override
	public NotaFiscal deserialize(JsonElement jsonSource, Type type,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject j = jsonSource.getAsJsonObject();
		
		final NotaFiscal nf = new NotaFiscal(
				ambiente,
				criarNotaFiscalId(s(j,"NotaFiscalId"), ambiente),
				new FilialId("86675642000106"), //TODO Obter Filial
				s(j,"naturezaOperacao"),
				FormaPagamento.A_VISTA,//TODO forma pagamento
				new Modelo(s(j,"modelo")),
				new Serie(l(j,"serie")),
				l(j,"numero"), 
				data(s(j,"emissao")), 
				data(s(j,"dataHora")),
				1,//TODO codigo numerico
				FormatoDanfe.RETRATO,//TODO Formato DANFE
				TipoEmissao.NORMAL,//TODO TipoEmissao.valueOf(j.get("tipoEmissao").getAsString()),
				TipoOperacao.valueOf(s(j,"tipoOperacao")),
				LocalDestino.valueOf(s(j,"localDestino")), 
				emitente(j).endereco().municipio(),//TODO MUNICIPIO FATO GERADOR
				b(j,"consumidorFinal"),
				Finalidade.valueOf(s(j,"finalidade")),
				Presenca.OUTROS,//TODO presenca
				Processo.APLICATIVO_CONTRIBUINTE,//TODO processo
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
		return new NotaFiscalTradutorJson(j.get("itens").toString(),ambiente).converterItens();
	}
	private List<Referencia> referencias(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("referencias").toString(),ambiente).converterReferencias();
	}
	private Emitente emitente(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("emitente").toString(),ambiente).converterEmitente();
	}
	private Transporte transporte(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("transporte").toString(),ambiente).converterTransporte();
	}
	private Destinatario destinatario(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("destinatario").toString(),ambiente).converterDestinatario();
	}
	private LocalEntrega localEntrega(JsonObject j){
		if (!tem(j,"localEntrega"))
			return null;
		return new NotaFiscalTradutorJson(j.get("localEntrega").toString(),ambiente).converterLocalEntrega();
	}
	private LocalRetirada localRetirada(JsonObject j){
		if (!tem(j,"localRetirada"))
			return null;
		return new NotaFiscalTradutorJson(j.get("localRetirada").toString(),ambiente).converterLocalRetirada();
	}
	private Cobranca cobranca(JsonObject j){
		return new NotaFiscalTradutorJson(j.get("cobranca").toString(),ambiente).converterCobranca();
	}
	private Informacao informacaoFisco(JsonObject j){
		if (tem(j,"observacaoFisco")){
			return new NotaFiscalTradutorJson(j.get("observacaoFisco").toString(),ambiente).converterObservacao();
		}
		return null;
	}
	private Informacao informacaoContribuinte(JsonObject j){
		if (tem(j,"observacao")){
			return new NotaFiscalTradutorJson(j.get("observacao").toString(),ambiente).converterObservacao();
		}
		return null;
	}
	private Exportacao exportacao(JsonObject j){
		if (tem(j,"exportacao")){		
			return new NotaFiscalTradutorJson(j.get("exportacao").toString(),ambiente).converterExportacao();
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
