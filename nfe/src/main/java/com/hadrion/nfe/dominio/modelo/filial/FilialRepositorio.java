package com.hadrion.nfe.dominio.modelo.filial;

import java.util.List;

public interface FilialRepositorio {

	Filial obterFilial(FilialId filialId);
	
	public List<Filial> obterTodas();
	
	void salvar(Filial filial);

}
