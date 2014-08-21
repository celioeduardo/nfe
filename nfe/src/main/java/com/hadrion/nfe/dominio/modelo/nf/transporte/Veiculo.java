package com.hadrion.nfe.dominio.modelo.nf.transporte;

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

}

