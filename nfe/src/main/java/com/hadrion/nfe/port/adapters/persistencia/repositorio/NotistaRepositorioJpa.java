package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.hadrion.nfe.dominio.modelo.notista.Notista;
import com.hadrion.nfe.dominio.modelo.notista.NotistaRepositorio;

@Repository
@Transactional
public class NotistaRepositorioJpa implements NotistaRepositorio{

	@Autowired
	private NotistaRepositorioSpringData repositorio;

	@Override
	public List<Notista> obterTodos() {
		return repositorio.findAll(new Sort("nome"));
	}
	
	
}
