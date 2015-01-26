package com.hadrion.nfe.dominio.modelo.cancelamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.cancelamento.CancelamentoService;
import com.hadrion.nfe.dominio.modelo.portal.cancelamento.RetornoCancelamento;

@Service
public class CancelarNotaService{
	
	//@Autowired
	private CancelamentoService cancelamentoService;
	
	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio;
	
	CancelarNotaService(){}
	
	void configurarCancelamentoNfeService(CancelamentoService cancelamentoNfeService){
		this.cancelamentoService = cancelamentoNfeService;
	}

	public void cancelar(SolicitacaoCancelamento solicitacao) {
		
		NotaFiscal nf = notaFiscalRepositorio.notaFiscalPeloId(solicitacao.notaFiscalId());
		
		if (!nf.estaAutorizada())
			throw new IllegalArgumentException(
					"Somente Nota Fiscal AUTORIZADA pode ser Cancelada.");
		
		RetornoCancelamento retorno = this.cancelamentoService
				.cancelar(solicitacao);
		
		processarRetorno(nf, solicitacao, retorno);
		
		this.notaFiscalRepositorio.salvar(nf);
	}

	private void processarRetorno(NotaFiscal nf,
			SolicitacaoCancelamento solicitacao,
			RetornoCancelamento retorno) {
		if (retorno.cancelamentoHomologado())
			nf.cancelar(retorno.numeroProtocolo(),retorno.mensagem(),retorno.dataHoraProcessamento());
		else
			throw new RuntimeException(retorno.mensagem().codigo()+"-"+retorno.mensagem().descricao());
	}
	
}
