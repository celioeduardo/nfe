package com.hadrion.nfe.dominio.modelo.inutilizacao;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.InutilizacaoPortalService;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.RetornoInutilizacao;

@Service
@Transactional
public class InutilizacaoService {
	
	@Autowired
	private InutilizacaoRepositorio repositorio;
	
	@Autowired
	private InutilizacaoPortalService inutilizacaoPortalService;
	
	public void inutilizar(Inutilizacao inutilizacao){
		RetornoInutilizacao retorno = inutilizacaoPortalService.inutilizar(inutilizacao);
		
		if (retorno.inutilizacaoHomologada())
			inutilizacao.homologar(
					retorno.dataHoraProcessamento(), 
					retorno.numeroProtocolo(), 
					retorno.xmlRetorno());
		else
			inutilizacao.falhar(retorno.xmlRetorno());
		
		repositorio.salvar(inutilizacao);
	}

}
