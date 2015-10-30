package com.hadrion.nfe.port.adapters.agrix.evento;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.DominioTest;

public class EventoAgrixServiceTest extends DominioTest{

	@Autowired
	private ConsumidorEventoAgrix consumidorEvento;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test @Ignore
	public void consumirEventos(){
		consumidorEvento.consumirEventos();
	}
	
}
