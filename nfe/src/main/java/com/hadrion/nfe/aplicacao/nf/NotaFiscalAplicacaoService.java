package com.hadrion.nfe.aplicacao.nf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

@Service
@Transactional
public class NotaFiscalAplicacaoService {
	
	@Autowired
	private NotaFiscalRepositorio repositorio;
	
	public List<NotaFiscalData> notasFicaisPendentesAutorizacaoResumo(Double empresa,Double filial,Date inicio,Date fim,String usuario,String notaFiscalId){
		List<NotaFiscalData> result = new ArrayList<NotaFiscalData>();
		NotaFiscalId notaFiscalIdFiltro = null;
		if (notaFiscalId!=null)
			notaFiscalIdFiltro=new NotaFiscalId(notaFiscalId);
		for (DescritorNotaFiscal nf : repositorio.notasPendentesAutorizacaoResumo(empresa,filial,inicio,fim,usuario,notaFiscalIdFiltro)) {
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
	public List<NotaFiscalData> notasFicaisPendentesAutorizacao(){
		List<NotaFiscalData> result = new ArrayList<NotaFiscalData>();
		
		for (NotaFiscal nf : repositorio.notasPendentesAutorizacao()) {
			result.add(new NotaFiscalData(nf.notaFiscalId().id(),
					nf.numero(),
					String.valueOf(nf.serie().numero()),
					nf.emissao(),
					nf.total().valor(),
					"C",
					1L,
					nf.destinatario().razaoSocial(),
					nf.tipoOperacao().toString()));
		}
		
		return result;
	}
	public String obterComboFilial(String query){
		return repositorio.queryToJson(query);
	}
}
