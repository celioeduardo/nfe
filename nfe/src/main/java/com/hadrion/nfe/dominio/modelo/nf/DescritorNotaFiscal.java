package com.hadrion.nfe.dominio.modelo.nf;

import java.util.Date;

public class DescritorNotaFiscal {
	private NotaFiscalId notaFiscalId;
	private Modelo modelo;
	private Serie serie;
	private Long numero;
	private Date emissao;
	private Date dataHora;

	@SuppressWarnings("unused")
	private DescritorNotaFiscal() {
		super();
	}

	public DescritorNotaFiscal(NotaFiscalId notaFiscalId,
			Modelo modelo,
			Serie serie,
			Long numero, 
			Date emissao, 
			Date dataHora) {
		this.notaFiscalId = notaFiscalId;
		this.modelo=modelo;
		this.serie=serie;
		this.numero=numero;
		this.emissao=emissao;
		this.dataHora=dataHora;
	}
	
	public NotaFiscalId notaFiscalId(){
		return this.notaFiscalId;
	}

	public Modelo modelo() {
		return modelo;
	}
	public Serie serie() {
		return serie;
	}
	public Long numero() {
		return numero;
	}
	public Date emissao() {
		return emissao;
	}
	public Date dataHora() {
		return dataHora;
	}
}
