package com.hadrion.nfe.port.adapters.servico.autenticacao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.hadrion.nfe.aplicacao.AutenticacaoAplicacaoService;

@Service
public class IdHadrionAutenticacaoService implements AutenticacaoAplicacaoService{
	
	@Value("${idhadrion.url}")
	private String idHadrionUrl;
	
	@Override
	public boolean autenticar(String hospedeId, String username, String senha) {
		RestTemplate restTemplate = new RestTemplate();
		
		String url = idHadrionUrl() + "hospedes/{hospedeId}/usuarios/{username}/autenticadoPor/{senha}";
		
		ResponseEntity<String> resposta = null;
		try {
			resposta = restTemplate.getForEntity(url, String.class, hospedeId, username, senha);
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return false;
			else throw e;
		}
		
		if (resposta.getStatusCode() == HttpStatus.OK)
			return true;
		else 
			return false;
	}
	
	private String idHadrionUrl(){
		return  idHadrionUrl.endsWith("/") ? 
				idHadrionUrl:
				idHadrionUrl + "/"; 
	}

}
