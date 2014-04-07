package com.hadrion.nfe.dominio.modelo.cancelamento;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.cancelamento.CancelamentoNfeService;
import com.hadrion.nfe.dominio.modelo.portal.cancelamento.RetornoCancelamento;

public class MockCancelarNotaServiceFabrica implements CancelarNotaServiceFabrica{
	
	private CancelamentoNfeService cancelamentoNfeService;
	
	public MockCancelarNotaServiceFabrica(
			CancelamentoNfeService cancelamentoNfeService) {
		super();
		this.cancelamentoNfeService = cancelamentoNfeService;
	}
	
	@Override
	public CancelarNotaService criarCancelarNotaService() {
		CancelarNotaService result = new CancelarNotaService();
		result.configurarCancelamentoNfeService(cancelamentoNfeService);
		return result;
	}
	
	public static MockCancelarNotaServiceFabrica cancelamentoHomologado(){
		return new MockCancelarNotaServiceFabrica(
			new CancelamentoNfeService() {
				@Override
				public RetornoCancelamento cancelar(SolicitacaoCancelamentoId solicitacao) {
					return new RetornoCancelamento(
							new NumeroProtocolo("CANC-1111"),
							new Mensagem(101, "Cancelamento Homologado"),
							new Date());
				}
			});
	}
	

}
