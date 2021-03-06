package com.hadrion.nfe.port.adapters.agrix.repositorio;

import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.ModoOperacao;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.port.adapters.agrix.AgrixUtil;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.DescritorNotaFiscalDeserializer;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.NotaFiscalTradutorJson;

@Service
@Transactional
//@Profile("agrix")
public class AgrixService{

	@Autowired
	@Qualifier("agrix")
	JdbcTemplate jdbc;
	
	@Value("${agrix.segundoOwner:}")
	private String segundoOwner;
	
	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	/*public List<NotaFiscal> notasPendentesAutorizacaoOld() {
		 return jdbc.query("Select * From VW_NFE_PENDENTES", new RowMapper<NotaFiscal>(){
			@Override
			public NotaFiscal mapRow(ResultSet rs, int rowNum){
				return NotaFiscalTradutor.paraNotaFiscal(rs);
			}
		});
	}*/
	public List<NotaFiscal> obterNotas(List<NotaFiscalId> notas, Ambiente ambiente){
		 List<NotaFiscal> result = obterNotas(notas,ambiente,null);
		 
		 if (result.size() > 0) 
			 return result;

		 if (StringUtils.isNotEmpty(segundoOwner))
			 result = obterNotas(notas, ambiente,segundoOwner);
		 
		 return result;
	}
	
	private List<NotaFiscal> obterNotas(List<NotaFiscalId> notas, Ambiente ambiente,String owner) {
		String in = "'" + StringUtils.join(AgrixUtil.notaFiscalIdToGuid(notas), "','") + "'";
		
		SimpleJdbcCall call = new SimpleJdbcCall(this.jdbc)
		.withSchemaName(owner)
		.withCatalogName("pcg_nf_json_adapter")
		.withFunctionName("obterNotas")
		.declareParameters(new SqlParameter("vc", Types.CLOB))
		.declareParameters(
				new SqlOutParameter("RETURN_VALUE", Types.CLOB));

		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("vc", in, Types.CLOB);
		call.compile();
		
		Clob clob = call.executeFunction(Clob.class, params);
		String conteudo; 
		try {
			conteudo =  clob.getSubString(1, (int)clob.length());			
		} catch ( SQLException  e) {
			conteudo=null;
		}		

		/*gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(NotaFiscal.class, new NotaFiscalDeserializer());
		gson = gsonBuilder.create();
		JsonObject jsonSource = gson.fromJson(conteudo, NotaFiscal.class);
		final JsonArray j = jsonSource.getAsJsonArray();
		
		if (j.size() == 0)
			return null;*/
		
		if (conteudo == null || conteudo.isEmpty())
			return Collections.emptyList();

		NotaFiscalTradutorJson tradutor = new NotaFiscalTradutorJson(
//				conteudo.substring(1, conteudo.length()-1),
				conteudo,
				ambiente);
		NotaFiscal[] nf = tradutor.converterNotaFiscal();
		
		return Arrays.asList(nf);
	}
	
	public List<DescritorNotaFiscal> notasPendentesAutorizacaoResumo(
			Ambiente ambiente,
			Long empresa,String filial,Date inicio,Date fim,String notistaId,
			NotaFiscalId notaFiscalId,
			ModoOperacao modoOperacao) {
		
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DescritorNotaFiscal.class, new DescritorNotaFiscalDeserializer(ambiente,modoOperacao));
		gson = gsonBuilder.create();
		
		SimpleJdbcCall call = new SimpleJdbcCall(this.jdbc)
		.withSchemaName(AgrixUtil.schema(filial))
		.withCatalogName("pcg_nf_json_adapter")
		.withFunctionName("obterPendentes")
		//.withoutProcedureColumnMetaDataAccess()
		.declareParameters(new SqlParameter("empresa", Types.DOUBLE))
		.declareParameters(new SqlParameter("filial", Types.VARCHAR))
		.declareParameters(new SqlParameter("inicio", Types.DATE))
		.declareParameters(new SqlParameter("fim", Types.DATE))
		.declareParameters(new SqlParameter("v_notista", Types.VARCHAR))
		.declareParameters(new SqlParameter("notafiscalid", Types.VARCHAR))
		.declareParameters(new SqlParameter("ambiente", Types.VARCHAR))
		.declareParameters(
				new SqlOutParameter("RETURN_VALUE", Types.CLOB));
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("empresa",empresa, Types.DOUBLE);	
		params.addValue("filial", filial, Types.VARCHAR);		
		params.addValue("inicio", inicio, Types.DATE);		
		params.addValue("fim", fim, Types.DATE);		
		params.addValue("notista", notistaId, Types.VARCHAR);		
		if (notaFiscalId==null)
			params.addValue("notafiscalid", "", Types.VARCHAR);
		else
			params.addValue("notafiscalid", notaFiscalId.id(), Types.VARCHAR);			
		params.addValue("ambiente", ambiente, Types.VARCHAR);		
		
