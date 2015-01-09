package com.hadrion.nfe.dominio.modelo.filial;

import java.util.List;

import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;

public interface FilialRepositorio {

	Filial obterFilial(FilialId filialId);
	
	public List<Filial> obterTodas();
	
	void salvar(Filial filial);

	public List<Filial> filiaisDaEmpresa(EmpresaId empresaId);

}
