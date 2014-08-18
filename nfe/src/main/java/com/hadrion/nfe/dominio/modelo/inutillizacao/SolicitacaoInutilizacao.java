package com.hadrion.nfe.dominio.modelo.inutillizacao;

import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominioPublicador;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;

public class SolicitacaoInutilizacao {
	
	private Ambiente ambiente;
	private NumeroProtocolo numeroProtocolo;
	private Mensagem retorno;
	private Date dataHoraProcessamento;
	private Date dataHoraSolicitacao;
	private SolicitacaoInutilizacaoId solicitacaoInutilizacaoId;
	private Faixa faixa;

	public SolicitacaoInutilizacao(
			SolicitacaoInutilizacaoId solicitacaoInutilizacaoId,
			Ambiente ambiente,
			Faixa faixa) {
		super();
		this.solicitacaoInutilizacaoId = solicitacaoInutilizacaoId;
		this.ambiente = ambiente;
		this.dataHoraSolicitacao = new Date(); 
		this.faixa = faixa;
	}
	
	public SolicitacaoInutilizacaoId solicitacaoInutilizacaoId(){
		return this.solicitacaoInutilizacaoId;
	}
	
	public Faixa faixa(){
		return faixa;
	}

	public Ambiente ambiente(){
		return this.ambiente;
	}

	public void sucesso(
			NumeroProtocolo numeroProtocolo,
			Mensagem mensagem, 
			Date dataHoraProcessamento){
		this.numeroProtocolo = numeroProtocolo;
		this.retorno = mensagem;
		this.dataHoraProcessamento = dataHoraProcessamento;
		EventoDominioPublicador.instancia().publicar(
				new InutilizacaoHomologada(this.ambiente()));
	}
	
	public void fracasso(
			Mensagem mensagem, 
			Date dataHoraProcessamento){
		this.retorno = mensagem;
		this.dataHoraProcessamento = dataHoraProcessamento;
	}
	
	public Date dataHoraSolicitacao(){
		return dataHoraSolicitacao;
	}
	public Date dataHoraProcessamento(){
		return dataHoraProcessamento;
	}
	
	public Mensagem retorno(){
		return this.retorno;
	}
	
	public NumeroProtocolo numeroProtocolo(){
		return this.numeroProtocolo;
	}

	public boolean bemSucedida() {
		return retorno != null && retorno.inutilizacaoHomologada();
	}
}
