package com.hadrion.nfe.dominio.modelo.lote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalFixture;

public class LoteRepositorioTest extends DominioTest {
	
	@Autowired
	private EntityManager em;
	
	private NotaFiscal nf;
	
	@Autowired
	NotaFiscalFixture notaFiscalFixture;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		
		nf = NotaFiscalFixture.nfEmHomologacao();
		
		notaFiscalRepositorio.salvar(nf);
		
		em.flush();
		em.clear();
	}
	
	
	@Test
	public void salvar () throws SQLException {
		Lote lote = Lote.gerarEmHomologacao(nf,new EmpresaId("4007474000116"));
		
		loteRepositorio.salvar(lote);
		
		em.flush();
		em.clear();
		
		assertEquals(1,loteRepositorio.lotesDaNota(nf.notaFiscalId()).size());
		
		lote = loteRepositorio.lotesDaNota(nf.notaFiscalId()).iterator().next();
		
		assertEquals(Ambiente.HOMOLOGACAO,lote.ambiente());
		assertEquals(new EmpresaId("4007474000116"),lote.empresaId());
		assertTrue(lote.estaNaoEnviado());
		
		assertEquals(1,lote.notas().size());
		
		assertTrue(lote.temNota(nf.notaFiscalId()));
		
	} 
	
	@Test
	public void lotesDaNotaFiscal() {
		
		Lote lote1 = Lote.gerarEmHomologacao(nf,new EmpresaId("4007474000116"));
		loteRepositorio.salvar(lote1);
		
		NotaFiscal outraNf = notaFiscalFixture.nfEmHomologacaoPersistidaParaTest();
		Lote lote2 = Lote.gerarEmHomologacao(outraNf,new EmpresaId("4007474000116"));
		loteRepositorio.salvar(lote2);

		NotaFiscal nfSemLote = notaFiscalFixture.nfEmHomologacaoPersistidaParaTest();
		
		em.flush();
		em.clear();
		
		Set<Lote> lotes;
		
		lotes = loteRepositorio.lotesDaNota(nf.notaFiscalId());
		assertEquals(1,lotes.size());
		assertEquals(lote1, lotes.iterator().next());
		assertTrue(lote1.temNota(nf.notaFiscalId()));
		
		lotes = loteRepositorio.lotesDaNota(outraNf.notaFiscalId());
		assertEquals(1,lotes.size());
		assertEquals(lote2, lotes.iterator().next());
		assertTrue(lote2.temNota(outraNf.notaFiscalId()));
		
		lotes = loteRepositorio.lotesDaNota(nfSemLote.notaFiscalId());
		assertEquals(0,lotes.size());
		
	}
	
		
}
