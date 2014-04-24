package com.hadrion.nfe.dominio.modelo.inutillizacao;

import java.util.Date;

import com.hadrion.comum.dominio.modelo.EventoDominio;
import com.hadrion.nfe.dominio.modelo.Ambiente;

public class InutilizacaoHomologada implements EventoDominio{

	private Ambiente ambiente;
	private int versaoEvento;
	private Date ocorridoEm;
	
	public InutilizacaoHomologada(Ambiente ambiente) {
		super();
		this.ambiente = ambiente;
		this.versaoEvento = 1;
		this.ocorridoEm = new Date();
	}	
	
	public Ambiente ambiente(){
		return ambiente;
	}
	
	@Override
	public int versaoEvento() {
		return versaoEvento;
	}

	@Override
	public Date ocorridoEm() {
		return ocorridoEm;
	}

}
