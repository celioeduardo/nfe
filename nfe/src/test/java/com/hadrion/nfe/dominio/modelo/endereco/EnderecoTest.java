package com.hadrion.nfe.dominio.modelo.endereco;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;

public class EnderecoTest {
	@Test
	public void equalsEndereco(){
		assertEquals(	new Endereco("ROD. BR 262 KM 443 S/N ", 
			"S/N",
			"",
			"ZONA RURAL",
		    new Municipio("NOVA SERRANA - MG",Uf.MG),
		    new Pais(1L,"BRASIL"),
		    new Cep(35519000L)), 	new Endereco("ROD. BR 262 KM 443 S/N ", 
			"S/N",
			"",
			"ZONA RURAL",
		    new Municipio("NOVA SERRANA - MG",Uf.MG),
		    new Pais(1L,"BRASIL"),
		    new Cep(35519000L)));
	}
}
