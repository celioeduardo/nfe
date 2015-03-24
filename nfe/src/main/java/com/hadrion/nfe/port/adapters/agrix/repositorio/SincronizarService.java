package com.hadrion.nfe.port.adapters.agrix.repositorio;

import java.util.List;

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
	
	public void sincronizar(List<NotaFiscalId> notas, Ambiente ambiente) {
		
		String owner=null;
		NotaFiscal nf = repositorio.findByNotaFiscalId(new NotaFiscalId(notas.get(0).id()));
		
		if (nf!=null)
			owner=nf.filialId().toString();
		
		List<NotaFiscal> notasAgrix = agrixService.obterNotas(notas,ambiente,owner);		
		for (NotaFiscal nfAgrix : notasAgrix) {
			mesclar(nfAgrix);
		}
	}
	
	private void mesclar(NotaFiscal nfAgrix){
		NotaFiscal nfLocal = repositorio.findByNotaFiscalId(nfAgrix.notaFiscalId());
		
		if (nfLocal != null){
			if (nfLocal.pendenteDeTransmissao())
				nfLocal.mesclar(nfAgrix);
			repositorio.save(nfLocal);
		}
		else {
			Filial filial = filialRepositorio.obterFilial(nfAgrix.filialId());
			nfAgrix.alterarModoOperacao(filial.modoOperacao(), filial.contingencia());
			nfLocal = nfAgrix;
			repositorio.saveAndFlush(nfLocal);
		}
	}
}
