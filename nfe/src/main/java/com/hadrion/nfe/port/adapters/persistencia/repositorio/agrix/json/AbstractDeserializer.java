package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class AbstractDeserializer {

	public AbstractDeserializer() {
		super();
	}

	protected NotaFiscalId criarNotaFiscalId(String guid, Ambiente ambiente) {
		return new NotaFiscalId(ambiente.sigla()+"-"+guid);
	}

}