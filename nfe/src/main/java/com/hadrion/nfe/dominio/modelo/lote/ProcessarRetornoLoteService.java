package com.hadrion.nfe.dominio.modelo.lote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.ConsultaProcessamentoLoteService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.ProtocoloNotaProcessada;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.RetornoConsultaProcessamentoLote;

@Service
public class ProcessarRetornoLoteService {
	
	@Autowired
	private ConsultaProcessamentoLoteService consultaProcessamentoLoteService;
	
	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio;
	
	public ProcessarRetornoLoteService(
			ConsultaProcessamentoLoteService consultaProcessamentoLoteService,
			NotaFiscalRepositorio notaFiscalRepositorio){
		this.consultaProcessamentoLoteService = consultaProcessamentoLoteService;
		this.notaFiscalRepositorio = notaFiscalRepositorio;
	}
	
	ProcessarRetornoLoteService(){}
	
	public void processar(Lote lote) {
		//TODO obter certificado
		RetornoConsultaProcessamentoLote retorno = 
				consultaProcessamentoLoteService.consultar(lote,null);
		
		if (retorno.loteFoiProcessado()){
			lote.processado(
					retorno.mensagem(),
					retorno.mensagemSefaz());
			processarProtocolos(lote, retorno.protocolos(), retorno.ambiente());
		} else if(retorno.loteEmProcessamento())
			;//Não faz nada
		else
			lote.inconsistente(retorno.mensagem());
	}
	
	private void processarProtocolos(Lote lote, List<ProtocoloNotaProcessada> protocolos, Ambiente ambiente){
		for (ProtocoloNotaProcessada protocolo : protocolos) {
			NotaFiscal nf = nf(protocolo.chaveAcesso(),ambiente);
			
			if (nf == null)
				throw new RuntimeException("Nota Fical não encontrada. Chave: "+protocolo.chaveAcesso());
			
			lote.processarNotaPeloProtocolo(nf.notaFiscalId(), protocolo);
		}
	}
	
	private NotaFiscal nf(ChaveAcesso chave, Ambiente ambiente){
		return notaFiscalRepositorio.notaFiscalPelaChave(chave,ambiente);
	}

}
