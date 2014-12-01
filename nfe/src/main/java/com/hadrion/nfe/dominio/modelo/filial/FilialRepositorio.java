package com.hadrion.nfe.dominio.modelo.filial;

public interface FilialRepositorio {

	Filial obterFilial(FilialId filialId);

	void salvar(Filial filial);

}
