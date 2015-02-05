package com.hadrion.nfe.dominio.modelo.inutilizacao;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioRegistro;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;

@Entity
@SequenceGenerator(name="SEQ",sequenceName="SQ_INUTILIZACAO")
@Table(name="INUTILIZACAO")
public class Inutilizacao {
	
	@Embedded
	private InutilizacaoId inutilizacaoId; 
	
	@Enumerated(EnumType.STRING)
	@Column(name="AMBIENTE")
	private Ambiente ambiente;
	
	@Embedded
	private Serie serie; 
	
	@Column(name="NUMERO_INICIAL")
	private long numeroInicial;
	
	@Column(name="NUMERO_FINAL")
	private long numeroFinal; 
	
	@Column(name="JUSTIFICATIVA",length=1000,nullable=false) 
	private String justificativa; 
	
	@Embedded
	private FilialId filialId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_HORA_HOMOLOGACAO")
	private Date dataHoraHomologacao;
	
	@Embedded
	private NumeroProtocolo numeroProtocolo;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="XML_ENVIO")
	private String xmlEnvio;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="XML_RETORNO")
	private String xmlRetorno;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="codigo", column=@Column(name="MSG_COD")),
		@AttributeOverride(name="descricao", column=@Column(name="MSG_DSC"))
	})
	private Mensagem mensagem;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
	@Version
    @Column(name="VERSAO")
    private int versaoConcorrencia;
	
	public Inutilizacao(InutilizacaoId inutilizacaoId, 
			Ambiente ambiente, Serie serie,
			long numeroInicial, long numeroFinal, String justificativa,
			FilialId filialId) {
		super();
		
		if (numeroInicial > numeroFinal)
			throw new RuntimeException("Número Incial não pode ser maior que Número Final");
		
		this.ambiente = ambiente;
		this.inutilizacaoId = inutilizacaoId;
		this.serie = serie;
		this.numeroInicial = numeroInicial;
		this.numeroFinal = numeroFinal;
		this.justificativa = justificativa;
		this.filialId = filialId;
	}

	public InutilizacaoId inutilizacaoId() {
		return inutilizacaoId;
	}

	public Serie serie() {
		return serie;
	}

	public long numeroInicial() {
		return numeroInicial;
	}

	public long numeroFinal() {
		return numeroFinal;
	}

	public String justificativa() {
		return justificativa;
	}

	public FilialId filialId() {
		return filialId;
	}

	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Inutilizacao() {}

	public void homologar(Date dataHoraHomologacao,
			NumeroProtocolo numeroProtocolo, Mensagem mensagem,
			String xmlEnvio, String xmlRetorno) {
		
		if (estaHomologada())
			throw new RuntimeException("Inutilização já foi homologada");
		
		this.dataHoraHomologacao = dataHoraHomologacao;
		this.numeroProtocolo = numeroProtocolo;
		this.mensagem = mensagem;
		this.xmlEnvio = xmlEnvio;
		this.xmlRetorno = xmlRetorno;
		
		DominioRegistro.eventoDominioPublicador().
			publicar(new InutilizacaoHomologada(ambiente));
	}

	public Date dataHoraHomologacao() {
		return dataHoraHomologacao;
	}

	public NumeroProtocolo numeroProtocolo() {
		return numeroProtocolo;
	}

	public String xmlRetorno(){
		return xmlRetorno;
	}

	public boolean estaHomologada() {
		return dataHoraHomologacao != null;
	}

	public void falhar(Mensagem mensagem, 
			String xmlEnvio, String xmlRetorno) {
		this.mensagem = mensagem;
		this.xmlEnvio = xmlEnvio;
		this.xmlRetorno = xmlRetorno;
	}
	
	public Ambiente ambiente(){
		return ambiente;
	}
	
	public Mensagem mensagem(){
		return mensagem;
	}
}
