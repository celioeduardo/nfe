package com.hadrion.nfe.dominio.modelo.inutillizacao;

import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominioPublicador;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;

public class SolicitacaoInutilizacao {
	
	private Ambiente ambiente;
	private boolean homologada;
	private NumeroProtocolo numeroProtocolo;
	private Mensagem retorno;
	private Date dataHoraProcessamento;
	private Date dataHoraSolicitacao;
	
	public SolicitacaoInutilizacao(int i, int j) {
	}

	public boolean estaHomologada() {
		return homologada;
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
		this.homologada=true;
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
}
