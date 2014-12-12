package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.NotaFiscalRepositorioSpringData;

@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class SincronizarService {
	
	@Autowired
	private AgrixService agrixService;
	
	@Autowired
	private NotaFiscalRepositorioSpringData repositorio;
	
	public void sincronizar(List<NotaFiscalId> notas, Ambiente ambiente) {
		List<NotaFiscal> notasAgrix = agrixService.obterNotas(notas,ambiente);
		for (NotaFiscal nfAgrix : notasAgrix) {
			//TODO Remover 
			if (nfAgrix.notaFiscalId().equals("H-05EF60F0EA9FFA7EE050007F01004CE2"))
				System.out.println("H-05EF60F0EA9FFA7EE050007F01004CE2");
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
			nfLocal = nfAgrix;
			repositorio.saveAndFlush(nfLocal);
		}
	}
}
