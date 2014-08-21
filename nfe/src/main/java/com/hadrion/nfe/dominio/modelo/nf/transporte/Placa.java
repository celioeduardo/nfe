package com.hadrion.nfe.dominio.modelo.nf.transporte;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;

public class Placa {
	private Uf uf;
	private String numero;
	
	public Placa(Uf uf, String numero) {
		super();
		this.uf = uf;
		this.numero = numero;
	}
	public Uf Uf() {
		return uf;
	}
	public String numero() {
		return numero;
	}
	
	
}
