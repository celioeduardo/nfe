package com.hadrion.nfe.port.adapters.agrix.repositorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.comum.paginacao.Pagina;
import com.hadrion.comum.paginacao.Paginacao;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.filial.FilialRepositorio;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.Situacao;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.port.adapters.agrix.AgrixUtil;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.NotaFiscalRepositorioSpringData;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.PageAdapter;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.PaginacaoAdapter;

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

	@Autowired
	private FilialRepositorio filialRepositorio;
	
	@Autowired
	private EntityManager em;
	
	@Override
	public List<DescritorNotaFiscal> notasPendentesAutorizacaoResumo(
			Ambiente ambiente,
			Long empresa, FilialId filialId, Date inicio, Date fim,
			String notistaId, NotaFiscalId notaFiscalId) {
		
		Filial filial = filialRepositorio.obterFilial(filialId);
		
		List<DescritorNotaFiscal> descritores = agrixService
				.notasPendentesAutorizacaoResumo(ambiente,empresa, 
						filialId.id(),inicio, fim, notistaId, 
						notaFiscalId,filial.modoOperacao());
		
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
		System.out.println("sincoronizando");
		sincronizarService.sincronizar(notas,ambiente);
		System.out.println("findby notas por ambiente");
		return repositorio.findByNotaFiscalIdInAndAmbiente(notas,ambiente);
	}
	@Override
	public NotaFiscal notaPendenteAutorizacao(NotaFiscalId notaFiscalId) {
		Ambiente ambiente = AgrixUtil.ambientePelaNotaFiscalId(notaFiscalId);
		sincronizarService.sincronizar(Collections.singletonList(notaFiscalId),ambiente);
		em.clear();
		List<NotaFiscal> result = 
				repositorio.findByNotaFiscalIdInAndAmbiente(
						Collections.singletonList(notaFiscalId),ambiente);
		return result.size() > 0 ? result.get(0) : null;
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
	public Pagina<NotaFiscal> notasAutorizadas(FilialId filialId,Ambiente ambiente,Paginacao paginacao) {
		return new PageAdapter<NotaFiscal>(repositorio.findByFilialIdAndSituacaoAndAmbiente(
				filialId, Situacao.AUTORIZADA,ambiente, 
				new PaginacaoAdapter(paginacao,new Sort(Direction.DESC, "numero"))));
	}

	@Override
	public Pagina<NotaFiscal> notasAutorizadas(
			FilialId filialId,Ambiente ambiente,Long numero,Paginacao paginacao) {
		return new PageAdapter<NotaFiscal>(
				repositorio.findByFilialIdAndSituacaoAndAmbienteAndNumero(
					filialId, Situacao.AUTORIZADA,ambiente,numero,
					new PaginacaoAdapter(paginacao,new Sort(Direction.DESC, "numero"))));
	}

	@Override
	public Pagina<NotaFiscal> notasAutorizadas(FilialId filialId,
			Ambiente ambiente, NotistaId notistaId,Paginacao paginacao) {
		return new PageAdapter<NotaFiscal>(
				repositorio.findByFilialIdAndNotistaIdAndSituacaoAndAmbiente(filialId, notistaId, 
						Situacao.AUTORIZADA,ambiente,
						new PaginacaoAdapter(paginacao,new Sort(Direction.DESC, "numero"))));
	}

	@Override
	public Pagina<NotaFiscal> notasAutorizadasNaoImpressas(
			FilialId filialId,Ambiente ambiente, Paginacao paginacao) {
		return new PageAdapter<NotaFiscal>(
				repositorio.findByFilialIdAndSituacaoAndAmbienteAndDanfeImpresso(
				filialId, Situacao.AUTORIZADA,ambiente,false,
				new PaginacaoAdapter(paginacao,new Sort(Direction.DESC, "numero"))));
	}

	@Override
	public Pagina<NotaFiscal> notasAutorizadasNaoImpressas(
			FilialId filialId,Ambiente ambiente,Long numero, Paginacao paginacao) {
		return new PageAdapter<NotaFiscal>(
				repositorio.findByFilialIdAndSituacaoAndAmbienteAndNumeroAndDanfeImpresso(
				filialId, Situacao.AUTORIZADA,ambiente,numero,false,
				new PaginacaoAdapter(paginacao,new Sort(Direction.DESC, "numero"))));
	}

	@Override
	public Pagina<NotaFiscal> notasAutorizadasNaoImpressas(FilialId filialId,
			Ambiente ambiente, NotistaId notistaId, Paginacao paginacao) {
		return new PageAdapter<NotaFiscal>(
				repositorio.findByFilialIdAndNotistaIdAndSituacaoAndAmbienteAndDanfeImpresso(
				filialId, notistaId, Situacao.AUTORIZADA,ambiente,false,
				new PaginacaoAdapter(paginacao,new Sort(Direction.DESC, "numero"))));
	}

	@Override
	public Pagina<NotaFiscal> notasCanceladas(FilialId filialId,
			Ambiente ambiente, NotistaId notistaId, Paginacao paginacao) {
		return new PageAdapter<NotaFiscal>(
				repositorio.findByFilialIdAndNotistaIdAndSituacaoAndAmbiente(filialId, notistaId, 
				Situacao.CANCELADA,ambiente,
				new PaginacaoAdapter(paginacao,new Sort(Direction.DESC, "numero"))));
	}

	@Override
	public Pagina<NotaFiscal> notasCanceladas(FilialId filialId,
			Ambiente ambiente, Paginacao paginacao) {
		return new PageAdapter<NotaFiscal>(
			repositorio.findByFilialIdAndSituacaoAndAmbiente(
				filialId,  
				Situacao.CANCELADA,
				ambiente,
				new PaginacaoAdapter(paginacao,new Sort(Direction.DESC, "numero"))));
	}

	@Override
	public List<NotaFiscal> notasAutorizadas(List<NotaFiscalId> ids) {
		return repositorio.findByNotaFiscalIdInAndSituacao(ids, Situacao.AUTORIZADA);
	}
	@Override
	public Optional<NotaFiscal> notaAutorizada(NotaFiscalId id) {
		return repositorio.findByNotaFiscalIdAndSituacao(id, Situacao.AUTORIZADA);
	}
	
}
