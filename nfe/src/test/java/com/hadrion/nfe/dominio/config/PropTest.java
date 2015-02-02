package com.hadrion.nfe.dominio.config;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hadrion.nfe.dominio.config.Prop.Server;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Application.class}, loader = SpringApplicationContextLoader.class)
@SpringApplicationConfiguration(classes = {Application.class})
@ActiveProfiles("test")
public class PropTest {

	@Autowired
	Prop prop;
	
	@Test @Ignore //Problema ao testar via Gradle
	public void test(){
		assertEquals("ABCD", prop.getNome());
		
		assertEquals(2,prop.getValores().size());
		assertEquals("test1",prop.getValores().get(0));
		assertEquals("test2",prop.getValores().get(1));
		
		assertEquals(2,prop.getServers().size());
		Server server = prop.getServers().get("coopadap");
		assertEquals("smtp.gmail.com",server.getHost());
		assertEquals(587,server.getPort());
		
		server = prop.getServers().get("agropesg");
		assertEquals("smtp.hotmail.com",server.getHost());
		assertEquals(25,server.getPort());
		
	}
	
}
