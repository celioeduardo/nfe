package com.hadrion.nfe.dominio.modelo.inutillizacao;

import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.InutilizacaoPortalService;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.RetornoInutilizacao;

public class InutilizacaoService{
	
	private InutilizacaoPortalService inutilizacaoPortalService;
	
	public InutilizacaoService(InutilizacaoPortalService inutilizacaoPortalService){
		this.inutilizacaoPortalService = inutilizacaoPortalService;
	} 
	
	public void inutilizar(SolicitacaoInutilizacao solicitacao) {
		RetornoInutilizacao retorno = this.inutilizacaoPortalService.inutilizar(solicitacao);
		processarRetorno(solicitacao, retorno);
	}

	private void processarRetorno(SolicitacaoInutilizacao solicitacao,RetornoInutilizacao retorno) {
		if (retorno.inutilizacaoHomologada())
			solicitacao.sucesso(
					retorno.numeroProtocolo(), 
					retorno.mensagem(), 
					retorno.dataHoraProcessamento());
		else
			solicitacao.fracasso(
					retorno.mensagem(), 
					retorno.dataHoraProcessamento());
	}


	/*private void assertNotaFiscalNaoNula(NotaFiscal notaFiscal){
		if (notaFiscal == null)
			throw new IllegalArgumentException("Nota Fiscal não pode ser nula.");
	}*/
}
