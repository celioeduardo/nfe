package com.hadrion.nfe.dominio.modelo.lote;

import java.util.HashSet;
import java.util.Set;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public abstract class AbstractLoteServiceTest extends DominioTest{
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}
	
	protected Set<NotaFiscalId> listaNotaFiscalId(String... lista) {
		Set<NotaFiscalId> result = new HashSet<NotaFiscalId>();
		for (String string : lista) {
			result.add(new NotaFiscalId(string));
		}
		return result;
	}	
}
