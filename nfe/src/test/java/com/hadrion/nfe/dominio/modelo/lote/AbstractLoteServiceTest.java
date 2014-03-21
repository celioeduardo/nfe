package com.hadrion.nfe.dominio.modelo.lote;

import java.util.HashSet;
import java.util.Set;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

public class AbstractLoteServiceTest {

	protected LoteRepositorio loteRepositorio;
	protected NotaFiscalRepositorio notaFiscalRepositorio;

	protected Set<NotaFiscalId> listaNotaFiscalId(String... lista) {
		Set<NotaFiscalId> result = new HashSet<NotaFiscalId>();
		for (String string : lista) {
			result.add(new NotaFiscalId(string));
		}
		return result;
	}
	

}
