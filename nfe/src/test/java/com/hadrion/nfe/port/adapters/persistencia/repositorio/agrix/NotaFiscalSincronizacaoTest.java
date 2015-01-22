package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hadrion.nfe.dominio.modelo.DominioTest;
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
	
}
