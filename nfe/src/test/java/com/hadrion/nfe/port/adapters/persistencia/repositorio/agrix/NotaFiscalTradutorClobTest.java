package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Types;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hadrion.nfe.dominio.config.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("teste")
public class NotaFiscalTradutorClobTest {

	@Autowired
	JdbcTemplate jdbc;

	@Before
	public void setUp() {
	}

	@Test @Ignore
	@Transactional
	public void obterClob() throws SQLException {

		// Gson g=new Gson();
		//
		SimpleJdbcCall call = new SimpleJdbcCall(this.jdbc)
				.withCatalogName("pcg_nf_json_adapter")
				.withFunctionName("construirJson")
				.declareParameters(new SqlParameter("vc2_es", Types.VARCHAR))
				.declareParameters(new SqlParameter("r", Types.VARCHAR))
				.declareParameters(
						new SqlOutParameter("RETURN_VALUE", Types.CLOB));

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("vc2_es", "S", Types.VARCHAR);
		params.addValue("r", "AACTBxAAHAAFox6AAM", Types.VARCHAR);

		call.compile();

		Clob clob = call.executeFunction(Clob.class, params);
		String conteudo =  clob.getSubString(1, (int)clob.length());
		System.out.println(conteudo);
		System.out.println("TAMANHO DO CLOB: "+clob.length());
		//clobToString(clob);
//		Reader reader = null;
//		reader = clob.getCharacterStream();
//		System.out.println(clobToString(clob));

	}
	@Test @Ignore
	@Transactional
	public void obterNotas() throws SQLException {
		
		SimpleJdbcCall call = new SimpleJdbcCall(this.jdbc)
		.withCatalogName("pcg_nf_json_adapter")
		.withFunctionName("obterNotas")
		.declareParameters(new SqlParameter("vc", Types.CLOB))
		.declareParameters(
				new SqlOutParameter("RETURN_VALUE", Types.CLOB));
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		
		params.addValue("vc", "'02931ADEA95F0FC5E050007F01001783','02CB7F2F30E0D13CE050007F01002AEB'", Types.CLOB);
		
		call.compile();
		
		Clob ret = call.executeFunction(Clob.class, params);
		String conteudo =  ret.getSubString(1, (int)ret.length());
		System.out.println(conteudo);
		System.out.println("TAMANHO DO CLOB: "+ret.length());
		
	}
}
