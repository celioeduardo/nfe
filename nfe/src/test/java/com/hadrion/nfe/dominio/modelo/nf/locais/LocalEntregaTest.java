package com.hadrion.nfe.dominio.modelo.nf.locais;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.endereco.Cep;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.endereco.Pais;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.Telefone;

public class LocalEntregaTest {
	
	@Test
	public void equalsLocalEntrega(){
		assertEquals(new LocalEntrega(
						new Cnpj(86675642000106L), 
						new Cpf(15997427803L), 
						new Endereco("ROD. BR 262 KM 443 S/N ", 
								"S/N",
								"",
								"ZONA RURAL",
							    new Municipio(0,"NOVA SERRANA - MG",Uf.MG),
							    new Pais(1L,"BRASIL"),
							    new Cep(35519000L),
							    new Telefone("1639164500"))),
				    new LocalEntrega(
							new Cnpj(86675642000106L), 
							new Cpf(15997427803L), 
							new Endereco("ROD. BR 262 KM 443 S/N ", 
									"S/N",
									"",
									"ZONA RURAL",
								    new Municipio(0,"NOVA SERRANA - MG",Uf.MG),
								    new Pais(1L,"BRASIL"),
								    new Cep(35519000L),
								    new Telefone("1639164500"))));					
	}
}
