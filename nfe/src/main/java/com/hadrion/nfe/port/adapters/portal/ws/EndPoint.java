package com.hadrion.nfe.port.adapters.portal.ws;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.Ambiente;

class EndPoint {
	
	Ambiente ambiente;
	Servico servico;
	Local local;
	Versao versao;
	
	EndPoint(Ambiente ambiente, Local local, Versao versao, Servico servico) {
		super();
		this.ambiente = ambiente;
		this.local = local;
		this.servico = servico;
		this.versao = versao;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			EndPoint objetoTipado = (EndPoint) objeto;
			objetosIguais = new EqualsBuilder()
				.append(ambiente,objetoTipado.ambiente)
				.append(local,objetoTipado.local)
				.append(versao,objetoTipado.versao)
				.append(servico,objetoTipado.servico)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(191,77)
			.append(ambiente)
			.append(local)
			.append(versao)
			.append(servico)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "EndPoint [ambiente=" + ambiente
				+",local=" + local
				+",servico=" + servico
				+",versao=" + versao
				+ "]";
	}
}
