package com.hadrion.nfe.dominio.modelo.nf.transporte;

import java.util.List;

public class Transporte {
	private ModalidadeFrete modalidadeFrete;
	private Transportador transportador;
	private RetencaoIcms retencaoIcms;
	private Veiculo veiculo;
	private List<Volume> volumes;
	
	public Transporte(ModalidadeFrete modalidadeFrete,
			Transportador transportador, RetencaoIcms retencaoIcms,
			Veiculo veiculo, List<Volume> volumes) {
		super();
		this.modalidadeFrete = modalidadeFrete;
		this.transportador = transportador;
		this.retencaoIcms = retencaoIcms;
		this.veiculo = veiculo;
		this.volumes = volumes;
	}

	public ModalidadeFrete modalidadeFrete() {
		return modalidadeFrete;
	}

	public Transportador transportador() {
		return transportador;
	}

	public RetencaoIcms retencaoIcms() {
		return retencaoIcms;
	}

	public Veiculo veiculo() {
		return veiculo;
	}

	public List<Volume> volumes() {
		return volumes;
	}

	
}
