package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Situacao;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;

public interface NotaFiscalRepositorioSpringData extends JpaRepository<NotaFiscal, Long>{

	NotaFiscal findByNotaFiscalId(NotaFiscalId notaFiscalId);

	NotaFiscal findByChaveAcessoAndAmbiente(ChaveAcesso chave, Ambiente ambiente);

	List<NotaFiscal> findByNotaFiscalIdInAndAmbiente(List<NotaFiscalId> notas, Ambiente ambiente);
	List<NotaFiscal> findByNotaFiscalIdIn(List<NotaFiscalId> notas);

	List<NotaFiscal> findBySituacaoAndAmbiente(Situacao autorizada,
			Ambiente ambiente);

	List<NotaFiscal> findBySituacaoAndAmbienteAndDanfeImpresso(
			Situacao autorizada, Ambiente ambiente, boolean danfeImpresso);

}
