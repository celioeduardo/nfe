package com.hadrion.nfe.dominio.modelo.inutilizacao;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.certificado.CertificadoService;
import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.InutilizacaoPortalService;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.RetornoInutilizacao;
import com.hadrion.nfe.port.adapters.portal.ws.Local;

@Service
@Transactional
public class InutilizacaoService {
	
	//@Autowired
	private InutilizacaoRepositorio repositorio;
	
	@Autowired
	private CertificadoService certificadoService;
	
	@Autowired
	private FilialRepositorio filialRepositorio;
	
	@Autowired
	private InutilizacaoPortalService inutilizacaoPortalService;
	
	public void inutilizar(Inutilizacao inutilizacao){
		Filial filial = filialRepositorio.obterFilial(inutilizacao.filialId());
		
		RetornoInutilizacao retorno = inutilizacaoPortalService.inutilizar(
				inutilizacao, 
				obterCertificado(inutilizacao),
				Local.obterPeloModoOperacao(filial.modoOperacao(), filial.uf()),
				filial.uf(),
				filial.cnpj());
		
		if (retorno.inutilizacaoHomologada())
			inutilizacao.homologar(
					retorno.dataHoraProcessamento(), 
					retorno.numeroProtocolo(), 
					retorno.xmlRetorno());
		else
			inutilizacao.falhar(retorno.xmlRetorno());
		
		repositorio.salvar(inutilizacao);
	}
	
	protected Certificado obterCertificado(Inutilizacao inutilizacao){
		return certificadoService.obterCertificadoPelaFilial(inutilizacao.filialId());
	}
	
	

}
