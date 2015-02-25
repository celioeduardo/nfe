package com.hadrion.nfe.port.adapters.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hadrion.nfe.dominio.modelo.hospede.Hospede;
import com.hadrion.nfe.dominio.modelo.notista.Notista;

@Service
public class HttpUsuarioNoPapelAdaptador implements UsuarioNoPapelAdaptador {
	
	@Autowired
	private NotistaTradutor tradutor;
	
	@Value("${idhadrion.url}")
	private String idHadrionUrl;
	
	@Override
	public Notista paraNotista(Hospede hospede, String identificacao) {
		RestTemplate restTemplate = new RestTemplate();
		
		String papel = "Notista";
		
		String url = idHadrionUrl() + "hospedes/{hospedeId}/usuarios/{username}/noPapel/{papel}";
		
		ResponseEntity<String> resposta = 
				restTemplate.getForEntity(url, String.class, hospede.id(), identificacao, papel);
		
		if (resposta.getStatusCode() == HttpStatus.OK)
			return tradutor.paraNotistaPelaRepresentacao(resposta.getBody());
		else
			return null; 
	}
	
	
	private String idHadrionUrl(){
		return  idHadrionUrl.endsWith("/") ? 
				idHadrionUrl:
				idHadrionUrl + "/"; 
	}

}
