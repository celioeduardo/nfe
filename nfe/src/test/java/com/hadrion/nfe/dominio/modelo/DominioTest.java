package com.hadrion.nfe.dominio.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hadrion.comum.domain.model.RastreadorEventoTest;
import com.hadrion.nfe.dominio.config.Application;
import com.hadrion.nfe.dominio.modelo.cancelamento.SolicitacaoCancelamentoRepositorio;
import com.hadrion.nfe.dominio.modelo.certificado.CertificadoFixture;
import com.hadrion.nfe.dominio.modelo.empresa.Empresa;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaRepositorio;
import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.filial.FilialRepositorio;
import com.hadrion.nfe.dominio.modelo.lote.GeracaoLoteService;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;
import com.hadrion.nfe.dominio.modelo.lote.NumeroReciboLote;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalFixture;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.tipos.Cnpj;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Application.class}, loader = SpringApplicationContextLoader.class)
@ActiveProfiles("teste")
@Transactional
public abstract class DominioTest extends RastreadorEventoTest {
	@Autowired
	protected EmpresaRepositorio empresaRepositorio;
	@Autowired
	protected FilialRepositorio filialRepositorio;
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
		
		Empresa empresa = new Empresa(
				new EmpresaId("4007474000116"),
				"Hadrion",
				new Cnpj(4007474000116L),
				CertificadoFixture.certificado());
		
		empresaRepositorio.salvar(empresa);
		
		Filial filial = new Filial(
				new FilialId("4007474000116"),
				"Hadrion",
				new Cnpj(4007474000116L),
				empresa.empresaId());
		
		filialRepositorio.salvar(filial);
		
	}
	protected NotaFiscal notaEmitidaHomologacaoPersistidaParaTest(String id) {
		NotaFiscal nf = NotaFiscalFixture.nfEmHomologacao(new NotaFiscalId(id));
		nf.emitida();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaAutorizadaHomologacaoPersistidaParaTest(String id) {
		NotaFiscal nf = NotaFiscalFixture.nfEmHomologacao(new NotaFiscalId(id));
		nf.emitida();
		nf.autorizada();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaCanceladaHomologacaoPersistidaParaTest(String id) {
		NotaFiscal nf = NotaFiscalFixture.nfEmHomologacao(new NotaFiscalId(id));
		nf.emitida();
		nf.autorizada();
		nf.cancelada();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaInutilizadaHomologacaoPersistidaParaTest(String id) {
		NotaFiscal nf = NotaFiscalFixture.nfEmHomologacao(new NotaFiscalId(id));
		nf.emitida();
		nf.inutilizada();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaDenegadaHomologacaoPersistidaParaTest(String id) {
		NotaFiscal nf = NotaFiscalFixture.nfEmHomologacao(new NotaFiscalId(id));
		nf.emitida();
		nf.denegada();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaEmitidaProducaoPersistidaParaTest(String id) {
		NotaFiscal nf = NotaFiscalFixture.nfEmProducao(new NotaFiscalId(id));
		nf.emitida();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaAutorizadaProducaoPersistidaParaTest(String id) {
		NotaFiscal nf = NotaFiscalFixture.nfEmProducao(new NotaFiscalId(id));
		nf.emitida();
		nf.autorizada();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaCanceladaProducaoPersistidaParaTest(String id) {
		NotaFiscal nf = NotaFiscalFixture.nfEmProducao(new NotaFiscalId(id));
		nf.emitida();
		nf.autorizada();
		nf.cancelada();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaInutilizadaProducaoPersistidaParaTest(String id) {
		NotaFiscal nf = NotaFiscalFixture.nfEmProducao(new NotaFiscalId(id));
		nf.emitida();
		nf.inutilizada();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}
	protected NotaFiscal notaDenegadaProducaoPersistidaParaTest(String id) {
		NotaFiscal nf = NotaFiscalFixture.nfEmProducao(new NotaFiscalId(id));
		nf.emitida();
		nf.denegada();
		notaFiscalRepositorio.salvar(nf);
		return nf;
	}

	protected Lote loteGeradoEmProducaoPersistidoParaTest(NotaFiscal... notas) {
		Lote lote = geracaoLoteService.gerarLoteEmProducao(colecaoNotas(notas));
		loteRepositorio.salvar(lote);
		return lote;
	}

	protected Lote loteProcessadoEmProducaoPersistidoParaTest(NotaFiscal... notas) {
		Lote lote = geracaoLoteService.gerarLoteEmProducao(colecaoNotas(notas));
		lote.transmitido(new NumeroReciboLote(""));
		loteRepositorio.salvar(lote);
		return lote;
	}

	protected Lote loteGeradoEmHomologacaoPersistidoParaTest(NotaFiscal... notas) {
		Lote lote = geracaoLoteService.gerarLoteEmHomologacao(colecaoNotas(notas));
		loteRepositorio.salvar(lote);
		return lote;
	}
	
	protected Lote loteProcessadoEmHomologacaoPersistidoParaTest(NotaFiscal... notas) {
		Lote lote = geracaoLoteService.gerarLoteEmHomologacao(colecaoNotas(notas));
		lote.transmitido(new NumeroReciboLote(""));
		loteRepositorio.salvar(lote);
		return lote;
	}
	
	protected Lote loteProcessandoEmHomologacaoPersistidoParaTest(NotaFiscal... notas) {
		Lote lote = geracaoLoteService.gerarLoteEmHomologacao(colecaoNotas(notas));
		lote.transmitido(new NumeroReciboLote("123456"));
		loteRepositorio.salvar(lote);
		return lote;
	}

	protected Lote loteProcessandoEmProducaoPersistidoParaTest(NotaFiscal... notas) {
		Lote lote = geracaoLoteService.gerarLoteEmProducao(colecaoNotas(notas));
		lote.transmitido(new NumeroReciboLote("654321"));
		loteRepositorio.salvar(lote);
		return lote;
	}
	
	private Set<NotaFiscal> colecaoNotas(NotaFiscal... notas){
		Set<NotaFiscal> result = new HashSet<NotaFiscal>();
		for (NotaFiscal notaFiscal : notas)
			result.add(notaFiscal);
		return result;
	}
}
