package com.hadrion.nfe.port.adapters.agrix.evento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile({"!test"})
public class TemporizadorEventoAgrix {
	
	@Autowired
	private ConsumidorEventoAgrix consumidorEventoAgrix;
	
	@Scheduled(fixedRate=5000)
	public void executar(){
		consumidorEventoAgrix.consumirEventos();
	}
	
}
