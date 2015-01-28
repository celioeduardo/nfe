package com.hadrion.nfe.port.adapters.portal.evento;

import java.util.Arrays;
import java.util.List;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;

public class LoteEvento {
	private Long id;
	private Evento[] eventos;
	
	public LoteEvento(Long id, Evento ... eventos) {
		this.id = id;
		this.eventos = eventos;
	}
	
	public Long id(){
		return id;
	}
	
	public List<Evento> eventos() {
		return Arrays.asList(eventos);
	}

	public Ambiente ambiente() {
		return eventos[0].ambiente();
	}

	public Uf uf() {
		return eventos[0].uf();
	}

}
