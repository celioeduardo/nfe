package com.hadrion.nfe.dominio.modelo.portal.recepcao;

import com.hadrion.nfe.dominio.modelo.lote.NumeroReciboLote;

public class ReciboLote {
	private NumeroReciboLote numero;
	
	public ReciboLote(NumeroReciboLote numero){
		this.numero = numero;
	}
	
	public NumeroReciboLote numero(){
		return numero;
	}
}
