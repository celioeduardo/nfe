package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;
import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.filial.FilialRepositorio;


@Repository
@Transactional
public class FilialRepositorioJpa implements FilialRepositorio {
	
	@Autowired
	private FilialRepositorioSpringData repositorio;
	
	
	@Override
	public Filial obterFilial(FilialId filialId) {
		return repositorio.findByFilialId(filialId);
	}


	@Override
	public void salvar(Filial filial) {
		repositorio.save(filial);
	}


	@Override
	public List<Filial> obterTodas() {
		return repositorio.findAll(new Sort("nome"));
	}


	@Override
	public List<Filial> filiaisDaEmpresa(EmpresaId empresaId) {
		return repositorio.findByEmpresaId(empresaId,new Sort("nome"));
	}

}
