package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

@Repository
@Profile("teste2")
public class MockNotaFiscalRepositorio implements NotaFiscalRepositorio {

	private Map<String,NotaFiscal> store=new HashMap<String, NotaFiscal>();
	
	@Override
	public List<NotaFiscal> notasPendentesAutorizacao() {		
		return null;
	}
	
	public void salvar(NotaFiscal notaFiscal){
		store.put(notaFiscal.notaFiscalId().id(), notaFiscal);
	}

	public NotaFiscal notaFiscalPeloId(NotaFiscalId notaFiscalId){
		return store.get(notaFiscalId.id());
	}

	@Override
	public void limpar() {
		store.clear();
	}

	@Override
	public List<DescritorNotaFiscal> notasPendentesAutorizacaoResumo(
			Double empresa, Double filial, Date inicio, Date fim,
			String usuario, NotaFiscalId notaFiscalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryToJson(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	
} 
