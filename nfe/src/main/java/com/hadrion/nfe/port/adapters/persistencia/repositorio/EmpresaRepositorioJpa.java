package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.empresa.Empresa;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaRepositorio;


@Repository
@Transactional
public class EmpresaRepositorioJpa implements EmpresaRepositorio {
	
	@Autowired
	private EmpresaRepositorioSpringData repositorio;
	
	@Override
	public Certificado obterCertificadoPelaEmpresa(EmpresaId empresaId) {
		Empresa empresa = repositorio.findByEmpresaId(empresaId);
		if (empresa == null)
			throw new IllegalArgumentException("Empresa não existe");
		return empresa.certificado();
	}

	@Override
	public void salvar(Empresa empresa) {
		repositorio.save(empresa);
	}

	@Override
	public List<Empresa> obterTodas() {
		return repositorio.findAll(new Sort("nome"));
	}

}
