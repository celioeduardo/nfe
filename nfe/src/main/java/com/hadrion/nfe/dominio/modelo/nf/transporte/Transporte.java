package com.hadrion.nfe.dominio.modelo.nf.transporte;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Transporte {
	
	@Enumerated(EnumType.STRING)
	@Column(name="TRANS_FRETE")
	private ModalidadeFrete modalidadeFrete;
	
	@Embedded
	private Transportador transportador;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="valorServico.quantia", column=@Column(name="TRANS_RETICMS_VALSERV")),
		@AttributeOverride(name="baseCalculo.quantia", column=@Column(name="TRANS_RETICMS_BASE")),
		@AttributeOverride(name="aliquota", column=@Column(name="TRANS_RETICMS_ALIQUOTA")),
		@AttributeOverride(name="valor.quantia", column=@Column(name="TRANS_RETICMS_VALOR")),
		@AttributeOverride(name="cfop.numero", column=@Column(name="TRANS_CFOP"))
	})
	private RetencaoIcms retencaoIcms;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="tipo", column=@Column(name="TRANS_VEIC_TIPO")),
		@AttributeOverride(name="placa.uf", column=@Column(name="TRANS_VEIC_PLACA_UF")),
		@AttributeOverride(name="placa.numero", column=@Column(name="TRANS_VEIC_PLACA_NUMERO")),
		@AttributeOverride(name="registroAntt", column=@Column(name="TRANS_VEIC_REG_ANTT")),
		@AttributeOverride(name="identificacao", column=@Column(name="TRANS_VEIC_IDENTIFICACAO"))
	})
	private Veiculo veiculo;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="VOLUMES",
	    joinColumns=@JoinColumn(name="ID_NF"),
	    inverseJoinColumns=@JoinColumn(name="ID_VOLUME"))
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
	
	public Transporte mesclar(Transporte transp){
		
		if (equals(transp)) return this;
		
		if(transp==null) return null;
		
		return new Transporte(
				transp.modalidadeFrete, 
				transp.transportador, 
				transp.retencaoIcms, 
				transp.veiculo, 
				transp.volumes);
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Transporte(){}

}
