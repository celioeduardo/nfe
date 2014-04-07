package com.hadrion.nfe.dominio.modelo;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hadrion.comum.domain.model.RastreadorEventoTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-nfe.xml" })
@ActiveProfiles("teste")
public class DominioTest extends RastreadorEventoTest {
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}
	
}
