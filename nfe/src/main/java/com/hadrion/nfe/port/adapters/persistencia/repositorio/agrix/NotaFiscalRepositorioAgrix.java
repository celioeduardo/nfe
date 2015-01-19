package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.Situacao;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;
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
			Double empresa, FilialId filial, Date inicio, Date fim,
			String notistaId, NotaFiscalId notaFiscalId) {
		
		List<DescritorNotaFiscal> descritores = agrixService
				.notasPendentesAutorizacaoResumo(ambiente,empresa, 
						filial != null ? String.valueOf(filial) : null, 
						inicio, fim, notistaId, notaFiscalId);
		
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
				if (nf.pendenteDeTransmissao() && notaNaoEstaEmLotePendente(nf)){
					result.add(descritor);
					descritor.atualizarMensagem(nf.mensagem());
					descritor.setChave(nf.chaveAcesso());
					descritor.setTipoEmissao(nf.tipoEmissao());
				}
			}
		}
		
		return result;
	}
	
	private boolean notaNaoEstaEmLotePendente(NotaFiscal nf){
		return loteRepositorio.lotesPendentesDaNota(nf.notaFiscalId()).size() == 0;
	}
	
	private Map<NotaFiscalId, NotaFiscal> obterNotas(List<NotaFiscalId> ids){
		if (ids == null || ids.size() == 0)
			return Collections.emptyMap();
		
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
	public List<NotaFiscal> notasPendentesAutorizacao(FilialId filialId,
			Ambiente ambiente) {
		List<Situacao> situacoes = new ArrayList<Situacao>();
		situacoes.add(Situacao.INDEFINIDA);
		situacoes.add(Situacao.EMITIDA);
		return repositorio.findByFilialIdInAndAmbienteAndSituacaoIn(filialId,ambiente,situacoes);
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

	@Override
	public List<NotaFiscal> notasAutorizadas(FilialId filialId,Ambiente ambiente) {
		return repositorio.findByFilialIdAndSituacaoAndAmbiente(filialId, Situacao.AUTORIZADA,ambiente);
	}

	@Override
	public List<NotaFiscal> notasAutorizadas(FilialId filialId,
			Ambiente ambiente, NotistaId notistaId) {
		return repositorio.findByFilialIdAndNotistaIdAndSituacaoAndAmbiente(filialId, notistaId, 
				Situacao.AUTORIZADA,ambiente);
	}

	@Override
	public List<NotaFiscal> notasAutorizadasNaoImpressas(FilialId filialId,Ambiente ambiente) {
		return repositorio.findByFilialIdAndSituacaoAndAmbienteAndDanfeImpresso(
				filialId, Situacao.AUTORIZADA,ambiente,false);
	}

	@Override
	public List<NotaFiscal> notasAutorizadasNaoImpressas(FilialId filialId,
			Ambiente ambiente, NotistaId notistaId) {
		return repositorio.findByFilialIdAndNotistaIdAndSituacaoAndAmbienteAndDanfeImpresso(
				filialId, notistaId, Situacao.AUTORIZADA,ambiente,false);
	}
	
	
	
}
