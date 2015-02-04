package com.hadrion.nfe.dominio.modelo.portal.inutilizacao;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;

public class RetornoInutilizacao {
	
	private NumeroProtocolo numeroProtocolo;
	private Mensagem mensagem;
	private Date dataHoraProcessamento;
	private String xmlEnvio;
	private String xmlRetorno;
	
	public RetornoInutilizacao(
			NumeroProtocolo numeroProtocolo, 
			Mensagem mensagem,
			Date dataHoraProcessamento,
			String xmlEnvio,
			String xmlRetorno) {
		super();
		this.dataHoraProcessamento = dataHoraProcessamento;
		this.numeroProtocolo = numeroProtocolo;
		this.mensagem = mensagem;
		this.xmlEnvio = xmlEnvio;
		this.xmlRetorno = xmlRetorno;
	}
	public RetornoInutilizacao(
			NumeroProtocolo numeroProtocolo, 
			Mensagem mensagem,
			Date dataHoraProcessamento) {
		this(numeroProtocolo,mensagem,dataHoraProcessamento,null,null);
	}

	public Date dataHoraProcessamento(){
		return dataHoraProcessamento;
	}
	
	public NumeroProtocolo numeroProtocolo(){
		return numeroProtocolo;
	}
	
	public Mensagem mensagem(){
		return mensagem;
	}

	public boolean inutilizacaoHomologada() {
		return mensagem.inutilizacaoHomologada();
	}
	
	public String xmlEnvio(){
		return xmlEnvio;
	}
	public String xmlRetorno(){
		return xmlRetorno;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			RetornoInutilizacao objetoTipado = (RetornoInutilizacao) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numeroProtocolo(),objetoTipado.numeroProtocolo())
				.append(mensagem(),objetoTipado.mensagem())
				.append(dataHoraProcessamento(),objetoTipado.dataHoraProcessamento())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(97,213)
			.append(numeroProtocolo())
			.append(mensagem())
			.append(dataHoraProcessamento())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "RetornoInutilizacao [numeroProtocolo=" + numeroProtocolo
				+ ",mensagem="+ mensagem
				+ ",dataHoraProcessamento="+ dataHoraProcessamento
				//+ ",xmlEnvio="+ xmlEnvio
				//+ ",xmlRetorno="+ xmlRetorno
				+ "]";
	}
	
}
