package com.hadrion.nfe.port.adapters.agrix.cancelamento;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.dominio.modelo.cancelamento.CancelarNotaService;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.port.adapters.agrix.repositorio.AgrixService;

@Service
@Transactional
public class CancelarNotaAgrixService extends CancelarNotaService {

	@Autowired
	private AgrixService agrixService;
	
	@Override
	protected void permiteCancelarNotaFical(NotaFiscal nf) {
		super.permiteCancelarNotaFical(nf);
		agrixService.simularCancelamento(nf);
	}

	

}
