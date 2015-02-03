package com.hadrion.nfe.dominio.modelo.inutilizacao;

import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;

public class Inutilizacao {

	private InutilizacaoId inutilizacaoId; 
	private Serie serie; 
	private long numeroInicial;
	private long numeroFinal; 
	private String justificativa; 
	private FilialId filialId;
	
	public Inutilizacao(InutilizacaoId inutilizacaoId, Serie serie,
			long numeroInicial, long numeroFinal, String justificativa,
			FilialId filialId) {
		super();
		
		if (numeroInicial > numeroFinal)
			throw new RuntimeException("Número Incial não pode ser maior que Número Final");
		
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

}
