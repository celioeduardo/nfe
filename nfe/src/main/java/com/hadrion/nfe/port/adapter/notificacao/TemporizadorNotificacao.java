package com.hadrion.nfe.port.adapter.notificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hadrion.comum.notificacao.PublicadorNotificacao;

@Component
@Profile({"dev", "prod"})
public class TemporizadorNotificacao {
	
	@Autowired
	private PublicadorNotificacao publicadorNotificacao;
	
	@Scheduled(fixedRate=100)
	public void executar(){
		publicadorNotificacao.publicarNotificacoes();
	}
	
}
