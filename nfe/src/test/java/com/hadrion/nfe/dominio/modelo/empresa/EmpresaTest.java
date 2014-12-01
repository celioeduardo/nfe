package com.hadrion.nfe.dominio.modelo.empresa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.certificado.CertificadoFixture;
import com.hadrion.nfe.tipos.Cnpj;

public class EmpresaTest {
	
	@Test
	public void novaEmpresa(){
		
		Empresa emp = new Empresa(
				new EmpresaId("123456"),
				"Hadrion Sistemas Integrados",
				new Cnpj(4007474000116L), 
				CertificadoFixture.certificado());
		
		assertEquals(new EmpresaId("123456"),emp.empresaId());
		assertEquals("Hadrion Sistemas Integrados",emp.nome());
		assertEquals(new Cnpj(4007474000116L),emp.cnpj());
		assertNotNull(emp.certificado());
		
	}
	
	@Test
	public void alterarNome(){
		
		Empresa emp = new Empresa(
				new EmpresaId("123456"),
				"Hadrion Sistemas Integrados",
				new Cnpj(4007474000116L), 
				CertificadoFixture.certificado());
		
		emp.renomear("Hadrion Sistemas Integrados Ltda");
		assertEquals("Hadrion Sistemas Integrados Ltda",emp.nome());
		
	}

}
