package com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.MensagemSefaz;

public class RetornoConsultaProcessamentoLote {
	
	private Ambiente ambiente;
	private Mensagem mensagem;
	private MensagemSefaz mensagemSefaz;
	private List<ProtocoloNotaProcessada> protocolos;
	
	public RetornoConsultaProcessamentoLote(
			Ambiente ambiente,
			Mensagem mensagem,
			MensagemSefaz mensagemSefaz,
			List<ProtocoloNotaProcessada> protocolos) {
		super();
		this.ambiente = ambiente;
		this.mensagem = mensagem;
		this.mensagemSefaz = mensagemSefaz;
		this.protocolos = protocolos;
	}
	
	public Ambiente ambiente(){
		return ambiente;
	}
	
	public Mensagem mensagem(){
		return mensagem;
	}
	
	public MensagemSefaz mensagemSefaz(){
		return mensagemSefaz;
	}
	
	public List<ProtocoloNotaProcessada> protocolos(){
		return protocolos;
	}

	public boolean loteProcessadoComSucesso() {
		return false;
	}
	
	public boolean loteFoiProcessado() {
		return mensagem.codigo() ==  104;
	}

	public boolean loteEmProcessamento() {
		return mensagem.codigo() ==  105;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			RetornoConsultaProcessamentoLote objetoTipado = (RetornoConsultaProcessamentoLote) objeto;
			objetosIguais = new EqualsBuilder()
				.append(ambiente(),objetoTipado.ambiente())
				.append(mensagem(),objetoTipado.mensagem())
				.append(mensagemSefaz(),objetoTipado.mensagemSefaz())
				.append(protocolos(),objetoTipado.protocolos())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(941,27)
			.append(ambiente())
			.append(mensagem())
			.append(mensagemSefaz())
			.append(protocolos())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "RetornoConsultaProcessamentoLote [ambiente=" + ambiente()
				+",mensagem=" + mensagem()
				+",mensagemSefaz=" + mensagemSefaz()
				+",protocolos=" + protocolos()
				+ "]";
	}
}
