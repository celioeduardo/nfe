package com.hadrion.nfe.aplicacao.notista;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.aplicacao.notista.data.NotistaData;
import com.hadrion.nfe.dominio.modelo.notista.Notista;
import com.hadrion.nfe.dominio.modelo.notista.NotistaRepositorio;

@Service
@Transactional
public class NotistaAplicacaoService {
	
	@Autowired
	private NotistaRepositorio notistaRepositorio;
	
	public List<NotistaData> obterTodos(){
		List<NotistaData> result = new ArrayList<NotistaData>();
		
		for (Notista notista : notistaRepositorio.obterTodos()) {
			result.add(construir(notista));
		}
		
		return result;
	}
	
	private NotistaData construir(Notista notista){
		return new NotistaData(
				String.valueOf(notista.notistaId()),
				notista.nome(),
				notista.descricao());
	}
	
}
