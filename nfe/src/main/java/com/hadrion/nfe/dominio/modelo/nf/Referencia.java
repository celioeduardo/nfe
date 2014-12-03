package com.hadrion.nfe.dominio.modelo.nf;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEstadual;

@Entity
@SequenceGenerator(name="SEQ",sequenceName="SQ_REFERENCIA")
@Table(name="REFERENCIA")
public class Referencia {
	@Embedded
	private Modelo modelo;
	
	//modelo 55
	@Embedded
	private ChaveAcesso chaveAcesso;
	
	//modelo 1/1A
	@Enumerated(EnumType.STRING)
	@Column(name="UF_EMITENTE")
	private Uf ufEmitente;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="EMISSAO")
	private Date emissao;
	
	@Embedded
	private Cnpj cnpjEmitente;
	
	@Embedded
	private Serie serie;
	
	@Column(name="NUMERO")
	private Long numero;
	
	//modelo produtor rural
	@Embedded
	private Cpf cpf;
	
	@Embedded
	private InscricaoEstadual ie;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
	protected Referencia(Modelo modelo, ChaveAcesso chave, Uf ufEmitente,
			Date emissao, Cnpj cnpjEmitente, Serie serie, Long numero, Cpf cpf,
			InscricaoEstadual ie) {
		super();
		this.modelo = modelo;
		this.chaveAcesso = chave;
		this.ufEmitente = ufEmitente;
		this.emissao = emissao;
		this.cnpjEmitente = cnpjEmitente;
		this.serie = serie;
		this.numero = numero;
		this.cpf = cpf;
		this.ie = ie;
	}
	

	public static Referencia produtorRural(Uf ufEmitente,Date emissao,Cnpj cnpjEmitente, 
			Serie serie, Long numero, InscricaoEstadual ie){
		
		return new Referencia(new Modelo("4"), null, ufEmitente,
				emissao, cnpjEmitente, serie, numero, null,ie);
	}
	public static Referencia produtorRural(Uf ufEmitente,Date emissao,Cpf cpf, 
			Serie serie, Long numero, InscricaoEstadual ie){
		
		return new Referencia(new Modelo("4"), null, ufEmitente,
				emissao, null, serie, numero, cpf,ie);
	}
	public static Referencia nfe(ChaveAcesso chave){
		return new Referencia(new Modelo("55"), chave, null,
		null, null, null, null, null,null);
	}
	
	public static Referencia modelo_1_1A(Modelo modelo, Uf ufEmitente,
			Date emissao, Cnpj cnpjEmitente, Serie serie, Long numero) {
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
				.append(chaveAcesso, objetoTipado.chaveAcesso)
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
		.append(chaveAcesso)
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
				+ ",chaveAcesso=" + chaveAcesso
				+ ",ufEmitente=" + ufEmitente
				+ ",emissao=" + emissao
				+ ",cnpjEmitente=" + cnpjEmitente
				+ ",serie=" + serie
				+ ",numero=" + numero
				+ ",cpf=" + cpf
				+ ",ie=" + ie + "]";	
	}


	public boolean referenciaNfe() {
		return Modelo.nfe(modelo);
	}

	public boolean referenciaNotaProdutorRural() {
		return Modelo.produtorRural(modelo);
	}
	
	public boolean referenciaNota1_1A() {
		return Modelo.modelo1_1A(modelo);
	}


	public ChaveAcesso chaveAcesso() {
		return chaveAcesso;
	}

	public Uf ufEmitente() {
		return ufEmitente;
	}
	
	public Date emissao(){
		return emissao;
	}
	
	public Cnpj cnpjEmitente(){
		return cnpjEmitente;
	}
	
	public Modelo modelo(){
		return modelo;
	}
	
	public Serie serie(){
		return serie;
	}
	
	public Long numero(){
		return numero;
	}
	
	public Cpf cpfEmitente(){
		return cpf;
	}
	
	public InscricaoEstadual ie(){
		return ie;
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Referencia(){}
	
}
