package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.UUID;

import com.hadrion.nfe.dominio.modelo.lote.LoteId;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;

public class MockLoteRepositorio implements LoteRepositorio {

	@Override
	public LoteId proximaIdentidade() {
		return new LoteId(UUID.randomUUID().toString().toUpperCase());
	}

	//private Map<String,Lote> store=new HashMap<String, Lote>();
	
	
	
	
} 
