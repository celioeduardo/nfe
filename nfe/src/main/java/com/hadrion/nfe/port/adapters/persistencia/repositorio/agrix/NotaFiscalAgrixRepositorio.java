package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import static com.hadrion.util.DataUtil.data;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json.DescritorNotaFiscalDeserializer;

@Repository
@Transactional
@Profile("teste")
public class NotaFiscalAgrixRepositorio implements NotaFiscalRepositorio{

	@Autowired
	JdbcTemplate jdbc;
	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Override
	public List<NotaFiscal> notasPendentesAutorizacao() {
		 return jdbc.query("Select * From VW_NFE_PENDENTES", new RowMapper<NotaFiscal>(){
			@Override
			public NotaFiscal mapRow(ResultSet rs, int rowNum){
				return NotaFiscalTradutor.paraNotaFiscal(rs);
			}
		});
	}
	@Override
	public List<DescritorNotaFiscal> notasPendentesAutorizacaoResumo() {

		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DescritorNotaFiscal.class, new DescritorNotaFiscalDeserializer());
		gson = gsonBuilder.create();
		
		SimpleJdbcCall call = new SimpleJdbcCall(this.jdbc)
		.withCatalogName("pcg_nf_json_adapter")
		.withFunctionName("obterPendentes")
		.declareParameters(new SqlParameter("empresa", Types.DOUBLE))
		.declareParameters(new SqlParameter("filial", Types.DOUBLE))
		.declareParameters(new SqlParameter("inicio", Types.DATE))
		.declareParameters(new SqlParameter("fim", Types.DATE))
		.declareParameters(new SqlParameter("id", Types.VARCHAR))
		.declareParameters(new SqlParameter("usuario", Types.VARCHAR))
		.declareParameters(
				new SqlOutParameter("RETURN_VALUE", Types.CLOB));

		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("empresa",86675642000106L , Types.DOUBLE);
		params.addValue("filial", 86675642000106L, Types.DOUBLE);
		params.addValue("inicio", data("22/09/2014"), Types.DATE);
		params.addValue("fim", data("22/09/2014"), Types.DATE);
		params.addValue("id", "", Types.VARCHAR);
		params.addValue("usuario", "", Types.VARCHAR);
		
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

	@Override
	public void salvar(NotaFiscal notaFiscal) {
	}

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
	}

	@Override
	public void limpar() {}

}
