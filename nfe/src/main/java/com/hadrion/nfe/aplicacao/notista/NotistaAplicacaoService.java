package com.hadrion.nfe.aplicacao.notista;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.aplicacao.notista.data.NotistaData;
import com.hadrion.nfe.dominio.modelo.hospede.Hospede;
import com.hadrion.nfe.dominio.modelo.notista.Notista;
import com.hadrion.nfe.dominio.modelo.notista.NotistaRepositorio;
import com.hadrion.nfe.dominio.modelo.notista.NotistaService;

@Service
@Transactional
public class NotistaAplicacaoService {
	
	@Autowired
	private NotistaRepositorio notistaRepositorio;
	
	@Autowired
	private NotistaService notistaService;
	
	public List<NotistaData> obterTodos(){
		List<NotistaData> result = new ArrayList<NotistaData>();
		
		for (Notista notista : notistaRepositorio.obterTodos()) {
			result.add(construir(notista));
		}
		
		return result;
	}
	
	public NotistaData obterNotistaPelaIdentificacao(String hospede, String identificacao){
		Notista notista = notistaService.obterNotista(new Hospede(hospede), identificacao);
		return construir(notista);
	}
	
	private NotistaData construir(Notista notista){
		return notista == null ? null : 
			new NotistaData(
				String.valueOf(notista.notistaId()),
				notista.nome(),
				notista.descricao());
	}
	
}
