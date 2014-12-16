package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

public class NotaFiscalSincronizacaoTest extends DominioTest {
	
	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio;
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private AgrixService agrixService;
	
	@Autowired
	@Qualifier("agrix")
	private JdbcTemplate jdbc;
		
	@Before
	public void setUp() throws Exception{
		super.setUp();
	}
	
	@Test
	public void agrixService(){
		List<NotaFiscal> notas = agrixService.obterNotas(
				Arrays.asList(new NotaFiscalId("H-013924F3091F4CC4E050007F010060FB")),
				Ambiente.HOMOLOGACAO);
//		NotaFiscal nf = notas.get(0);
//		System.out.println(nf.emitente());
//		System.out.println(nf.destinatario());
//		System.out.println(nf.localRetirada());
//		System.out.println(nf.localEntrega());
//		System.out.println(nf.localDestino());
//		System.out.println(nf.itens());
		assertEquals(1, notas.size());
		//System.out.println("\n\nNFE_SAIDA: "+jdbc.queryForObject("Select Count(*) From NFE_SAIDA",Integer.class));
	}
	
	@Test
	//@Rollback(false)
	public void sincronizacao(){
		NotaFiscal nf = notaFiscalRepositorio.notaFiscalPeloId(new NotaFiscalId("H-013924F3091F4CC4E050007F010060FB"));
		//assertNull(nf);
		
		notaFiscalRepositorio
				.notasPendentesAutorizacao(
						Arrays.asList(new NotaFiscalId("H-013924F3091F4CC4E050007F010060FB")),
						Ambiente.HOMOLOGACAO);
		
		em.flush();
		em.clear();
		
		nf = notaFiscalRepositorio.notaFiscalPeloId(new NotaFiscalId("H-013924F3091F4CC4E050007F010060FB"));
		assertNotNull(nf);
		
		
	}
		
}
