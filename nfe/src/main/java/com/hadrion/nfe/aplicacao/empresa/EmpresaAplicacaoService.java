package com.hadrion.nfe.aplicacao.empresa;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.aplicacao.empresa.data.EmpresaData;
import com.hadrion.nfe.dominio.modelo.empresa.Empresa;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaRepositorio;

@Service
@Transactional
public class EmpresaAplicacaoService {
	
	@Autowired
	private EmpresaRepositorio empresaRepositorio;
	
	public List<EmpresaData> obterTodas(){
		List<EmpresaData> result = new ArrayList<EmpresaData>();
		
		for (Empresa empresa : empresaRepositorio.obterTodas()) {
			result.add(construir(empresa));
		}
		
		return result;
	}
	
	private EmpresaData construir(Empresa empresa){
		return new EmpresaData(
				String.valueOf(empresa.empresaId()),
				empresa.nome(),
				String.valueOf(empresa.cnpj()));
	}
	
}
