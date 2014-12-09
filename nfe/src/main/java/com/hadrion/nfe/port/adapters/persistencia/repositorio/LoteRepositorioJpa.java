package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.LoteId;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

@Repository("loteRepositorio")
@Transactional
public class LoteRepositorioJpa implements LoteRepositorio {

	@Autowired
	private LoteRepositorioSpringData repositorio;
	
	@Override
	public LoteId proximaIdentidade() {
		return new LoteId(UUID.randomUUID().toString().toUpperCase());
	}

	@Override
	public void salvar(Lote lote) {
		repositorio.save(lote);
	}
	
	//TODO melhorar algoritimo
	@Override
	public Set<Lote> lotesDaNota(NotaFiscalId notaFiscalId) {
		Set<Lote> result = new HashSet<Lote>();

//		for (Lote lote : repositorio.findLotesDaNotaFiscal(notaFiscalId)) {
//			result.add(lote);
//		}
		
		for (Lote lote : repositorio.findAll()) {
			if (lote.temNota(notaFiscalId))
				result.add(lote);
				
		}
		return result;
	}

	@Override
	public void limpar() {
	}

} 
