package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;

@Repository
@Transactional
@Profile("x")
public class NotaFiscalRepositorioJpa implements NotaFiscalRepositorio{

	@Autowired
	private NotaFiscalRepositorioSpringData repositorio;
	
	@Override
	public List<DescritorNotaFiscal> notasPendentesAutorizacaoResumo(
			Ambiente ambiente,
			Double empresa, FilialId filial, Date inicio, Date fim,
			String usuario, NotaFiscalId notaFiscalId) {
		return null;
	}

	@Override
	public List<NotaFiscal> notasPendentesAutorizacao(List<NotaFiscalId> notas, Ambiente ambiente) {
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

	@Override
	public List<NotaFiscal> notasAutorizadas(FilialId filialId,Ambiente ambiente) {
		return null;
	}

	@Override
	public List<NotaFiscal> notasAutorizadasNaoImpressas(FilialId filialId,Ambiente ambiente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NotaFiscal> notasPendentesAutorizacao(FilialId filialId,
			Ambiente ambiente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NotaFiscal> notasAutorizadasNaoImpressas(FilialId filialId,
			Ambiente ambiente, NotistaId notistaId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NotaFiscal> notasAutorizadas(FilialId filialId,
			Ambiente ambiente, NotistaId notistaId) {
		// TODO Auto-generated method stub
		return null;
	}

}
