package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

@Profile("teste")
@Repository("notaFiscalRepositorio")
public class MockNotaFiscalRepositorio implements NotaFiscalRepositorio {

	private Map<String,NotaFiscal> store=new HashMap<String, NotaFiscal>();
	
	@Override
	public List<NotaFiscal> pendentesTransmissao() {		
		return null;
	}
	
	public void salvar(NotaFiscal notaFiscal){
		store.put(notaFiscal.notaFiscalId().id(), notaFiscal);
	}

	public NotaFiscal notaFiscalPeloId(NotaFiscalId notaFiscalId){
		return store.get(notaFiscalId.id());
	}
} 
