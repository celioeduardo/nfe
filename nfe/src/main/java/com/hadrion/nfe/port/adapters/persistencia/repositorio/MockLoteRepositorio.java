package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.LoteId;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

@Repository("loteRepositorio")
@Profile("teste")
public class MockLoteRepositorio implements LoteRepositorio {

	private Map<String,Lote> store=new HashMap<String, Lote>();
	
	@Override
	public LoteId proximaIdentidade() {
		return new LoteId(UUID.randomUUID().toString().toUpperCase());
	}

	@Override
	public void salvar(Lote lote) {
		store.put(lote.loteId().id(), lote);
	}

	@Override
	public Set<Lote> lotesDaNota(NotaFiscalId notaFiscalId) {
		Set<Lote> result = new HashSet<Lote>();
		for (Lote lote : store.values()) {
			if (lote.temNota(notaFiscalId))
				result.add(lote);
				
		}
		return result;
	}

	@Override
	public void limpar() {
		store.clear();
	}

} 
