package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import static com.hadrion.util.DataUtil.data;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoEmissao;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json.DescritorNotaFiscalDeserializer;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;

public class DescritorNotaFiscalTradutorJsonTest {
	private static String JSON = 
			"{\r\n" + 
			"		\"id\" : \"0215C27A36D5B0E0E050007F01004D45\",\r\n" + 
			"		\"tipo\" : \"E\",\r\n" + 
			"		\"empresa\" : 86675642000106,\r\n" + 
			"		\"filial\" : 86675642000700,\r\n" + 
			"		\"numero\" : 2121,\r\n" + 
			"		\"serie\" : \"1\",\r\n" + 
			"		\"emissao\" : \"02/09/14\",\r\n" + 
			"		\"movimentacao\" : \"02/09/14\",\r\n" + 
			"		\"publicoTipo\" : \"C\",\r\n" + 
			"		\"publicoCodigo\" : 56,\r\n" + 
			"		\"publicoNome\" : \"CEREALISTA SIQUEIRANDRADE LTDA.         \",\r\n" +
			"		\"valor\":1000" + 
			"}";

	
	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DescritorNotaFiscal.class, new DescritorNotaFiscalDeserializer(Ambiente.HOMOLOGACAO));
		gson = gsonBuilder.create();
	}

	@Test
	public void converter() throws IOException{
		
		DescritorNotaFiscal descritor = gson.fromJson(JSON, DescritorNotaFiscal.class);

		assertEquals(new DescritorNotaFiscal(new NotaFiscalId("H-0215C27A36D5B0E0E050007F01004D45"),
				"E",
				new Cnpj(86675642000106L),
				new Cnpj(86675642000700L),
				2121L,
				new Serie(1L),
				null,
				TipoEmissao.NORMAL,
				data("02/09/14"),
				data("02/09/14"),
				"C",
				56L,
				"CEREALISTA SIQUEIRANDRADE LTDA.         ",
				new Dinheiro(1000.)),descritor);
		
	}

}
