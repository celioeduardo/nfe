package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;

@Repository
@Transactional
public class NotaFiscalRepositorioJpa implements NotaFiscalRepositorio{

	@Autowired
	private NotaFiscalRepositorioSpringData repositorio;
	
	@Override
	public List<DescritorNotaFiscal> notasPendentesAutorizacaoResumo(
			Double empresa, Double filial, Date inicio, Date fim,
			String usuario, NotaFiscalId notaFiscalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NotaFiscal> notasPendentesAutorizacao(List<NotaFiscalId> notas) {
		return repositorio.findByNotaFiscalIdIn(notas);
	}

	@Override
	public NotaFiscal notaFiscalPeloId(NotaFiscalId notaFiscalId) {
		return repositorio.findByNotaFiscalId(notaFiscalId);
	}

	@Override
	public String queryToJson(String query) {
		// TODO Auto-generated method stub
		return null;
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
