package com.hadrion.nfe.aplicacao.inutilizacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.nfe.aplicacao.inutilizacao.data.InutilizacaoData;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
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
				inutilizacao.numeroProtocolo() != null ? String.valueOf(inutilizacao.numeroProtocolo()) : null,
				inutilizacao.mensagem() != null ? inutilizacao.mensagem().codigo() : null,
				inutilizacao.mensagem() != null ? inutilizacao.mensagem().descricao() : null);
	}
	
	private Inutilizacao inutilizacao(String inutilizacaoId){
		return inutilizacaoRepositorio.obterPeloId(
				new InutilizacaoId(inutilizacaoId));
	}

	public List<InutilizacaoData> obterPendentes(String filialId,
			String ambiente) {
		List<InutilizacaoData> result = new ArrayList<InutilizacaoData>();
		
		List<Inutilizacao> pendentes = inutilizacaoRepositorio.obterPendentes(
				new FilialId(filialId),Ambiente.valueOf(ambiente));
		
		for (Inutilizacao inutilizacao : pendentes) 
			result.add(contruir(inutilizacao));
		
		return result;
	}

	public List<InutilizacaoData> obterHomologadas(String filialId,
			String ambiente) {
		List<InutilizacaoData> result = new ArrayList<InutilizacaoData>();
		
		List<Inutilizacao> pendentes = inutilizacaoRepositorio.obterHomologadas(
				new FilialId(filialId),Ambiente.valueOf(ambiente));
		
		for (Inutilizacao inutilizacao : pendentes) 
			result.add(contruir(inutilizacao));
		
		return result;
	}
}
