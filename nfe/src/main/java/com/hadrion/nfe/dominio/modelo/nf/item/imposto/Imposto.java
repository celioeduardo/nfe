package com.hadrion.nfe.dominio.modelo.nf.item.imposto;

import com.hadrion.nfe.dominio.modelo.cofins.Cofins;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.pis.Pis;
import com.hadrion.nfe.tipos.Dinheiro;

public class Imposto {
	
	public static final Imposto NULO = new Imposto(
			Dinheiro.ZERO,Icms.NULO,Pis.NULO,Cofins.NULO);
	
	private Dinheiro valorTotalAproximado;
	private Icms icms;
	private Pis pis;
	private Cofins cofins;
	
	public Imposto(Dinheiro valorTotalAproximado, Icms icms, Pis pis,
			Cofins cofins) {
		super();
		this.valorTotalAproximado = valorTotalAproximado;
		this.icms = icms;
		this.pis = pis;
		this.cofins = cofins;
	}

	public Dinheiro valorTotalAproximado() {
		return valorTotalAproximado;
	}

	public Icms icms() {
		if (icms == null) 
			return Icms.NULO;
		return icms;
	}

	public Pis pis() {
		if (pis == null)
			return Pis.NULO;
		return pis;
	}

	public Cofins cofins() {
		if (cofins == null)
			return Cofins.NULO;
		return cofins;
	}
}
