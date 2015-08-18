package com.hadrion.nfe.port.adapters.agrix.repositorio;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.NotaFiscalRepositorioSpringData;

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class SincronizarService {
	
	@Autowired
	private AgrixService agrixService;
	
	@Autowired
	private FilialRepositorio filialRepositorio; 
	
	@Autowired
	private NotaFiscalRepositorioSpringData repositorio;
	
	private static final Logger logger = LoggerFactory.getLogger(SincronizarService.class);
	
	public void sincronizar(List<NotaFiscalId> notas, Ambiente ambiente) {
		logger.debug("obtendo {} notas no agrix...");
		List<NotaFiscal> notasAgrix = agrixService.obterNotas(notas,ambiente);
		logger.debug("notas obtidas do agrix");
		
		for (NotaFiscal nfAgrix : notasAgrix) {
			mesclar(nfAgrix);
		}
	}
	
	private void mesclar(NotaFiscal nfAgrix){
		
		logger.debug("mesclando nf {}",nfAgrix.numero());
		
		logger.debug("verificando se existe na base local.");
		NotaFiscal nfLocal = repositorio.findByNotaFiscalId(nfAgrix.notaFiscalId());
		
		if (nfLocal != null){
			logger.debug("nota existe na base local");
			if (nfLocal.pendenteDeTransmissao()){
				logger.debug("início da mesclagem - nf {}", nfLocal.numero());
				nfLocal.mesclar(nfAgrix);
				logger.debug("fim da mesclagem", nfLocal.numero());
			}
			logger.debug("salvando nf {} no repositório...", nfLocal.numero());
			repositorio.saveAndFlush(nfLocal);
			logger.debug("nf {} salva", nfLocal.numero());
		}
		else {
			logger.debug("nota NÃO existe na base local, iniciando inclusão...");
			Filial filial = filialRepositorio.obterFilial(nfAgrix.filialId());
			
			logger.debug("definindo Modo de Operação em que a filial está trabalhando...");
			nfAgrix.alterarModoOperacao(filial.modoOperacao(), filial.contingencia());
			logger.debug("Modo de Operação definido");
			
			nfLocal = nfAgrix;
			
			logger.debug("salvando nf {} no repositório...", nfLocal.numero());
			repositorio.saveAndFlush(nfLocal);
			logger.debug("nf {} salva", nfLocal.numero());
		}
	}
}