		call.compile();
		
		Clob clob = call.executeFunction(Clob.class, params);
		String conteudo;
		try {
			conteudo =  clob.getSubString(1, (int)clob.length());			
		} catch ( SQLException  e) {
			conteudo=null;
		}
		return Arrays.asList(gson.fromJson(conteudo, DescritorNotaFiscal[].class));
		
	}
	/*
	@Override
	public NotaFiscal notaFiscalPeloId(NotaFiscalId notaFiscalId) {
		 return jdbc.query("Select * From VW_NFE Where NFE_GUID = ?",
				 new Object[]{notaFiscalId.id()},
				 new ResultSetExtractor<NotaFiscal>(){
				@Override
				public NotaFiscal extractData(ResultSet rs) throws SQLException{
					if(rs.next())
						return NotaFiscalTradutor.paraNotaFiscal(rs);
					else 
						return null;
				}
			});
	}*/

	public String queryToJson(String query) {
		
		SimpleJdbcCall call = new SimpleJdbcCall(this.jdbc)
		.withCatalogName("pcg_nf_json_adapter")
		.withFunctionName("queryToJson")
		.declareParameters(new SqlParameter("v", Types.VARCHAR))
		.declareParameters(
				new SqlOutParameter("RETURN_VALUE", Types.CLOB));

		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("v", query, Types.VARCHAR);
		
		call.compile();
		
		Clob clob = call.executeFunction(Clob.class, params);
		String conteudo; 
		try {
			conteudo =  clob.getSubString(1, (int)clob.length());			
		} catch ( SQLException  e) {
			System.out.println(e.getMessage());
			conteudo=null;
		}
		
		return conteudo;
	}

	public void simularCancelamento(NotaFiscal nf) {
		
		SimpleJdbcCall call = new SimpleJdbcCall(this.jdbc)
			.withSchemaName(AgrixUtil.schema(nf.filialId().toString()))
			.withCatalogName("pcg_nf_json_adapter")
			.withProcedureName("simularCancelamento")
			.declareParameters(new SqlParameter("nfid", Types.VARCHAR));
	
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("nfid", AgrixUtil.notaFiscalIdToGuid(nf.notaFiscalId()), Types.VARCHAR);
		call.compile();
		try {
			call.execute(params);
		} catch (UncategorizedSQLException e) {
			throw new RuntimeException(e.getMostSpecificCause().getMessage());
		}
	}

	public void comunicarNotaAutorizada(String notaFiscalId, String chaveAcesso,
			String ambiente,String filialId) {
		
		//Não comunicar Autorização quando o ambiente for Homologação
		if (Ambiente.valueOf(ambiente) == Ambiente.HOMOLOGACAO)
			return;
		
		SimpleJdbcCall call = new SimpleJdbcCall(this.jdbc)
			.withSchemaName(AgrixUtil.schema(filialId))
			.withCatalogName("pcg_nf_json_adapter")
			.withProcedureName("comunicarNotaAutorizada")
			.declareParameters(new SqlParameter("nfid", Types.VARCHAR))
			.declareParameters(new SqlParameter("chave", Types.VARCHAR));
	
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("nfid", AgrixUtil.notaFiscalIdToGuid(notaFiscalId), Types.VARCHAR);
		params.addValue("chave", chaveAcesso, Types.VARCHAR);
		call.compile();
		
		call.execute(params);
		
	}

	public void comunicarNotaCancelada(String notaFiscalId,String filialId) {
		//Não comunicar Autorização quando o ambiente for Homologação
		if (AgrixUtil.ambientePelaNotaFiscalId(notaFiscalId) == Ambiente.HOMOLOGACAO)
			return;
		
		SimpleJdbcCall call = new SimpleJdbcCall(this.jdbc)
			.withSchemaName(AgrixUtil.schema(filialId))
			.withCatalogName("pcg_nf_json_adapter")
			.withProcedureName("comunicarCancelamento")
			.declareParameters(new SqlParameter("nfid", Types.VARCHAR));
	
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("nfid", AgrixUtil.notaFiscalIdToGuid(notaFiscalId), Types.VARCHAR);
		call.compile();
		
		call.execute(params);
		
	}
}
