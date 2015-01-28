package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import static org.junit.Assert.assertEquals;

import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.config.Application;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.ModoOperacao;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoEmissao;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.DescritorNotaFiscalDeserializer;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class NotaFiscalDescritorTradutorClobTest {

	@Autowired
	JdbcTemplate jdbc;
	private GsonBuilder gsonBuilder;
	private Gson gson;

	@Before
	public void setUp() {
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DescritorNotaFiscal.class, 
				new DescritorNotaFiscalDeserializer(Ambiente.HOMOLOGACAO,ModoOperacao.NORMAL));
		gson = gsonBuilder.create();
		
	}

	@Test @Ignore
	@Transactional
	public void obterClob() throws SQLException {

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
		params.addValue("id", "03ADF8A01D1727DDE050007F01002730", Types.VARCHAR);
		params.addValue("usuario", "", Types.VARCHAR);
		//params.addValue("notaFiscalId", "", Types.VARCHAR);

		call.compile();

		Clob clob = call.executeFunction(Clob.class, params);
		String conteudo =  clob.getSubString(1, (int)clob.length());

		List<DescritorNotaFiscal> lista = Arrays.asList(gson.fromJson(conteudo, DescritorNotaFiscal[].class));
		assertEquals(new DescritorNotaFiscal(new NotaFiscalId("H-03ADF8A01D1727DDE050007F01002730"),
				"E",
				new Cnpj(86675642000106L),
				new Cnpj(86675642000106L),
				204818L,
				new Serie(2L),
				null,
				TipoEmissao.NORMAL,
				data("22/09/14"),
				data("22/09/14"),
				"A",
				157L,
				"AKEMI YOSHIE YAMAGUCHI",
				new Dinheiro(1000.))
		,lista.get(0));

		
		//System.out.println(conteudo);
		//System.out.println("TAMANHO DO CLOB: "+clob.length());
		//clobToString(clob);
//		Reader reader = null;
//		reader = clob.getCharacterStream();
//		System.out.println(clobToString(clob));

	}

	/*private String clobToString(Clob clob) {
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
	}*/
	private Date data(String data){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			return formatter.parse(data);
		} catch (ParseException e) {
			return null;
		}	
		
	}
}
