package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.LoteId;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;
import com.hadrion.nfe.dominio.modelo.lote.SituacaoLote;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;

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

	@Override
	public List<Lote> lotesPendentesDaNota(NotaFiscalId notaFiscalId) {
		List<Lote> result = new ArrayList<Lote>(); 
		
		List<SituacaoLote> situacoesPendentes = Arrays.asList(
				SituacaoLote.NAO_ENVIADO,
				SituacaoLote.PROCESSANDO); 
		
		List<Lote> lotesPendentes = repositorio
				.findBySituacaoIn(situacoesPendentes);

		for (Lote lote : lotesPendentes) 
			if (lote.temNota(notaFiscalId))
				result.add(lote);
		
		return result;
	}

	@Override
	public List<Lote> lotesEmProcessamento(Ambiente ambiente, FilialId filialId) {
		return repositorio.findBySituacaoAndFilialId(SituacaoLote.PROCESSANDO,filialId);
	}

	@Override
	public Lote obterLote(LoteId loteId) {
		return repositorio.findByLoteId(loteId);
	}

	@Override
	public List<Lote> lotesEmProcessamento(Ambiente ambiente,
			FilialId filialId, NotistaId notistaId) {
		return repositorio.findBySituacaoAndFilialIdAndNotistaId(SituacaoLote.PROCESSANDO,filialId,notistaId);
	}

} 
