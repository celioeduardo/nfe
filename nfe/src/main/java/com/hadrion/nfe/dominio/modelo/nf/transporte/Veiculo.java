package com.hadrion.nfe.dominio.modelo.nf.transporte;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Veiculo {
	private TipoVeiculo tipo;
	private Placa placa;
	private String registroAntt;
	private String identificacao;
	
	public Veiculo(TipoVeiculo tipo, Placa placa, String registroAntt,
			String identificacao) {
		super();
		this.tipo = tipo;
		this.placa = placa;
		this.registroAntt = registroAntt;
		this.identificacao = identificacao;
	}

	public TipoVeiculo tipo() {
		return tipo;
	}

	public Placa placa() {
		return placa;
	}

	public String registroAntt() {
		return registroAntt;
	}

	public String identificacao() {
		return identificacao;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Veiculo objetoTipado = (Veiculo) objeto;
			objetosIguais = new EqualsBuilder()
				.append(tipo(),objetoTipado.tipo())
				.append(placa(),objetoTipado.placa())
				.append(registroAntt(),objetoTipado.registroAntt())
				.append(identificacao(),objetoTipado.identificacao())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(6549,991)
			.append(tipo())
			.append(placa())
			.append(registroAntt())
			.append(identificacao())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Veiculo [tipo=" + tipo()
				+ ",placa="+ placa()
				+ ",registroAntt="+ registroAntt()
				+ ",identificacao="+ identificacao()
				+ "]";
	}
}

