package com.hadrion.nfe.aplicacao.inutilizacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.nfe.aplicacao.inutilizacao.data.InutilizacaoData;
import com.hadrion.nfe.dominio.modelo.inutilizacao.Inutilizacao;
import com.hadrion.nfe.dominio.modelo.inutilizacao.InutilizacaoId;
import com.hadrion.nfe.dominio.modelo.inutilizacao.InutilizacaoRepositorio;
import com.hadrion.nfe.dominio.modelo.inutilizacao.InutilizacaoService;

@Service
@Transactional
public class InutilizacaoAplicacaoService {

	@Autowired
	private InutilizacaoService inutilizacaoService;
	@Autowired
	private InutilizacaoRepositorio inutilizacaoRepositorio;
	
	public void inutilizar(InutilizarComando comando){
		Inutilizacao inutilizacao = inutilizacao(comando.getInutilizacaoId());
		inutilizacaoService.inutilizar(inutilizacao);
	}
	
	@Transactional(readOnly=true)
	public InutilizacaoData obter(String inutilizacaoId){
		return contruir(inutilizacao(inutilizacaoId));
	}
	
	private InutilizacaoData contruir(Inutilizacao inutilizacao){
		return new InutilizacaoData(
				inutilizacao.inutilizacaoId().id(), 
				inutilizacao.ambiente(), 
				String.valueOf(inutilizacao.serie()), 
				inutilizacao.numeroInicial(), 
				inutilizacao.numeroFinal(), 
				inutilizacao.justificativa(), 
				inutilizacao.dataHoraHomologacao(), 
				String.valueOf(inutilizacao.numeroProtocolo()),
				inutilizacao.mensagem() != null ? inutilizacao.mensagem().codigo() : null,
				inutilizacao.mensagem() != null ? inutilizacao.mensagem().descricao() : null);
	}
	
	private Inutilizacao inutilizacao(String inutilizacaoId){
		return inutilizacaoRepositorio.obterPeloId(
				new InutilizacaoId(inutilizacaoId));
	}
}
