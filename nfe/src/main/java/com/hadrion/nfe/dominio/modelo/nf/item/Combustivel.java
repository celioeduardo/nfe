package com.hadrion.nfe.dominio.modelo.nf.item;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.tipos.Quantidade;

public class Combustivel {
	private Long codAnp;
	private Quantidade quantidade;
	private Uf ufConsumo;
	private Cide cide;
	
	public Combustivel(Long codAnp, Quantidade quantidade, Uf ufConsumo,
			Cide cide) {
		super();
		this.codAnp = codAnp;
		this.quantidade = quantidade;
		this.ufConsumo = ufConsumo;
		this.cide = cide;
	}

	public Long codAnp() {
		return codAnp;
	}

	public Quantidade quantidade() {
		return quantidade;
	}

	public Uf ufConsumo() {
		return ufConsumo;
	}

	public Cide cide() {
		return cide;
	}
	
}
