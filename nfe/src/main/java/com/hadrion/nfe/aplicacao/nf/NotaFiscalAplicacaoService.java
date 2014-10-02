package com.hadrion.nfe.aplicacao.nf;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

@Service
@Transactional
public class NotaFiscalAplicacaoService {
	
	@Autowired
	NotaFiscalRepositorio repositorio;
	
	public List<NotaFiscalData> notasFicaisPendentesAutorizacao(){
		List<NotaFiscalData> result = new ArrayList<NotaFiscalData>();
		
		for (NotaFiscal nf : repositorio.notasPendentesAutorizacao()) {
			result.add(new NotaFiscalData(
					nf.numero(),
					String.valueOf(nf.serie().numero()),
					nf.emissao(),
					nf.total().valor()));
		}
		
		return result;
	}
	
}
