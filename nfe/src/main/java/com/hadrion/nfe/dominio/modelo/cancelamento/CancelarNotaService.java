package com.hadrion.nfe.dominio.modelo.cancelamento;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.portal.cancelamento.CancelamentoNfeService;
import com.hadrion.nfe.dominio.modelo.portal.cancelamento.RetornoCancelamento;

public class CancelarNotaService {
	
	private CancelamentoNfeService cancelamentoNfeService;
	private SolicitacaoCancelamentoRepositorio solicitacaoCancelamentoRepositorio;
	
	public CancelarNotaService(
			CancelamentoNfeService cancelamentoNfeService,
			SolicitacaoCancelamentoRepositorio solicitacaoCancelamentoRepositorio){
		this.cancelamentoNfeService = cancelamentoNfeService;
		this.solicitacaoCancelamentoRepositorio = solicitacaoCancelamentoRepositorio;
	}  
	
	public void cancelarEmHomologacao(NotaFiscalId notaFiscalId) {
		
		SolicitacaoCancelamento solicitacao = new SolicitacaoCancelamento(
				solicitacaoCancelamentoRepositorio.proximaIdentidade(), 
				Ambiente.HOMOLOGACAO, 
				notaFiscalId);
		
		RetornoCancelamento retorno = this.cancelamentoNfeService
				.cancelar(solicitacao.solicitacaoCancelamentoId());
		
		processarRetorno(solicitacao, retorno);
	}

	public void cancelarEmProducao(NotaFiscalId notaFiscalId) {
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

}
