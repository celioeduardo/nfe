package com.hadrion.nfe.dominio.modelo.nf.cobranca;

import java.util.List;

public class Cobranca {
	private Fatura fatura;
	private List<Duplicata> duplicatas;
	
	public Cobranca(Fatura fatura, List<Duplicata> duplicatas) {
		super();
		this.fatura = fatura;
		this.duplicatas = duplicatas;
	}

	public Fatura fatura() {
		return fatura;
	}

	public List<Duplicata> duplicata() {
		return duplicatas;
	}
	
}
