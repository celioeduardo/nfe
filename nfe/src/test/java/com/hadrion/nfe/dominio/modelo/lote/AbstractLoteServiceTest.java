package com.hadrion.nfe.dominio.modelo.lote;

import java.util.HashSet;
import java.util.Set;

import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public abstract class AbstractLoteServiceTest extends DominioTest{
	
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
