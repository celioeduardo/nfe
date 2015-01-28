package com.hadrion.nfe.dominio.modelo.cancelamento;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class CancelarNotaServiceTest extends DominioTest{

	@Autowired
	private CancelarNotaService cancelarNotaService;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test @Ignore
	public void cancelar(){
		cancelarNotaService.cancelar(new SolicitacaoCancelamento(
				new NotaFiscalId("H-03ADF8A01D1627DDE050007F01002730"), 
				"Teste de cancelamento da Nota Fiscal"));
	}
	
	
}
