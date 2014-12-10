package com.hadrion.nfe.aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.hadrion.comum.dominio.modelo.EventoDominio;
import com.hadrion.comum.dominio.modelo.EventoDominioSpring;
import com.hadrion.comum.evento.EventStore;

@Service
public class ProcessadorEventoNfe implements ApplicationListener<EventoDominioSpring>{
	
	@Autowired
	private EventStore eventStore;
		
    EventStore eventStore(){
    	return this.eventStore;
    }

	@Override
	public void onApplicationEvent(EventoDominioSpring event) {
		store(event.eventoDominio());
	}
	private void store(EventoDominio aDomainEvent) {
        this.eventStore().append(aDomainEvent);
    }
	
}
