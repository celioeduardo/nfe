package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.transporte.ModalidadeFrete;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Placa;
import com.hadrion.nfe.dominio.modelo.nf.transporte.RetencaoIcms;
import com.hadrion.nfe.dominio.modelo.nf.transporte.TipoVeiculo;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transportador;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Veiculo;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Volume;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json.TransportadorDeserializer;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json.TransporteDeserializer;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json.TransporteTradutorJson;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json.VeiculoDeserializer;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.InscricaoEstadual;

public class TransporteTradutorJsonTest {
	private static String JSON = 
			"{\r\n" + 
			"	\"tipoFrete\" : \"9\",\r\n" + 
			"	\"transportadora\" : {\r\n" + 
			"		\"cnpj\" : \"74230061000181\",\r\n" + 
			"		\"nome\" : \"CAD COMEDIA\",\r\n" + 
			"		\"ie\" : \"123456789\",\r\n" + 
			"		\"endereco\" : \"CENTRO\",\r\n" + 
			"		\"municipio\" : \"REBS\",\r\n" + 
			"		\"uf\" : \"SP\"\r\n" + 
			"	},\r\n" + 
			"	\"veiculo\" : {\r\n" + 
			"		\"tipo\" : \"VEICULO\",\r\n" + 
			"		\"placa\" : \"AAA9999\",\r\n" + 
			"		\"uf\" : \"SP\",\r\n" + 
			"		\"rntc\" : \"\"\r\n" + 
			"	}\r\n" +
			"}";

	
	private GsonBuilder gsonBuilder;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Transportador.class, new TransportadorDeserializer());
		gsonBuilder.registerTypeAdapter(Veiculo.class, new VeiculoDeserializer());
		gsonBuilder.registerTypeAdapter(Veiculo.class, new TransporteDeserializer());
	}

	@Test
	public void converter() throws IOException{
		TransporteTradutorJson tradutor = new TransporteTradutorJson(JSON);
		Transporte transporte = tradutor.converterTransporte();
		
		Endereco endereco = new Endereco("CENTRO", 
				null,
				null,
				null,
			    new Municipio(-1,"REBS",Uf.valueOf("SP")),
			    null,
			    null,
			    null);
		
		InscricaoEstadual ie = new InscricaoEstadual("123456789"); 
		
		Transportador transportador = new Transportador(
				new Cnpj(74230061000181L), 
				null, 
				"CAD COMEDIA", 
				ie, 
				endereco);
		
		Veiculo veiculo = new Veiculo(
				TipoVeiculo.VEICULO, 
				new Placa(Uf.SP,"AAA9999"), 
				"", 
				"");

		RetencaoIcms retencaoIcms = null;

		assertEquals(new Transporte(ModalidadeFrete.SEM_FRETE, transportador, retencaoIcms, veiculo, new ArrayList<Volume>()),
					 transporte);
		
	}

}
