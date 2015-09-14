package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Situacao;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;

public interface NotaFiscalRepositorioSpringData extends JpaRepository<NotaFiscal, Long>{

	NotaFiscal findByNotaFiscalId(NotaFiscalId notaFiscalId);

	NotaFiscal findByChaveAcessoAndAmbiente(ChaveAcesso chave, Ambiente ambiente);

	List<NotaFiscal> findByNotaFiscalIdInAndAmbiente(List<NotaFiscalId> notas, Ambiente ambiente);
	List<NotaFiscal> findByNotaFiscalIdIn(List<NotaFiscalId> notas);

	Page<NotaFiscal> findByFilialIdAndSituacaoAndAmbiente(FilialId filialId, Situacao autorizada,
			Ambiente ambiente, Pageable pageable);
	
	Page<NotaFiscal> findByFilialIdAndNotistaIdAndSituacaoAndAmbiente(
			FilialId filialId, NotistaId notistaId, Situacao autorizada,
			Ambiente ambiente, Pageable pageable);
	
	Page<NotaFiscal> findByFilialIdAndSituacaoAndAmbienteAndDanfeImpresso(FilialId filialId,
			Situacao autorizada, Ambiente ambiente, boolean danfeImpresso, Pageable pageable);

	List<NotaFiscal> findByFilialIdInAndAmbienteAndSituacaoIn(FilialId filialId,
			Ambiente ambiente, List<Situacao> situacoes);

	Page<NotaFiscal> findByFilialIdAndNotistaIdAndSituacaoAndAmbienteAndDanfeImpresso(
			FilialId filialId, NotistaId notistaId, Situacao autorizada,
			Ambiente ambiente, boolean danfeImpresso, Pageable pageable);

	Page<NotaFiscal> findByFilialIdAndSituacaoAndAmbienteAndNumeroAndDanfeImpresso(
			FilialId filialId, Situacao autorizada, Ambiente ambiente, Long numero,
			boolean danfeImpresso, Pageable pageable);

	Page<NotaFiscal> findByFilialIdAndSituacaoAndAmbienteAndNumero(
			FilialId filialId, Situacao autorizada, Ambiente ambiente,
			Long numero, Pageable pageable);

	List<NotaFiscal> findByNotaFiscalIdInAndSituacao(List<NotaFiscalId> ids, 
			Situacao situacao);

	Optional<NotaFiscal> findByNotaFiscalIdAndSituacao(NotaFiscalId id,
			Situacao autorizada);

	List<NotaFiscal> findByAmbienteAndFilialId(Ambiente ambiente, 
			FilialId filial);
	
}
