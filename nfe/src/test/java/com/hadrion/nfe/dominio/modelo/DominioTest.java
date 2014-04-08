package com.hadrion.nfe.dominio.modelo;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hadrion.comum.domain.model.RastreadorEventoTest;
import com.hadrion.nfe.dominio.modelo.cancelamento.SolicitacaoCancelamentoRepositorio;
import com.hadrion.nfe.dominio.modelo.lote.GeracaoLoteService;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
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
	protected NotaFiscal notaEmitidaHomologacaoPersistidaParaTest(String id) {
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId(id));
		nf.emitidaHomologacao();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaAutorizadaHomologacaoPersistidaParaTest(String id) {
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId(id));
		nf.emitidaHomologacao();
		nf.autorizadaHomologacao();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaCanceladaHomologacaoPersistidaParaTest(String id) {
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId(id));
		nf.emitidaHomologacao();
		nf.autorizadaHomologacao();
		nf.canceladaHomologacao();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaInutilizadaHomologacaoPersistidaParaTest(String id) {
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId(id));
		nf.emitidaHomologacao();
		nf.inutilizadaHomologacao();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaDenegadaHomologacaoPersistidaParaTest(String id) {
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId(id));
		nf.emitidaHomologacao();
		nf.denegadaHomologacao();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaEmitidaProducaoPersistidaParaTest(String id) {
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId(id));
		nf.emitidaProducao();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaAutorizadaProducaoPersistidaParaTest(String id) {
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId(id));
		nf.emitidaProducao();
		nf.autorizadaProducao();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaCanceladaProducaoPersistidaParaTest(String id) {
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId(id));
		nf.emitidaProducao();
		nf.autorizadaProducao();
		nf.canceladaProducao();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaInutilizadaProducaoPersistidaParaTest(String id) {
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId(id));
		nf.emitidaProducao();
		nf.inutilizadaProducao();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaDenegadaProducaoPersistidaParaTest(String id) {
		NotaFiscal nf = new NotaFiscal(new NotaFiscalId(id));
		nf.emitidaProducao();
		nf.denegadaProducao();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	
}
