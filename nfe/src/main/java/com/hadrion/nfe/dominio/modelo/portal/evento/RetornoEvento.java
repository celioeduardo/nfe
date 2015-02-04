package com.hadrion.nfe.dominio.modelo.portal.evento;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;

public class RetornoEvento {
	
	private ChaveAcesso chaveAcesso;
	private int codigoTipoEvento;
	private String descricaoTipoEvento;
	private NumeroProtocolo numeroProtocolo;
	private Mensagem mensagem;
	private Date dataHoraRegistroEvento;
	private String xmlEnvio;
	private String xmlRetorno;
	
	public RetornoEvento(
			ChaveAcesso chaveAcesso,
			int codigoTipoEvento,
			String descricaoTipoEvento,
			Mensagem mensagem,
			Date dataHoraRegistroEvento,
			NumeroProtocolo numeroProtocolo) {
		super();
		this.chaveAcesso = chaveAcesso;
		this.codigoTipoEvento = codigoTipoEvento;
		this.descricaoTipoEvento = descricaoTipoEvento;
		this.mensagem = mensagem;
		this.dataHoraRegistroEvento = dataHoraRegistroEvento;
		this.numeroProtocolo = numeroProtocolo;
	}

	public ChaveAcesso chaveAcesso(){
		return chaveAcesso;
	}
	
	public void definirXmlRetorno(String xmlRetorno){
		this.xmlRetorno = xmlRetorno;
	}
	public String xmlRetorno(){
		return xmlRetorno;
	}
	public void definirXmlEnvio(String xmlEnvio){
		this.xmlEnvio = xmlEnvio;
	}
	
	public String xmlEnvio(){
		return this.xmlEnvio;
	}
	
	public Date dataHoraRegistroEvento(){
		return dataHoraRegistroEvento;
	}
	
	public NumeroProtocolo numeroProtocolo(){
		return numeroProtocolo;
	}
	
	public Mensagem mensagem(){
		return mensagem;
	}

	public int codigoTipoEvento(){
		return codigoTipoEvento;
	}
	
	public String descricaoTipoEvento(){
		return this.descricaoTipoEvento;
	}
	
	public boolean cancelamentoHomologado() {
		return codigoTipoEvento == 110111 &&
				numeroProtocolo() != null;
	}
	public boolean cartaCorrecaoRegistrada() {
		return codigoTipoEvento == 110110 &&
				numeroProtocolo() != null;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			RetornoEvento objetoTipado = (RetornoEvento) objeto;
			objetosIguais = new EqualsBuilder()
				.append(chaveAcesso(),objetoTipado.chaveAcesso())
				.append(codigoTipoEvento(),objetoTipado.codigoTipoEvento())
				.append(descricaoTipoEvento(),objetoTipado.descricaoTipoEvento())
				.append(numeroProtocolo(),objetoTipado.numeroProtocolo())
				.append(mensagem(),objetoTipado.mensagem())
				.append(dataHoraRegistroEvento(),objetoTipado.dataHoraRegistroEvento())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(97,213)
			.append(chaveAcesso())
			.append(codigoTipoEvento())
			.append(descricaoTipoEvento())
			.append(numeroProtocolo())
			.append(mensagem())
			.append(dataHoraRegistroEvento())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "RetornoEvento [chaveAcesso=" + chaveAcesso
				+ ",codigoTipoEvento="+ codigoTipoEvento
				+ ",descricaoTipoEvento="+ descricaoTipoEvento
				+ ",mensagem="+ mensagem
				+ ",dataHoraRegistroEvento="+ dataHoraRegistroEvento
				+ ",numeroProtocolo="+ numeroProtocolo
				+ "]";
	}

}
