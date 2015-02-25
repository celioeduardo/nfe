package com.hadrion.nfe.port.adapters.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.comum.media.RepresentationReader;
import com.hadrion.nfe.dominio.modelo.hospede.Hospede;
import com.hadrion.nfe.dominio.modelo.notista.Notista;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.NotistaRepositorioSpringData;

@Service
class NotistaTradutor {
	
	@Autowired
	private NotistaRepositorioSpringData repositorio;
	
	Notista paraNotistaPelaRepresentacao(
			String representacaoUsuarioNoPapel){
		
		RepresentationReader reader = 
				new RepresentationReader(representacaoUsuarioNoPapel);
		
		String username = reader.stringValue("username");	
		String hospede = reader.stringValue("hospedeId");
		
		Notista notista = repositorio.findByHospedeAndIdentificacao(
				new Hospede(hospede),
				username);
		
		return notista;
		
	}
	
}
