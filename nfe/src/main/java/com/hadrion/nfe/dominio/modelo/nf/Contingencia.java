package com.hadrion.nfe.dominio.modelo.nf;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Contingencia {
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CONTIN_DATA_HORA")
	private Date dataHora;
	
	@Column(name="CONTIN_JUSTIFICA")
	private String justificativa;
	
	public Contingencia(Date dataHora, String justificativa) {
		this.dataHora = dataHora;
		setJustificativa(justificativa);
	}
	
	public Date dataHora(){
		return dataHora;
	}
	
	public String justificativa(){
		return justificativa;
	}
	
	private void setJustificativa(String justificativa){
		if (StringUtils.isEmpty(justificativa))
			throw new IllegalArgumentException(
					"Justificativa de Contingência é obrigatória");
		if (justificativa.length() < 15 || justificativa.length() > 256)
			throw new IllegalArgumentException(
					"O tamanho da Justificativa de Contingência deve ser"
					+ " entre 15 e 256 caracteres") ;
		this.justificativa = justificativa;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Contingencia objetoTipado = (Contingencia) objeto;
			objetosIguais = new EqualsBuilder()
				.append(dataHora(),objetoTipado.dataHora())
				.append(justificativa(),objetoTipado.justificativa())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(9611,79)
			.append(dataHora())
			.append(justificativa())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Contingencia [dataHora=" + dataHora()
				+",justificativa = " + justificativa()
				+ "]";
	} 
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Contingencia(){}
}
