package com.hadrion.nfe.port.adapters.portal.evento;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.tipos.Cnpj;

public class EventoCancelamento extends Evento{

	public EventoCancelamento(Uf uf, Ambiente ambiente, Cnpj cnpjAutor,
			ChaveAcesso chaveAcesso, Date dataHora,
			NumeroProtocolo numeroProtocolo, String justificativa) {
		
		super(110111, "Cancelamento", uf, ambiente, 
				cnpjAutor, chaveAcesso, dataHora,1);
		this.numeroProtocolo = numeroProtocolo;
		this.justificativa = justificativa;
	}

	private NumeroProtocolo numeroProtocolo;
	private String justificativa;

	public NumeroProtocolo numeroProtocolo() {
		return numeroProtocolo;
	}

	public String justificativa() {
		return justificativa;
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;
	
		if (objeto != null && this.getClass() == objeto.getClass()) {
			EventoCancelamento objetoTipado = (EventoCancelamento) objeto;
			objetosIguais = new EqualsBuilder()
				.append(tipo(),objetoTipado.tipo())
				.append(descricao(),objetoTipado.descricao())
				.append(uf(),objetoTipado.uf())
				.append(ambiente(),objetoTipado.ambiente())
				.append(cnpjAutor(),objetoTipado.cnpjAutor())
				.append(chaveAcesso(),objetoTipado.chaveAcesso())
				.append(dataHora(),objetoTipado.dataHora())
				.append(sequencia(),objetoTipado.sequencia())
				.append(versao(),objetoTipado.versao())
				.append(numeroProtocolo(),objetoTipado.numeroProtocolo())
				.append(justificativa(),objetoTipado.justificativa())
				.isEquals();
		}
	
		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(177,723)
			.append(tipo())
			.append(descricao())
			.append(uf())
			.append(ambiente())
			.append(cnpjAutor())
			.append(chaveAcesso())
			.append(dataHora())
			.append(sequencia())
			.append(versao())
			.append(numeroProtocolo())
			.append(justificativa())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "EventoCancelamento [tipo=" + tipo()
				+ ",descricao="+ descricao()
				+ ",uf="+ uf()
				+ ",ambiente="+ ambiente()
				+ ",cnpjAutor="+ cnpjAutor()
				+ ",chaveAcesso="+ chaveAcesso()
				+ ",dataHora="+ dataHora()
				+ ",sequencia="+ sequencia()
				+ ",versao="+ versao()
				+ ",numeroProtocolo="+ numeroProtocolo()
				+ ",justificativa="+ justificativa()
				+ "]";
	}

}
