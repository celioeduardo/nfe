package com.hadrion.nfe.dominio.modelo.lote;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

public class AbstractLoteServiceTest extends DominioTest{
	@Autowired
	protected GeracaoLoteService geracaoLoteService;
	@Autowired
	protected LoteRepositorio loteRepositorio;
	@Autowired
	protected NotaFiscalRepositorio notaFiscalRepositorio;

	protected Set<NotaFiscalId> listaNotaFiscalId(String... lista) {
		Set<NotaFiscalId> result = new HashSet<NotaFiscalId>();
		for (String string : lista) {
			result.add(new NotaFiscalId(string));
		}
		return result;
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}
	

}
