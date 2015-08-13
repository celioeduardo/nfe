package com.hadrion.nfe.web;

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
			"{\r\n" + 
			"	notaFiscalId : \""+nota.notaFiscalId()+"\",\r\n" + 
			"	chave : \""+nota.chaveAcesso()+"\",\r\n" + 
			"	municipioCarregamento : {\r\n" + 
			"		codigo : "+nota.emitente().endereco().municipio().codigo()+",\r\n" + 
			"		nome : "+nota.emitente().endereco().municipio().nome()+",\r\n" + 
			"		uf : "+nota.emitente().endereco().municipio().uf()+"\r\n" + 
			"	},\r\n" + 
			"	municipioDescarregamento : {\r\n" + 
			"		codigo : "+nota.destinatario().endereco().municipio().codigo()+",\r\n" + 
			"		nome : "+nota.destinatario().endereco().municipio().nome()+",\r\n" + 
			"		uf : "+nota.destinatario().endereco().municipio().uf()+"\r\n" + 
			"	},\r\n" + 
			"	valor : "+nota.total()+",\r\n" + 
			"	pesoBruto : "+nota.transporte().volumes().stream().mapToDouble(s->s.pesoBruto()).sum() +",\r\n" + 
			"	placaVeiculo : "+nota.transporte().veiculo().placa()+",\r\n" + 
			"	condutor : {\r\n" + 
			"		nome : "+nota.transporte().transportador().razaoSocial()+",\r\n" + 
			"		cpf : 92103634853\r\n" + 
			"	}\r\n" + 
			"}";
		
		String responseBody = restTemplate.getForObject(
				buildUrl("notas_fiscais/%s",nota.notaFiscalId()), 
				String.class);
		
		System.out.println(responseBody);
		
		JSONAssert.assertEquals(JSON, responseBody, true);
	}

//	@Test(expected=HttpClientErrorException.class)
//	public void recursoNaoEncontado() throws JSONException{
//		restTemplate.getForEntity(buildUrl("empresas/1/veiculos?placa=INEXISTENTE"), String.class);
//	}
	
	
	private String buildUrl(String url,Object ... args){
		
		return String.format("http://localhost:"+port+"/"+url,args);
	}
	
}

