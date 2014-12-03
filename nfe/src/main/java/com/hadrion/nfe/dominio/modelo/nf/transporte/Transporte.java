package com.hadrion.nfe.dominio.modelo.nf.transporte;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Transporte {
	@Enumerated(EnumType.STRING)
	private ModalidadeFrete modalidadeFrete;
	
	@Embedded
	private Transportador transportador;
	
	@Embedded
	private RetencaoIcms retencaoIcms;
	
	@Embedded
	private Veiculo veiculo;
	
	@ElementCollection
	@CollectionTable(name="VOLUMES")
	@Column(name="VOLUME")	
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
	public Transporte(ModalidadeFrete modalidadeFrete,
			Transportador transportador, RetencaoIcms retencaoIcms,
			Veiculo veiculo, Volume... volumes) {
		this(modalidadeFrete,transportador,retencaoIcms,veiculo,Arrays.asList(volumes));
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

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Transporte objetoTipado = (Transporte) objeto;
			objetosIguais = new EqualsBuilder()
				.append(modalidadeFrete(),objetoTipado.modalidadeFrete())
				.append(transportador(),objetoTipado.transportador())
				.append(retencaoIcms(),objetoTipado.retencaoIcms())
				.append(veiculo(),objetoTipado.veiculo())
				.append(volumes(),objetoTipado.volumes())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(9677,115)
			.append(modalidadeFrete())
			.append(transportador())
			.append(retencaoIcms())
			.append(veiculo())
			.append(volumes())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Transporte [modalidadeFrete=" + modalidadeFrete()
				+ ",transportador="+ transportador()
				+ ",retencaoIcms="+ retencaoIcms()
				+ ",veiculo="+ veiculo()
				+ ",volumes="+ volumes()
				+ "]";
	}
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Transporte(){}

}
