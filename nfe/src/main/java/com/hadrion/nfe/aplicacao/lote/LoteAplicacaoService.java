package com.hadrion.nfe.aplicacao.lote;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.nfe.aplicacao.lote.data.LoteData;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.LoteId;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;
import com.hadrion.nfe.dominio.modelo.lote.ProcessarRetornoLoteService;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.MensagemSefaz;

@Service
@Transactional
public class LoteAplicacaoService {
	
	@Autowired
	private LoteRepositorio loteRepositorio;
	
	@Autowired
	private ProcessarRetornoLoteService processarRetornoLoteService;
	
	public List<LoteData> obterLotesPendentes(Ambiente ambiente, String filialId, String notistaId){
		List<Lote> lotes = null;
		if (notistaId != null && !notistaId.isEmpty())
			lotes = loteRepositorio.lotesEmProcessamento(ambiente,new FilialId(filialId),new NotistaId(notistaId));
		else
			lotes = loteRepositorio.lotesEmProcessamento(ambiente,new FilialId(filialId));
		
		List<LoteData> result = new ArrayList<LoteData>();
		
		for (Lote lote : lotes) 
			result.add(contruir(lote));
		
		return result;
		
	}
	
	public void processarRetorno(String loteId){
		Lote lote = lote(loteId);
		if (lote != null)
			processarRetornoLoteService.processar(lote);
	}
	
	private Lote lote(String loteId){
		return loteRepositorio.obterLote(new LoteId(loteId));
	}
	
	private LoteData contruir(Lote lote) {
		return new LoteData(
				String.valueOf(lote.loteId()),
				lote.numero(),
				String.valueOf(lote.numeroRecibo()), 
				String.valueOf(lote.ambiente()),
				codigoMensagem(lote.mensagemErro()),
				descricaoMensagem(lote.mensagemErro()),
				codigoMensagem(lote.mensagemProcessamento()),
				descricaoMensagem(lote.mensagemProcessamento()),
				codigoMensagem(lote.mensagemSefaz()),
				descricaoMensagem(lote.mensagemSefaz()));
	}
	
	private String codigoMensagem(Mensagem msg){
		if (msg != null)
			return String.valueOf(msg.codigo());
		return null;
	}
	
	private String descricaoMensagem(Mensagem msg){
		if (msg != null)
			return msg.descricao();
		return null;
	}
	private String codigoMensagem(MensagemSefaz msg){
		if (msg != null)
			return String.valueOf(msg.codigo());
		return null;
	}
	
	private String descricaoMensagem(MensagemSefaz msg){
		if (msg != null)
			return msg.descricao();
		return null;
	}
	
	
}
