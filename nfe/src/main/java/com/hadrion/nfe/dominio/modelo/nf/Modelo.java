package com.hadrion.nfe.dominio.modelo.nf;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Modelo {

	public static final Modelo NFE = new Modelo("55");
	public static final Modelo PRODUTOR_RURAL = new Modelo("04");
	public static final Modelo MODELO_1 = new Modelo("01");
	public static final Modelo MODELO_1A = new Modelo("1A");
	
	@Column(name="MODELO")
	private String modelo;

	public Modelo(String modelo){
		this.modelo=modelo;
	}

	public String modelo() {
		return modelo;
	}
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Modelo objetoTipado = (Modelo) objeto;
			try {
				if (Integer.parseInt(modelo()) == Integer.parseInt(objetoTipado.modelo()))
					return true;
			} catch (Exception e) {}
			objetosIguais = this.modelo().equals(objetoTipado.modelo());
		}

		return objetosIguais;
	}
	

	@Override
	public int hashCode() {
		return new HashCodeBuilder(27851,57) 
				.append(modelo)
				.toHashCode();
	}
	
	@Override
	public String toString() {
		return modelo;
	}

	public static boolean nfe(Modelo modelo) {
		return NFE.equals(modelo);
	}
	public static boolean produtorRural(Modelo modelo) {
		return PRODUTOR_RURAL.equals(modelo);
	}
	public static boolean modelo1_1A(Modelo modelo) {
		return MODELO_1.equals(modelo) || MODELO_1A.equals(modelo);
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Modelo(){}
}
