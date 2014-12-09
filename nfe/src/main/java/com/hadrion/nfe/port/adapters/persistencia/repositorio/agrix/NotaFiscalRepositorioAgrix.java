package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.nfe.dominio.modelo.Ambiente;
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
	
	@Override
	public List<DescritorNotaFiscal> notasPendentesAutorizacaoResumo(
			Double empresa, Double filial, Date inicio, Date fim,
			String usuario, NotaFiscalId notaFiscalId) {
		return agrixService.notasPendentesAutorizacaoResumo(empresa, filial, inicio, fim, usuario, notaFiscalId);
	}

	@Override
	public List<NotaFiscal> notasPendentesAutorizacao(List<NotaFiscalId> notas,Ambiente ambiente) {
		sincronizar(notas,ambiente);
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
	
	private void sincronizar(List<NotaFiscalId> notas, Ambiente ambiente) {
		List<NotaFiscal> notasAgrix = agrixService.obterNotas(notas,ambiente);
		for (NotaFiscal nfAgrix : notasAgrix) {
			mesclar(nfAgrix);
		}
	}
	
	private void mesclar(NotaFiscal nfAgrix){
		NotaFiscal nfLocal = repositorio.findByNotaFiscalId(nfAgrix.notaFiscalId());
		if (nfLocal != null){
			if (nfLocal.pendenteDeTransmissao())
				nfLocal.mesclar(nfAgrix);
		}
		else
			nfLocal = nfAgrix;
		repositorio.save(nfLocal);
	}
	
}
