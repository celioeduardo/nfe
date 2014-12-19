package com.hadrion.nfe.aplicacao.filial;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.aplicacao.filial.data.FilialData;
import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialRepositorio;

@Service
@Transactional
public class FilialAplicacaoService {
	
	@Autowired
	private FilialRepositorio filialRepositorio;
	
	public List<FilialData> obterTodas(){
		List<FilialData> result = new ArrayList<FilialData>();
		
		for (Filial filial : filialRepositorio.obterTodas()) {
			result.add(construir(filial));
		}
		
		return result;
	}
	
	private FilialData construir(Filial filial){
		return new FilialData(filial.filialId(),
				filial.nome(),
				filial.cnpj(),
				filial.empresaId());
	}
	
}
