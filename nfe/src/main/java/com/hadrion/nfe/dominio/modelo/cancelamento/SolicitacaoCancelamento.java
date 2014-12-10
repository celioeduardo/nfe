package com.hadrion.nfe.dominio.modelo.cancelamento;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioRegistro;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;

public class SolicitacaoCancelamento {
	
	private SolicitacaoCancelamentoId solicitacaoCancelamentoId;
	private NotaFiscalId notaFiscalId;
	private Date dataHoraSolicitacao;
	private Ambiente ambiente;
	private Mensagem retorno;
	private Date dataHoraProcessamento;
	private NumeroProtocolo numeroProtocolo;
	
	public SolicitacaoCancelamento(
			SolicitacaoCancelamentoId solicitacaoCancelamentoId,
			Ambiente ambiente,
			NotaFiscalId notaFiscalId) {
		super();
		this.solicitacaoCancelamentoId = solicitacaoCancelamentoId;
		this.notaFiscalId = notaFiscalId;
		this.ambiente = ambiente;
		this.dataHoraSolicitacao = new Date();
	}

	public SolicitacaoCancelamentoId solicitacaoCancelamentoId(){
		return this.solicitacaoCancelamentoId;
	}
	
	public Date dataHoraSolicitacao(){
		return this.dataHoraSolicitacao;
	}
	public Date dataHoraProcessamento(){
		return this.dataHoraProcessamento;
	}
	
	public Mensagem retorno(){
		return this.retorno;
	}
	
	public NumeroProtocolo numeroProtocolo(){
		return this.numeroProtocolo;
	}
	
	public void sucesso(
			NumeroProtocolo numeroProtocolo,
			Mensagem mensagem, 
			Date dataHoraProcessamento){
		this.numeroProtocolo = numeroProtocolo;
		this.retorno = mensagem;
		this.dataHoraProcessamento = dataHoraProcessamento;
		
		DominioRegistro.eventoDominioPublicador()
			.publicar(
				new CancelamentoHomologado(notaFiscalId, ambiente));
	}
	
	public void fracasso(
			Mensagem mensagem, 
			Date dataHoraProcessamento){
		this.retorno = mensagem;
		this.dataHoraProcessamento = dataHoraProcessamento;
	}

}
