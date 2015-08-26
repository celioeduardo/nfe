package com.hadrion.nfe.dominio.modelo.nf.importacao;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;


public class Importacao {
	
	private int numero;
	private Date emissao;
	private String localDesembarque;
	private Uf ufDesembarque;
	private Date dataDesembarque;
	private ViaTransporte viaTransporte;
	private String codigoExportador;
	private Adicao adicao;
	private Dinheiro valorArfmm;
	private Intermediacao intermediacao;
	private Cnpj cnpjTerceiro;
	private Uf ufTerceiro;
	public Importacao(int numero,Date data,String localDesembarque,Uf ufDesembarque, 
			Date dataDesembarque,ViaTransporte viaTransporte,String codigoExportador,
			Dinheiro valorArfmm,Intermediacao intermediacao,Cnpj cnpjTerceiro,Uf ufTerceiro,
			Adicao adicao) {
		this.numero=numero;
		this.emissao=data;
		this.localDesembarque=localDesembarque;
		this.ufDesembarque=ufDesembarque;
		this.dataDesembarque=dataDesembarque;
		this.viaTransporte=viaTransporte;
		this.codigoExportador=codigoExportador;
		this.valorArfmm=valorArfmm;
		this.intermediacao=intermediacao;
		this.cnpjTerceiro=cnpjTerceiro;
		this.ufTerceiro=ufTerceiro;
		this.adicao=adicao;
	}
	public int numero() {
		return this.numero;
	}
	
	public Date emissao() {
		return emissao;
	}
	
	public String localDesembarque() {
		return localDesembarque;
	}
	public Uf ufDesembarque() {
		return ufDesembarque;
	}
	public Date dataDesembarque() {
		return dataDesembarque;
	}
	public ViaTransporte viaTransporte() {
		return viaTransporte;
	}
	public Dinheiro valorARFMM() {
		return valorArfmm;
	}
	public Intermediacao intermediacao() {
		return intermediacao;
	}
	public Cnpj cnpjTerceiro() {
		return cnpjTerceiro;
	}
	public Uf ufTerceiro() {
		return ufTerceiro;
	}
	public String codigoExportador() {
		return codigoExportador;
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Importacao objetoTipado = (Importacao) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero,objetoTipado.numero)
				.append(emissao,objetoTipado.emissao)
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
			.append(numero)
			.append(emissao)
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
		return "Importacao [numeroDi=" + numero
				+ ",dataDi="+ emissao
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
