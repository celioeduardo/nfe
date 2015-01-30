package com.hadrion.nfe.dominio.modelo.nf;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(name="SEQ",sequenceName="SQ_CCE")
@Table(name="CCE")
public class CartaCorrecao {

	@Column(name="SEQUENCIA")
	private int sequencia;
	
	@Column(name="CORRECAO")
	private String correcao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_REGISTRO")
	private Date dataRegistro;
	
	@Lob
	@Column(name="XML_ENVIO")
	@Basic(fetch=FetchType.LAZY)
	private String xmlEnvio;
	
	@Lob
	@Column(name="XML_RETORNO")
	@Basic(fetch=FetchType.LAZY)
	private String xmlRetorno;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
	public CartaCorrecao(int sequencia, String correcao, Date dataRegistro, String xmlEnvio, String xmlRetorno){
		this.sequencia = sequencia;
		this.correcao = correcao;
		this.dataRegistro = dataRegistro;
		this.xmlEnvio = xmlEnvio;
		this.xmlRetorno = xmlRetorno;
	}
	
	public CartaCorrecao(int sequencia, String correcao, String xmlEnvio, 
			String xmlRetorno){
		this(sequencia,correcao,null,xmlEnvio,xmlRetorno);
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private CartaCorrecao(){}
	
	public String correcao(){
		return correcao;
	}

	public int sequencia() {
		return sequencia;
	}

	public Date dataRegistro() {
		return dataRegistro;
	}

	public void registrar(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public boolean estaRegistrada() {
		return dataRegistro() != null;
	}

	public boolean naoEstaRegistrada() {
		return !estaRegistrada();
	}

	public String xmlEnvio() {
		return xmlEnvio;
	}
	public String xmlRetorno() {
		return xmlRetorno;
	}
}
