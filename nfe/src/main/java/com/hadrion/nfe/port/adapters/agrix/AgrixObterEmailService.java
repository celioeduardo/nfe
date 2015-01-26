package com.hadrion.nfe.port.adapters.agrix;

import java.sql.Types;
import java.util.ArrayList;
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

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.ObterEmailService;
import com.hadrion.nfe.tipos.Email;

@Service
public class AgrixObterEmailService implements ObterEmailService{
	
	@Autowired
	@Qualifier("agrix")
	JdbcTemplate jdbc;
	
	@Override
	public List<Email> obterEmailsContatoDaNotaFiscal(NotaFiscalId notaFiscalId) {
		SimpleJdbcCall call = new SimpleJdbcCall(this.jdbc)
			.withSchemaName(AgrixUtil.schema())
			.withCatalogName("pcg_nf_json_adapter")
			.withFunctionName("obterEmails")
			.declareParameters(new SqlParameter("nfid", Types.VARCHAR))
			.declareParameters(
					new SqlOutParameter("RETURN_VALUE", Types.VARCHAR));

		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("nfid", AgrixUtil.notaFiscalIdToGuid(notaFiscalId), Types.VARCHAR);
		call.compile();
		
		String retorno = call.executeFunction(String.class, params);
		
		return parse(retorno);
		
	}
	
	private List<Email> parse(String conteudo){
		if (conteudo == null || conteudo.isEmpty())
			return Collections.emptyList();
		
		String emails[] = conteudo.split(";");
		List<Email> result = new ArrayList<Email>();
		for (String email : emails) 
			result.add(new Email(email));
		
		return result;
	}
	

}
