package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Before;
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

import com.google.gson.JsonObject;
import com.hadrion.nfe.dominio.config.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("teste")
public class NotaFiscalDescritorTradutorClobTest {

	@Autowired
	JdbcTemplate jdbc;

	@Before
	public void setUp() {
	}

	@Test
	@Transactional
	public void obterClob() throws SQLException {

		SimpleJdbcCall call = new SimpleJdbcCall(this.jdbc)
				.withCatalogName("pcg_nf_json_adapter")
				.withFunctionName("obterPendentes")
				.declareParameters(new SqlParameter("empresa", Types.INTEGER))
				.declareParameters(new SqlParameter("filial", Types.INTEGER))
				.declareParameters(new SqlParameter("inicio", Types.DATE))
				.declareParameters(new SqlParameter("fim", Types.DATE))
				.declareParameters(new SqlParameter("usuario", Types.VARCHAR))				
				.declareParameters(
						new SqlOutParameter("RETURN_VALUE", Types.CLOB));

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("empresa", 1, Types.INTEGER);
		params.addValue("filial", null , Types.INTEGER);
		params.addValue("inicio", data("01/09/2014"), Types.DATE);
		params.addValue("fim", null, Types.DATE);
		params.addValue("usuario", "", Types.VARCHAR);

		call.compile();

		Clob clob = call.executeFunction(Clob.class, params);
		String conteudo =  clob.getSubString(1, (int)clob.length());
		JsonObject j = new JsonObject();
		//System.out.println(conteudo);
		//System.out.println("TAMANHO DO CLOB: "+clob.length());
//		clobToString(clob);
//		Reader reader = null;
//		reader = clob.getCharacterStream();
//		System.out.println(clobToString(clob));

	}

	private String clobToString(Clob clob) {
		Reader reader = null;
		try {
			reader = clob.getCharacterStream();
			BufferedReader buffReader = new BufferedReader(reader);
			String line = null;
			StringBuffer buff = new StringBuffer();
			while ((line = buffReader.readLine()) != null) {
				buff.append(line);
			}
			return buff.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return clob.toString();
	}
	private Date data(String data){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			return formatter.parse(data);
		} catch (ParseException e) {
			return null;
		}	
		
	}
}
