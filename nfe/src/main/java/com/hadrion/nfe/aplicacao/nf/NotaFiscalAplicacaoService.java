package com.hadrion.nfe.aplicacao.nf;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

@Service
@Transactional
public class NotaFiscalAplicacaoService {
	
	@Autowired
	private NotaFiscalRepositorio repositorio;
	
	public List<NotaFiscalData> notasFicaisPendentesAutorizacao(){
		List<NotaFiscalData> result = new ArrayList<NotaFiscalData>();
		
		for (DescritorNotaFiscal nf : repositorio.notasPendentesAutorizacaoResumo()) {
			result.add(new NotaFiscalData(nf.notaFiscalId().id(),
					nf.numero(),
					String.valueOf(nf.serie().numero()),
					nf.emissao(),
					nf.valor().valor(),
					nf.publicoTipo(),
					nf.publicoCodigo(),
					nf.publicoNome(),
					nf.tipo()));
		}
		
		return result;
	}
	
}
