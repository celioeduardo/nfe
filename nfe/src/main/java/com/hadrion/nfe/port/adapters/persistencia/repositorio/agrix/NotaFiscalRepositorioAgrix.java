package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.NotaFiscalRepositorioSpringData;

@Repository
@Transactional
//@Profile("agrix")
public class NotaFiscalRepositorioAgrix implements NotaFiscalRepositorio{

	@Autowired
	private NotaFiscalRepositorioSpringData repositorio;
	
	@Autowired
	private AgrixService agrixService;
	
	@Autowired
	private SincronizarService sincronizarService;
	
	@Autowired
	private LoteRepositorio loteRepositorio;
	
	@Override
	public List<DescritorNotaFiscal> notasPendentesAutorizacaoResumo(
			Ambiente ambiente,
			Double empresa, Double filial, Date inicio, Date fim,
			String usuario, NotaFiscalId notaFiscalId) {
		
		List<DescritorNotaFiscal> descritores = agrixService
				.notasPendentesAutorizacaoResumo(ambiente,empresa, filial, inicio, fim, usuario, notaFiscalId);
		
		return filtrarDescritoresPendentes(descritores);
	}
	
	private List<DescritorNotaFiscal> filtrarDescritoresPendentes(List<DescritorNotaFiscal> descritores){
		List<DescritorNotaFiscal> result = new ArrayList<DescritorNotaFiscal>(descritores.size());
		
		Map<NotaFiscalId, NotaFiscal> notas = obterNotas(descritoresParaIds(descritores));
		
		for (DescritorNotaFiscal descritor : descritores) {
			if (!notas.containsKey(descritor.notaFiscalId()))
				result.add(descritor);
			else {
				NotaFiscal nf = notas.get(descritor.notaFiscalId());
				if (nf.pendenteDeTransmissao() && !notaNaoEstaEmLotePendente(nf)){
					result.add(descritor);
					descritor.atualizarMensagem(nf.mensagem());
				}
			}
		}
		
		return result;
	}
	
	private boolean notaNaoEstaEmLotePendente(NotaFiscal nf){
		return loteRepositorio.lotesPendentesDaNota(nf.notaFiscalId()).size() > 0;
	}
	
	private Map<NotaFiscalId, NotaFiscal> obterNotas(List<NotaFiscalId> ids){
		Map<NotaFiscalId, NotaFiscal> result = new HashMap<NotaFiscalId, NotaFiscal>(ids.size());
		
		List<NotaFiscal> notas = repositorio.findByNotaFiscalIdIn(ids);
		
		for (NotaFiscal nf : notas) 
			result.put(nf.notaFiscalId(), nf);
		
		return result;
	}
	
	private List<NotaFiscalId> descritoresParaIds(List<DescritorNotaFiscal> descritores){
		List<NotaFiscalId> result = new ArrayList<NotaFiscalId>(descritores.size());
		for (DescritorNotaFiscal descritor : descritores) 
			result.add(descritor.notaFiscalId());
		return result;
	}
	

	@Override
	public List<NotaFiscal> notasPendentesAutorizacao(List<NotaFiscalId> notas,Ambiente ambiente) {
		sincronizarService.sincronizar(notas,ambiente);
		return repositorio.findByNotaFiscalIdInAndAmbiente(notas,ambiente);
	}

	@Override
	public NotaFiscal notaFiscalPeloId(NotaFiscalId notaFiscalId) {
		return repositorio.findByNotaFiscalId(notaFiscalId);
	}

	@Override
	public void limpar() {
	}

	@Override
	public void salvar(NotaFiscal notaFiscal) {
		repositorio.save(notaFiscal);
	}

	@Override
	public NotaFiscal notaFiscalPelaChave(ChaveAcesso chave, Ambiente ambiente) {
		return repositorio.findByChaveAcessoAndAmbiente(chave,ambiente);
	}
	
	
	
}
