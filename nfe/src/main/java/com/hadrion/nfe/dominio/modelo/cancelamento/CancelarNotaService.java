package com.hadrion.nfe.dominio.modelo.cancelamento;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.cancelamento.CancelamentoNfeService;
import com.hadrion.nfe.dominio.modelo.portal.cancelamento.RetornoCancelamento;

public class CancelarNotaService {
	
	private CancelamentoNfeService cancelamentoNfeService;
	private SolicitacaoCancelamentoRepositorio solicitacaoCancelamentoRepositorio;
	private NotaFiscalRepositorio notafiscalRepositorio;
	
	public CancelarNotaService(
			CancelamentoNfeService cancelamentoNfeService,
			SolicitacaoCancelamentoRepositorio solicitacaoCancelamentoRepositorio,
			NotaFiscalRepositorio notaFiscalRepositorio){
		this.cancelamentoNfeService = cancelamentoNfeService;
		this.solicitacaoCancelamentoRepositorio = solicitacaoCancelamentoRepositorio;
		this.notafiscalRepositorio = notaFiscalRepositorio;
	}  
	
	public void cancelarEmHomologacao(NotaFiscalId notaFiscalId) {
		
		if (!notaFiscalNaoNula(notaFiscalId).
				estaAutorizadaEmHomologacao())
			throw new IllegalArgumentException(
					"Somente Nota Fiscal AUTORIZADA pode ser Cancelada.");
		
		SolicitacaoCancelamento solicitacao = new SolicitacaoCancelamento(
				solicitacaoCancelamentoRepositorio.proximaIdentidade(), 
				Ambiente.HOMOLOGACAO, 
				notaFiscalId);
		
		RetornoCancelamento retorno = this.cancelamentoNfeService
				.cancelar(solicitacao.solicitacaoCancelamentoId());
		
		processarRetorno(solicitacao, retorno);
	}

	public void cancelarEmProducao(NotaFiscalId notaFiscalId) {
		
		if (!notaFiscalNaoNula(notaFiscalId).
				estaAutorizadaEmProducao())
			throw new IllegalArgumentException(
					"Somente Nota Fiscal AUTORIZADA pode ser Cancelada.");
		
		SolicitacaoCancelamento solicitacao = new SolicitacaoCancelamento(
				solicitacaoCancelamentoRepositorio.proximaIdentidade(), 
				Ambiente.PRODUCAO, 
				notaFiscalId);
		
		RetornoCancelamento retorno = this.cancelamentoNfeService
				.cancelar(solicitacao.solicitacaoCancelamentoId());
		
		processarRetorno(solicitacao, retorno);
	}

	private void processarRetorno(SolicitacaoCancelamento solicitacao,
			RetornoCancelamento retorno) {
		if (retorno.cancelamentoHomologado())
			solicitacao.sucesso(
					retorno.numeroProtocolo(), 
					retorno.mensagem(), 
					retorno.dataHoraProcessamento());
		else
			solicitacao.fracasso(
					retorno.mensagem(), 
					retorno.dataHoraProcessamento());
	}
	
	private NotaFiscal notaFiscalNaoNula(NotaFiscalId notaFiscalId){
		NotaFiscal nf = notafiscalRepositorio.notaFiscalPeloId(notaFiscalId);
		if (nf == null)
			throw new IllegalArgumentException("Nota Fiscal inexistente.");
		return nf; 
	}
	
}
