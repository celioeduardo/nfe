package com.hadrion.nfe.web;

import java.util.Locale;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.hadrion.nfe.dominio.config.Application;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalFixture;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
@WebAppConfiguration
@IntegrationTest("server.port:0") 
public class NfeRestControllerTest{

	@Autowired
	private NotaFiscalFixture notaFiscalFixture;
	
	@Value("${local.server.port}")
    int port;
	
	private RestTemplate restTemplate;
	
	@Before
	public void setup(){
		restTemplate = new RestTemplate();
	}
	
	@Test
	public void obterUma() throws JSONException{
		
		NotaFiscal nota = notaFiscalFixture.nfEmHomologacaoAutorizadaPersistidaParaTest();
		
		String JSON = 
			String.format("{\r\n" + 
			"	notaFiscalId : \"%s\",\r\n" + 
			"	chave : \"%s\",\r\n" + 
			"	emitente : {\r\n" + 
			"		endereco : {\r\n" + 
			"			municipio : {\r\n" + 
			"				codigo : %s,\r\n" + 
			"				nome : \"%s\",\r\n" + 
			"				uf : \"%s\"\r\n" + 
			"			}\r\n" + 
			"		}\r\n" + 
			"	},\r\n" + 
			"	destinatario : {\r\n" + 
			"		endereco : {\r\n" + 
			"			municipio : {\r\n" + 
			"				codigo : %s,\r\n" + 
			"				nome : \"%s\",\r\n" + 
			"				uf : \"%s\"\r\n" + 
			"			}\r\n" + 
			"		}\r\n" + 
			"	},\r\n" + 
			"	tipoOperacao : \"%s\",\r\n" + 
			"	total : %s,\r\n" + 
			"	transportador : {\r\n" + 
			"		nome : \"%s\",\r\n" + 
			"		cpf : %s,\r\n" + 
			"		cnpj : null\r\n" + 
			"	},\r\n" + 
			"	veiculo : {\r\n" + 
			"		placa : {\r\n" + 
			"			numero : \"%s\",\r\n" + 
			"			uf : \"%s\"\r\n" + 
			"		}\r\n" + 
			"	},\r\n" + 
			"	volumes : [{\r\n" + 
			"		pesoBruto : %s\r\n" + 
			"	}]\r\n" + 
			"}",
			nota.notaFiscalId(),
			nota.chaveAcesso(),
			nota.emitente().endereco().municipio().codigo(),
			nota.emitente().endereco().municipio().nome(),
			nota.emitente().endereco().municipio().uf(),
			nota.destinatario().endereco().municipio().codigo(),
			nota.destinatario().endereco().municipio().nome(),
			nota.destinatario().endereco().municipio().uf(),
			nota.tipoOperacao(),
			String.format(Locale.ENGLISH, "%.2f", nota.total().valor()),
			nota.transportador().get().razaoSocial(),
			nota.transportador().get().cpf(),
			nota.veiculo().get().placa().numero(),
			nota.veiculo().get().placa().uf(),
			String.format(Locale.ENGLISH, "%.2f", nota.volumes().iterator().next().pesoBruto())
			);
		
		String responseBody = restTemplate.getForObject(
				buildUrl("notas_fiscais/%s",nota.notaFiscalId()), 
				String.class);
		
		System.out.println(JSON);
		System.out.println(responseBody);
		
		JSONAssert.assertEquals(JSON, responseBody, false);
	}
	
	@Test 
	public void obterAutorizadas() throws JSONException{
		
		NotaFiscal nota = notaFiscalFixture.nfEmHomologacaoAutorizadaPersistidaParaTest();
		
		String JSON = 
				String.format("[{\r\n" + 
						"	notaFiscalId : \"%s\",\r\n" + 
						"	chave : \"%s\",\r\n" + 
						"	destinatario : {\r\n" + 
						"		endereco : {\r\n" + 
						"			municipio : {\r\n" + 
						"				codigo : %s,\r\n" + 
						"				nome : \"%s\",\r\n" + 
						"				uf : \"%s\"\r\n" + 
						"			}\r\n" + 
						"		}\r\n" + 
						"	},\r\n" + 
						"	tipoOperacao : \"%s\",\r\n" + 
						"	total : %s,\r\n" + 
						"	transportador : {\r\n" + 
						"		nome : \"%s\",\r\n" + 
						"		cpf : %s,\r\n" + 
						"		cnpj : null\r\n" + 
						"	},\r\n" + 
						"	veiculo : {\r\n" + 
						"		placa : {\r\n" + 
						"			numero : \"%s\",\r\n" + 
						"			uf : \"%s\"\r\n" + 
						"		}\r\n" + 
						"	},\r\n" + 
						"	volumes : [{\r\n" + 
						"		pesoBruto : %s\r\n" + 
						"	}]\r\n" + 
						"}]",
						nota.notaFiscalId(),
						nota.chaveAcesso(),
						nota.destinatario().endereco().municipio().codigo(),
						nota.destinatario().endereco().municipio().nome(),
						nota.destinatario().endereco().municipio().uf(),
						nota.tipoOperacao(),
						String.format(Locale.ENGLISH, "%.2f", nota.total().valor()),
						nota.transportador().get().razaoSocial(),
						nota.transportador().get().cpf(),
						nota.veiculo().get().placa().numero(),
						nota.veiculo().get().placa().uf(),
						String.format(Locale.ENGLISH, "%.2f", nota.volumes().iterator().next().pesoBruto())
						);
		
		String responseBody = restTemplate.getForObject(
				buildUrl("notas_fiscais/autorizadas?ambiente=HOMOLOGACAO&empresa=4007474000116&filial=4007474000116",nota.notaFiscalId()), 
				String.class);
		
		System.out.println(JSON);
		System.out.println(responseBody);
		
		JSONAssert.assertEquals(JSON, responseBody, false);
	}

	@Test(expected=HttpClientErrorException.class)
	public void recursoNaoEncontado() throws JSONException{
		restTemplate.getForEntity(buildUrl("notas_fiscais/xxxxxxxx"), String.class);
	}
	
	private String buildUrl(String url,Object ... args){
		
		return String.format("http://localhost:"+port+"/"+url,args);
	}
	
}

