package com.hadrion.nfe.dominio.modelo.lote;

import java.util.HashSet;
import java.util.Set;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class Lote {
	
	private Set<NotaFiscalId> notas;
	
	public Lote(Set<NotaFiscalId> notas){
		this.notas=new HashSet<NotaFiscalId>(notas);
	}
	
	public int quantidade() {
		return notas.size();
	}

	protected Lote(){
		
	}
}
