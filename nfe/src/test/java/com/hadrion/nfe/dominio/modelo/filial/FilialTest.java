package com.hadrion.nfe.dominio.modelo.filial;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;
import com.hadrion.nfe.tipos.Cnpj;

public class FilialTest {
	
	
	@Test
	public void novaFilial(){
		
		Filial filial = new Filial(
				new FilialId("654321"),
				"Hadrion",
				new Cnpj(4007474000116L), 
				new EmpresaId("123456"));
		
		assertEquals(new FilialId("654321"),filial.filialId());
		assertEquals("Hadrion",filial.nome());
		assertEquals(new Cnpj(4007474000116L),filial.cnpj());
		assertEquals(new EmpresaId("123456"),filial.empresaId());
		
	}
	
	@Test
	public void renomear(){
		Filial filial = new Filial(
				new FilialId("654321"),
				"Hadrion",
				new Cnpj(4007474000116L), 
				new EmpresaId("123456"));
		
		filial.renomear("Hadrion [ALTERADO]");
		assertEquals("Hadrion [ALTERADO]",filial.nome());
		
	}

}
