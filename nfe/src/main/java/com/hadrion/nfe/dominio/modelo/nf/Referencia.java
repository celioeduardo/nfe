package com.hadrion.nfe.dominio.modelo.nf;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.icms.SubstituicaoTributaria;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEstadual;

public class Referencia {
	private Modelo modelo;
	//modelo 55
	private ChaveAcesso chave;
	//modelo 1/1A	
	private Uf ufEmitente;
	private Date emissao;
	private Cnpj cnpjEmitente;
	private Serie serie;
	private Long numero;
	//modelo produtor rural
	private Cpf cpf;
	private InscricaoEstadual ie;
	
	protected Referencia(Modelo modelo, ChaveAcesso chave, Uf ufEmitente,
			Date emissao, Cnpj cnpjEmitente, Serie serie, Long numero, Cpf cpf,
			InscricaoEstadual ie) {
		super();
		this.modelo = modelo;
		this.chave = chave;
		this.ufEmitente = ufEmitente;
		this.emissao = emissao;
		this.cnpjEmitente = cnpjEmitente;
		this.serie = serie;
		this.numero = numero;
		this.cpf = cpf;
		this.ie = ie;
	}
	

	public static Referencia produtorRural(Modelo modelo, 
			Uf ufEmitente,Date emissao,Cnpj cnpjEmitente, 
			Serie serie, Long numero, InscricaoEstadual ie){
		
		return new Referencia(modelo, null , ufEmitente,
				emissao, cnpjEmitente, serie, numero, null,
				ie);
	}
	public static Referencia produtorRural(Modelo modelo, 
			Uf ufEmitente,Date emissao,Cpf cpf, 
			Serie serie, Long numero, InscricaoEstadual ie){
		
		return new Referencia(modelo, null , ufEmitente,
				emissao, null, serie, numero, cpf,
				ie);
	}
	public static Referencia nfe(ChaveAcesso chave){
		return new Referencia(new Modelo("55"), chave, null,
		null, null, null, null, null,null);
	}
	
	public static Referencia modelo_1_1A(Modelo modelo, ChaveAcesso chave, Uf ufEmitente,
			Date emissao, Cnpj cnpjEmitente, Serie serie, Long numero, Cpf cpf,
			InscricaoEstadual ie) {
		return new Referencia(modelo, null , ufEmitente,
				emissao, cnpjEmitente, serie, numero, null, null);
		
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Referencia objetoTipado = (Referencia) objeto;
			objetosIguais = new EqualsBuilder()
				.append(modelo, objetoTipado.modelo)
				.append(chave, objetoTipado.chave)
				.append(ufEmitente, objetoTipado.ufEmitente)
				.append(emissao, objetoTipado.emissao)
				.append(cnpjEmitente, objetoTipado.cnpjEmitente)
				.append(serie, objetoTipado.serie)
				.append(numero, objetoTipado.numero)
				.append(cpf, objetoTipado.cpf)
				.append(ie, objetoTipado.ie)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(664531,27)
		.append(modelo)
		.append(chave)
		.append(ufEmitente)
		.append(emissao)
		.append(cnpjEmitente)
		.append(serie)
		.append(numero)
		.append(cpf)
		.append(ie)
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Referencia [modelo=" + modelo
				+ ",chave=" + chave
				+ ",ufEmitente=" + ufEmitente
				+ ",emissao=" + emissao
				+ ",cnpjEmitente=" + cnpjEmitente
				+ ",serie=" + serie
				+ ",numero=" + numero
				+ ",cpf=" + cpf
				+ ",ie=" + ie + "]";	
	}

}
