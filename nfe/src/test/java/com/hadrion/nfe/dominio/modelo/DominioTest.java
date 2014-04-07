package com.hadrion.nfe.dominio.modelo;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hadrion.comum.domain.model.RastreadorEventoTest;
import com.hadrion.nfe.dominio.modelo.cancelamento.CancelarNotaService;
import com.hadrion.nfe.dominio.modelo.cancelamento.SolicitacaoCancelamentoRepositorio;
import com.hadrion.nfe.dominio.modelo.lote.GeracaoLoteService;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-nfe.xml" })
@ActiveProfiles("teste")
public abstract class DominioTest extends RastreadorEventoTest {
	
	@Autowired
	protected GeracaoLoteService geracaoLoteService;
	@Autowired
	protected LoteRepositorio loteRepositorio;
	@Autowired
	protected NotaFiscalRepositorio notaFiscalRepositorio;
	@Autowired
	protected SolicitacaoCancelamentoRepositorio solicitacaoCancelamentoRepositorio;
	@Override
	public void setUp() throws Exception {
		super.setUp();
		loteRepositorio.limpar();
		notaFiscalRepositorio.limpar();
		solicitacaoCancelamentoRepositorio.limpar();
	}
	
}
