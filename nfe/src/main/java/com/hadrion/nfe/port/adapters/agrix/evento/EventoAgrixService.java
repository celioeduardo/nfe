package com.hadrion.nfe.port.adapters.agrix.evento;

import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class EventoAgrixService {
	
	@Autowired
	@Qualifier("agrix")
	private JdbcTemplate jdbc;

	
	public List<EventoAgrix> obterEventosDesde(long id, String owner){
		
		SimpleJdbcCall call = new SimpleJdbcCall(this.jdbc)
			.withSchemaName(owner)
			.withCatalogName("PCG_EVENTO")
			.withFunctionName("TODOS_EVENTOS_DESDE_COMO_JSON")
			.declareParameters(new SqlParameter("n_id", Types.NUMERIC))
			.declareParameters(
					new SqlOutParameter("RETURN_VALUE", Types.CLOB));

		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("n_id", id, Types.NUMERIC);
		call.compile();
		
		Clob clob = call.executeFunction(Clob.class, params);
		String conteudo; 
		try {
			conteudo =  clob.getSubString(1, (int)clob.length());			
		} catch ( SQLException  e) {
			return Collections.emptyList();
		}		
		
		EventoAgrixTradutor tradutor = new EventoAgrixTradutor();
		
		return tradutor.deserializarEventos(conteudo);
		
	}
	
	
}
