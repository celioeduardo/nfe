package com.hadrion.comum.dominio.modelo;

import org.springframework.context.ApplicationEvent;

public class EventoDominioSpring extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private EventoDominio eventoDominio;
	
	public EventoDominioSpring(EventoDominio eventoDominio, Object source) {
		super(source);
		this.eventoDominio = eventoDominio;
	}
	
	public EventoDominio eventoDominio(){
		return eventoDominio;
	}

}
