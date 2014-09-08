package com.hadrion.nfe.dominio.modelo.nf.publico;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.endereco.Cep;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.endereco.Pais;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Telefone;

public class EmitenteTest {
	@Test
	public void equalsEmitente(){
		assertEquals(new Emitente(
						new Cnpj(7233848000100L), 
						new Cpf(7233848000100L), 
						"OSPER AGROINDUSTRIAL S/A", 
						"", 
						new Endereco("ROD. BR 262 KM 443 S/N ", 
								"S/N",
								"",
								"ZONA RURAL",
							    new Municipio(0,"NOVA SERRANA - MG",Uf.MG),
							    new Pais(1L,"BRASIL"),
							    new Cep(35519000L),
							    new Telefone("3732322434")),
						new Telefone("3732322434"), 
						new InscricaoEstadual("452332065.00-50"), 
						new InscricaoEstadual("452332065.00-50"),
						null), 
					new Emitente(
						new Cnpj(7233848000100L), 
						new Cpf(7233848000100L), 
						"OSPER AGROINDUSTRIAL S/A", 
						"", 
						new Endereco("ROD. BR 262 KM 443 S/N ", 
								"S/N",
								"",
								"ZONA RURAL",
							    new Municipio(0,"NOVA SERRANA - MG",Uf.MG),
							    new Pais(1L,"BRASIL"),
							    new Cep(35519000L),
							    new Telefone("3732322434")),
						new Telefone("3732322434"), 
						new InscricaoEstadual("452332065.00-50"), 
						new InscricaoEstadual("452332065.00-50"),
						null));
	}
}
