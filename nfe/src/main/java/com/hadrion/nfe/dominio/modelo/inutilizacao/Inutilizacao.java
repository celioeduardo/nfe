package com.hadrion.nfe.dominio.modelo.inutilizacao;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioRegistro;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;

public class Inutilizacao {

	private InutilizacaoId inutilizacaoId; 
	private Ambiente ambiente;
	private Serie serie; 
	private long numeroInicial;
	private long numeroFinal; 
	private String justificativa; 
	private FilialId filialId;
	private Date dataHoraHomologacao;
	private NumeroProtocolo protocolo;
	private String xmlRetorno;
	
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
			NumeroProtocolo numeroProtocolo, String xmlRetorno) {
		this.dataHoraHomologacao = dataHoraHomologacao;
		this.protocolo = numeroProtocolo;
		this.xmlRetorno = xmlRetorno;
		
		DominioRegistro.eventoDominioPublicador().
			publicar(new InutilizacaoHomologada(ambiente));
	}

	public Date dataHoraHomologacao() {
		return dataHoraHomologacao;
	}

	public NumeroProtocolo numeroProtocolo() {
		return protocolo;
	}

	public String xmlRetorno(){
		return xmlRetorno;
	}

	public boolean estaHomologada() {
		return dataHoraHomologacao != null;
	}

	public void falhar(String xmlRetorno) {
		this.xmlRetorno = xmlRetorno;
	}
	
	public Ambiente ambiente(){
		return ambiente;
	}
}
