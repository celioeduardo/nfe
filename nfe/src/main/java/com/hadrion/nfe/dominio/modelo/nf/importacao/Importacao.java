package com.hadrion.nfe.dominio.modelo.nf.importacao;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;


public class Importacao {
	
	private int numeroDi;
	private Date dataDi;
	private String localDesembarque;
	private Uf ufDesembarque;
	private Date dataDesembarque;
	private ViaTransporte viaTransporte;
	private Double codigoExportador;
	private Adicao adicao;
	public Importacao(int numeroDi, Date dataDi, String localDesembarque,
			Uf ufDesembarque, Date dataDesembarque, ViaTransporte viaTransporte,
			Double codigoExportador,
			Adicao adicao) {
		this.numeroDi=numeroDi;
		this.dataDi=dataDi;
		this.localDesembarque=localDesembarque;
		this.ufDesembarque=ufDesembarque;
		this.dataDesembarque=dataDesembarque;
		this.viaTransporte=viaTransporte;
		this.codigoExportador=codigoExportador;
		this.adicao=adicao;
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Importacao objetoTipado = (Importacao) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numeroDi,objetoTipado.numeroDi)
				.append(dataDi,objetoTipado.dataDi)
				.append(localDesembarque,objetoTipado.localDesembarque)
				.append(ufDesembarque,objetoTipado.ufDesembarque)
				.append(dataDesembarque,objetoTipado.dataDesembarque)
				.append(viaTransporte,objetoTipado.viaTransporte)
				.append(codigoExportador,objetoTipado.codigoExportador)
				.append(adicao,objetoTipado.adicao)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(1327,171)
			.append(numeroDi)
			.append(dataDi)
			.append(localDesembarque)
			.append(ufDesembarque)
			.append(dataDesembarque)
			.append(viaTransporte)
			.append(codigoExportador)
			.append(adicao)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Importacao [numeroDi=" + numeroDi
				+ ",dataDi="+ dataDi
				+ ",localDesembarque="+ localDesembarque
				+ ",ufDesembarque="+ ufDesembarque
				+ ",dataDesembarque="+ dataDesembarque
				+ ",viaTransporte="+ viaTransporte
				+ ",codigoExportador="+ codigoExportador
				+ ",adicao="+ adicao
				+ "]";
	}	
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Importacao(){}

}
